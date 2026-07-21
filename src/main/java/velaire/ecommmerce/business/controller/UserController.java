package velaire.ecommmerce.business.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import velaire.ecommmerce.business.dtos.UserDTO;
import velaire.ecommmerce.business.service.UserService;
import velaire.ecommmerce.infrastructure.security.JwtUtil;
import velaire.ecommmerce.infrastructure.security.SecurityConfig;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@SecurityRequirement(name = SecurityConfig.SECURITY_SCHEME)
public class UserController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;


    @PostMapping("/register")
    public ResponseEntity<UserDTO> saveUser(@RequestBody UserDTO userDto){
        return ResponseEntity.ok(userService.saveUser(userDto));
    }

    @PostMapping("/login")
    public String login (@RequestBody UserDTO userDto){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userDto.getEmail(),
                        userDto.getPassword()
                )
        );
        String role = authentication.getAuthorities().stream()
                .findFirst() // Pega a primeira role que encontrar
                .map(GrantedAuthority::getAuthority) // Converte para String
                .orElse("ROLE_USER");

        return "Bearer " + jwtUtil.generateToken(authentication.getName(), role);
    }

}
