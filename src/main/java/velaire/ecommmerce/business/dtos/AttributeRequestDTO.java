package velaire.ecommmerce.business.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class AttributeRequestDTO {

        @NotBlank(message = "O nome do atributo é obrigatório e não pode estar vazio")
        private String name;

}
