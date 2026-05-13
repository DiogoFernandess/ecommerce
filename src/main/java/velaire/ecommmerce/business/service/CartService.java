package velaire.ecommmerce.business.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import velaire.ecommmerce.infrastructure.entity.Cart;
import velaire.ecommmerce.infrastructure.entity.CartItem;
import velaire.ecommmerce.infrastructure.entity.Product;
import velaire.ecommmerce.infrastructure.repository.CartRepository;
import velaire.ecommmerce.infrastructure.repository.ProductRepository;

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
}
