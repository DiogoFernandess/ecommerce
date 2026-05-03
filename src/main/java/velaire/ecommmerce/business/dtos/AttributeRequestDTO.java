package velaire.ecommmerce.business.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AttributeRequestDTO {
    @NotBlank(message = "O atributo não pode ser nulo")
    private String name;
}
