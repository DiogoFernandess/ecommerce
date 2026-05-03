package velaire.ecommmerce.business.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import velaire.ecommmerce.business.dtos.AttributeRequestDTO;
import velaire.ecommmerce.business.dtos.ProductRequestDTO;
import velaire.ecommmerce.business.dtos.ProductResponseDTO;
import velaire.ecommmerce.business.service.AdminService;
import velaire.ecommmerce.business.service.ProductService;
import velaire.ecommmerce.infrastructure.entity.Aroma;
import velaire.ecommmerce.infrastructure.entity.Category;
import velaire.ecommmerce.infrastructure.entity.Format;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final ProductService productService;
    private final AdminService adminService;

    @PostMapping("/products")
    public ResponseEntity<ProductResponseDTO> createProduct (@RequestBody @Valid ProductRequestDTO dto){
        ProductResponseDTO response = adminService.createProduct(dto);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/categories")
    public ResponseEntity<Category> createCategory (@RequestBody @Valid AttributeRequestDTO dto){
        Category category = adminService.createCategory(dto.getName());

        return new ResponseEntity<>(category, HttpStatus.CREATED);
    }

    @PostMapping("/aromas")
    public ResponseEntity<Aroma> createAroma (@RequestBody @Valid AttributeRequestDTO dto){
        Aroma aroma = adminService.createAroma(dto.getName());

        return new ResponseEntity<>(aroma, HttpStatus.CREATED);
    }

    @PostMapping("/formats")
    public ResponseEntity<Format> createFormat(@RequestBody @Valid AttributeRequestDTO dto){
        Format format = adminService.createFormat(dto.getName());

        return new ResponseEntity<>(format, HttpStatus.CREATED);
    }
}
