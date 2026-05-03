package velaire.ecommmerce.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import velaire.ecommmerce.infrastructure.entity.Category;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    // Busca uma categoria pelo nome exato (útil para validações do Admin)
    Optional<Category> findByNameIgnoreCase(String name);

    // Verifica se já existe uma categoria com esse nome antes de salvar
    boolean existsByNameIgnoreCase(String name);
}
