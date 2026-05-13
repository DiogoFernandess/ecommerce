package velaire.ecommmerce.business.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import velaire.ecommmerce.business.dtos.CartItemResponseDTO;
import velaire.ecommmerce.business.dtos.CartResponseDTO;
import velaire.ecommmerce.infrastructure.entity.Cart;
import velaire.ecommmerce.infrastructure.entity.CartItem;
import velaire.ecommmerce.infrastructure.entity.Product;
import velaire.ecommmerce.infrastructure.repository.CartRepository;
import velaire.ecommmerce.infrastructure.repository.ProductRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    @Transactional
    public void addProduct(String userId, Long productId, Integer quantity){

        Product product = productRepository.findById(productId)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado"));

        if (product.getStockQuantity()< quantity){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Estoque insuficiente");
        }

        Cart cart = cartRepository.findByUserId(userId)
                .orElseGet(()->{
                    Cart c = new Cart();
                    c.setUserId(userId);
                    return cartRepository.save(c);
                });

        cart.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .ifPresentOrElse(
                        item -> item.setQuantity(item.getQuantity() + quantity),
                        () -> {
                            CartItem newItem = new CartItem();
                            newItem.setProduct(product);
                            newItem.setQuantity(quantity);
                            cart.addItem(newItem);
                        }
                );
        cartRepository.save(cart);
    }

    @Transactional
    public void removeItem(String userId, Long productId){

        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Carrinho vazio"));

        boolean removed = cart.getItems().removeIf(item -> item.getProduct().getId().equals(productId));

        if (!removed){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado no carrinho");

        }

        cartRepository.save(cart);
    }

    @Transactional
    public void clearCart(String userId){

        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Carrinho vazio"));

        cart.getItems().clear();

        cartRepository.save(cart);
    }

    public CartResponseDTO getCart(String userId){

        Cart cart = cartRepository.findByUserId(userId)
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setUserId(userId);
                    return newCart;
                });

        List<CartItemResponseDTO> itemDTOs = cart.getItems().stream()
                .map(item -> {
                    BigDecimal price = item.getProduct().getPrice();
                    BigDecimal quantity = BigDecimal.valueOf(item.getQuantity());
                    BigDecimal subTotal = price.multiply(quantity);

                    return CartItemResponseDTO.builder()
                            .productId(item.getProduct().getId())
                            .productName(item.getProduct().getName())
                            .imageUrl(item.getProduct().getImageUrl())
                            .quantity(item.getQuantity())
                            .unitPrice(price)
                            .subTotal(subTotal)
                            .build();
                })
                .collect(Collectors.toList());

        BigDecimal totalCart = itemDTOs.stream()
                .map(CartItemResponseDTO::getSubTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return CartResponseDTO.builder()
                .userId(userId).items(itemDTOs).totalCartPrice(totalCart).build();
    }
}
