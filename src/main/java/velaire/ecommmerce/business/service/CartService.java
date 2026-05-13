package velaire.ecommmerce.business.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
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
}
