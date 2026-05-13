package velaire.ecommmerce.business.dtos;

import lombok.Data;

@Data
public class CartRequestDTO {
    private Long productId;
    private Integer quantity;
}
