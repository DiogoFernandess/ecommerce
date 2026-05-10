package velaire.ecommmerce.business.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import velaire.ecommmerce.infrastructure.repository.AromaRepository;
import velaire.ecommmerce.infrastructure.repository.CategoryRepository;
import velaire.ecommmerce.infrastructure.repository.FormatRepository;
import velaire.ecommmerce.infrastructure.repository.ProductRepository;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final AromaRepository aromaRepository;
    private final FormatRepository formatRepository;


}
