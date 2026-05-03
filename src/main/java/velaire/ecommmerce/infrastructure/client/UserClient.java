package velaire.ecommmerce.infrastructure.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import velaire.ecommmerce.business.dtos.UserDTO;

@FeignClient(name = "user", url = "${usuario.url}")
public interface UserClient {
    @GetMapping("/user")
    UserDTO findUserByEmail(@RequestParam("email") String email,
                                 @RequestHeader("Authorization") String token);
}
