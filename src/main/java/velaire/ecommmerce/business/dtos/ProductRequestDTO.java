package velaire.ecommmerce.business.dtos;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

public record ProductRequestDTO(
        String name,
        String description,
        @NotNull @Positive BigDecimal price,
        @NotNull @Min(0) Integer stockQuantity,
        @NotNull Long categoryId,
        @NotNull Long aromaId,
        @NotNull Long formatId,
        String imageUrl
) {
}
