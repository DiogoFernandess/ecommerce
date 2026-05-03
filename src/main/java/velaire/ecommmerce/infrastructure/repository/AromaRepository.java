package velaire.ecommmerce.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import velaire.ecommmerce.infrastructure.entity.Aroma;

import java.util.Optional;

@Repository
public interface AromaRepository extends JpaRepository<Aroma, Long> {
        Optional<Aroma> findByNameIgnoreCase(String name);

        boolean existsByNameIgnoreCase(String name);
}
