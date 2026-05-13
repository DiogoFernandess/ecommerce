package velaire.ecommmerce.infrastructure.repository;

import jakarta.annotation.Nonnull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import velaire.ecommmerce.infrastructure.entity.Product;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findById(@Nonnull Long id);
    // Busca todos os produtos de uma categoria específica
    List<Product> findByCategoryId(Long categoryId);

    // Busca por aroma
    List<Product> findByAromaId(Long aromaId);

    // Busca por formato
    List<Product> findByFormatId(Long formatId);

    // Filtro Combinado: Busca por Categoria, Aroma e Formato ao mesmo tempo
    // O Spring trata os parâmetros nulos se usarmos uma Query personalizada
    @Query("SELECT p FROM Product p WHERE " +
            "(:categoryId IS NULL OR p.category.id = :categoryId) AND " +
            "(:aromaId IS NULL OR p.aroma.id = :aromaId) AND " +
            "(:formatId IS NULL OR p.format.id = :formatId)")
    List<Product> findByFilters(Long categoryId, Long aromaId, Long formatId);


}
