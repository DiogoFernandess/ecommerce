package velaire.ecommmerce.business.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;


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
}
