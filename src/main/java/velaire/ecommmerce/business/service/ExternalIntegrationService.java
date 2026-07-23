package velaire.ecommmerce.business.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import velaire.ecommmerce.business.dtos.ExternalProductDTO;
import velaire.ecommmerce.business.dtos.ProductResponseDTO;


@Service
public class ExternalIntegrationService {

    private final RestClient restClient;

    public ExternalIntegrationService(@Value("${gateway.externo.url}") String baseUrl,
                                      @Value("${gateway.apikey}") String apiKey){
        this.restClient = RestClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader("Authorization", "Bearer " + apiKey)
                .build();
    }

    public void createProductExternalSystem(ProductResponseDTO productResponseDTO){

        ExternalProductDTO payload = new ExternalProductDTO(
                String.valueOf(productResponseDTO.getId()),
                productResponseDTO.getName(),
                productResponseDTO.getPrice(),
                "BRL"
        );

        restClient.post()
                .uri("/products/create")
                .body(payload)
                .retrieve()
                .toBodilessEntity();
    }
}
