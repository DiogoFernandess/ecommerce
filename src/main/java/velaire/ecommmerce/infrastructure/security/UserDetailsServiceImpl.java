package velaire.ecommmerce.infrastructure.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import velaire.ecommmerce.business.dtos.UserDTO;
import velaire.ecommmerce.infrastructure.client.UserClient;

public class UserDetailsServiceImpl {

    @Autowired
    private UserClient client;

        public UserDetails loadUserData(String email, String token){

        UserDTO userDTO = client.findUserByEmail(email, token);
        return User
                .withUsername(userDTO.getEmail()) // Define o nome de usuário como o e-mail
                .password(userDTO.getSenha()) // Define a senha do usuário
                .build();
        }
}
