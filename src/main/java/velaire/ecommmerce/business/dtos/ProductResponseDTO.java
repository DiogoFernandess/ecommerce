package velaire.ecommmerce.business.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import velaire.ecommmerce.infrastructure.entity.Product;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponseDTO{
    Long id;
    String name;
    String description;
    double price;
    Integer stockQuantity;
    String imageUrl;
    String categoryName;
    String aromaName;
    String formatName;
}


