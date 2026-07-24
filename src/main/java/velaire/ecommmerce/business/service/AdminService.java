package velaire.ecommmerce.business.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import velaire.ecommmerce.business.dtos.ProductRequestDTO;
import velaire.ecommmerce.business.dtos.ProductResponseDTO;
import velaire.ecommmerce.infrastructure.entity.Aroma;
import velaire.ecommmerce.infrastructure.entity.Category;
import velaire.ecommmerce.infrastructure.entity.Format;
import velaire.ecommmerce.infrastructure.entity.Product;
import velaire.ecommmerce.infrastructure.repository.AromaRepository;
import velaire.ecommmerce.infrastructure.repository.CategoryRepository;
import velaire.ecommmerce.infrastructure.repository.FormatRepository;
import velaire.ecommmerce.infrastructure.repository.ProductRepository;

@Service
@RequiredArgsConstructor
public class AdminService {

    @Autowired
    private AromaRepository aromaRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private FormatRepository formatRepository;
    @Autowired
    private ProductRepository productRepository;

    private final ExternalIntegrationService integrationService;

    @Transactional
    public ProductResponseDTO createProduct(ProductRequestDTO dto) {


        // 2. Buscar e validar as entidades relacionadas
        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Categoria não encontrada"));

        Aroma aroma = aromaRepository.findById(dto.getAromaId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Aroma não encontrado"));

        Format format = formatRepository.findById(dto.getFormatId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Formato não encontrado"));

        // 3. Mapear DTO para Entity
        Product product = new Product();
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setStockQuantity(dto.getStockQuantity());
        product.setImageUrl(dto.getImageUrl());
        product.setCategory(category);
        product.setAroma(aroma);
        product.setFormat(format);

        // 4. Salvar
        Product savedProduct = productRepository.save(product);

        ProductResponseDTO response = convertToResponseDTO(savedProduct);

        integrationService.createProductExternalSystem(response);
        // 5. Retornar um DTO de resposta (boa prática para não expor a entidade pura)
        return convertToResponseDTO(savedProduct);
    }


    private ProductResponseDTO convertToResponseDTO(Product product) {
        return ProductResponseDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .stockQuantity(product.getStockQuantity())
                .imageUrl(product.getImageUrl())
                .categoryName(product.getCategory().getName())
                .aromaName(product.getAroma().getName())
                .formatName(product.getFormat().getName())
                .build();
    }

    public Aroma createAroma(String name){
        if (aromaRepository.existsByNameIgnoreCase(name)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Este aroma já está cadastrado.");
        }
        Aroma aroma = new Aroma();
        aroma.setName(name);
        return aromaRepository.save(aroma);
    }

    public Category createCategory(String name){
        if (categoryRepository.existsByNameIgnoreCase(name)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Esta categoria já está cadastrada");
        }
        Category category = new Category();
        category.setName(name);
        return categoryRepository.save(category);
    }

    public Format createFormat(String name){
        if (formatRepository.existsByNameIgnoreCase(name)){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Este formato já está cadastrado");
        }
        Format format = new Format();
        format.setName(name);
        return formatRepository.save(format);
    }
}
