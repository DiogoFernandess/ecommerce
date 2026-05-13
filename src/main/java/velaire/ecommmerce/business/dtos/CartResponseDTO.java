package velaire.ecommmerce.business.dtos;

import lombok.Data;

import java.util.List;

@Data
public class CartResponseDTO {
    String userId;
    List<CartResponseDTO> items;
    Double totalCartPrice;
}
