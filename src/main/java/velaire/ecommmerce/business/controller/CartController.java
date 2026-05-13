package velaire.ecommmerce.business.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import velaire.ecommmerce.business.dtos.CartRequestDTO;
import velaire.ecommmerce.business.service.CartService;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping("/add")
    public ResponseEntity<Void> addProduct (@RequestBody CartRequestDTO request, Authentication authentication){

        String userId = authentication.getName();
        cartService.addProduct(userId, request.getProductId(), request.getQuantity());
        return ResponseEntity.status(HttpStatus.CREATED).build();

    }

    @DeleteMapping("/item/{productId}")
    public ResponseEntity<Void> removeItem(@PathVariable Long productId, Authentication authentication){

        String userId = authentication.getName();
        cartService.removeItem(userId, productId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/clear")
    public ResponseEntity<Void> clearCart (Authentication authentication){

        String userId = authentication.getName();
        cartService.clearCart(userId);

        return ResponseEntity.noContent().build();
    }
}
