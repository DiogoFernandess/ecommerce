package velaire.ecommmerce.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import velaire.ecommmerce.infrastructure.entity.Cart;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByUserId(String userId);
}
