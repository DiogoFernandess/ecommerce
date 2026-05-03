package velaire.ecommmerce.business.dtos;

import velaire.ecommmerce.infrastructure.entity.Product;

import java.math.BigDecimal;

public record ProductResponseDTO(
        Long id,
        String name,
        String description,
        BigDecimal price,
        Integer stockQuantity,
        String imageUrl,
        // Aqui retornamos apenas os nomes ou objetos simples dos atributos
        String categoryName,
        String aromaName,
        String formatName
) {
    // Construtor de conveniência para converter a Entity para o DTO
    public ProductResponseDTO(Product product) {
        this(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getStockQuantity(),
                product.getImageUrl(),
                product.getCategory() != null ? product.getCategory().getName() : null,
                product.getAroma() != null ? product.getAroma().getName() : null,
                product.getFormat() != null ? product.getFormat().getName() : null
        );
    }
}
