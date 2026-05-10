package velaire.ecommmerce;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import velaire.ecommmerce.business.controller.ProductController;
import velaire.ecommmerce.infrastructure.entity.Cart;
import velaire.ecommmerce.infrastructure.entity.Product;

@WebMvcTest
public class CartTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CartController cartController;

    @Test
    void addProductInCart() throws Exception{

    }
}
