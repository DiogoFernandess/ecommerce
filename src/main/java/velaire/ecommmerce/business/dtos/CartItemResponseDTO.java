package velaire.ecommmerce.business.dtos;

import lombok.Data;

@Data
public class CartItemResponseDTO {
    Long productId;
    String productName;
    String imageUrl;
    Integer quantity;
    Double unitPrice;
    Double subTotal;

}
