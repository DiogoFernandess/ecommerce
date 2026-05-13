package velaire.ecommmerce.business.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartResponseDTO {
    private String userId;
    private List<CartItemResponseDTO> items;
    private BigDecimal totalCartPrice;
}
