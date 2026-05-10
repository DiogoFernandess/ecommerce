package velaire.ecommmerce;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.web.servlet.MockMvc;
import velaire.ecommmerce.business.controller.AdminController;
import velaire.ecommmerce.business.dtos.AttributeRequestDTO;
import velaire.ecommmerce.business.dtos.ProductRequestDTO;
import velaire.ecommmerce.business.dtos.ProductResponseDTO;
import velaire.ecommmerce.business.service.AdminService;
import velaire.ecommmerce.business.service.ProductService;
import velaire.ecommmerce.infrastructure.entity.Aroma;
import velaire.ecommmerce.infrastructure.entity.Category;
import velaire.ecommmerce.infrastructure.entity.Format;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AdminController.class)
class AdminControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AdminService adminService;

    @Test
    @DisplayName("Admin deve criar Categoria com sucesso")
    void createCategoryLikeAdmin() throws Exception {
        AttributeRequestDTO request = new AttributeRequestDTO("Velas Clássicas");

        Category mockCategory = new Category();
        mockCategory.setId(1L);
        mockCategory.setName("Velas Clássicas");
        Mockito.when(adminService.createCategory(anyString())).thenReturn(mockCategory);

        mockMvc.perform(post("/admin/categories")
                        .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_ADMIN"))) // Simula Admin
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Admin deve criar Aroma com sucesso")
    void createAromaLikeAdmin() throws Exception {
        AttributeRequestDTO request = new AttributeRequestDTO("Lavanda");

        Aroma mockAroma = new Aroma();
        mockAroma.setName("Lavanda");
        Mockito.when(adminService.createAroma(anyString())).thenReturn(mockAroma);

        mockMvc.perform(post("/admin/aromas")
                        .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_ADMIN"))) // Simula Admin
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Admin deve criar Formato com sucesso")
    void createFormatLikeAdmin() throws Exception {
        AttributeRequestDTO request = new AttributeRequestDTO("Cilíndrica");

        Format mockFormat = new Format();
        mockFormat.setName("Cilíndrica");
        Mockito.when(adminService.createFormat(anyString())).thenReturn(mockFormat);

        mockMvc.perform(post("/admin/formats")
                        .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_ADMIN"))) // Simula Admin
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Admin deve criar Produto com sucesso")
    void createProductLikeAdmin() throws Exception {
        ProductRequestDTO request = new ProductRequestDTO(
                "Vela de Lavanda", "Calmaria pura",
                45.90, 50, 1L, 1L, 1L, ""
        );

        // Simulando a resposta do Service
        ProductResponseDTO response = new ProductResponseDTO(
                1L, "Vela de Lavanda", "Calmaria pura",45.90, 50, null,
                "Velas Clássicas", "Lavanda", "Cilíndrica"
        );
        Mockito.when(adminService.createProduct(any(ProductRequestDTO.class))).thenReturn(response);

        mockMvc.perform(post("/admin/products")
                        .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_ADMIN"))) // Simula Admin
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());
    }
}
