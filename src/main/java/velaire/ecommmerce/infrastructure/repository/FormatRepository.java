package velaire.ecommmerce.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import velaire.ecommmerce.infrastructure.entity.Format;

import java.util.Optional;

@Repository
public interface FormatRepository extends JpaRepository<Format, Long> {
    Optional<Format> findByNameIgnoreCase(String name);

    boolean existsByNameIgnoreCase(String name);
}