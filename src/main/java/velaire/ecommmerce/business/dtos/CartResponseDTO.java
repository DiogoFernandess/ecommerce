package velaire.ecommmerce.business.dtos;

import java.util.List;

public class CartResponseDTO {
    String userId;
    List<CartResponseDTO> items;
    Double totalCartPrice;
}
