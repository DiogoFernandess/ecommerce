package velaire.ecommmerce.infrastructure.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import velaire.ecommmerce.business.dtos.UserDTO;
import velaire.ecommmerce.infrastructure.entity.User;
import velaire.ecommmerce.infrastructure.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Busca o usuário no banco de dados pelo e-mail
        User user = userRepository.findByEmail(email)
                .orElseThrow();

        // Cria e retorna um objeto UserDetails com base no usuário encontrado
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername()) // Define o nome de usuário como o e-mail
                .password(user.getPassword())
                .authorities(user.getRole().name())// Define a senha do usuário
                .build(); // Constrói o objeto UserDetails
    }
}
