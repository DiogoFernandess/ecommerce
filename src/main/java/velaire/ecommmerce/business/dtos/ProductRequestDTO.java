package velaire.ecommmerce.business.dtos;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequestDTO{
    String name;
    String description;
    @NotNull @Positive BigDecimal price;
    @NotNull @Min(0) Integer stockQuantity;
    @NotNull Long categoryId;
    @NotNull Long aromaId;
    @NotNull Long formatId;
    String imageUrl;
}

