package velaire.ecommmerce.business.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import velaire.ecommmerce.business.converter.UserConverter;
import velaire.ecommmerce.business.dtos.RoleEnum;
import velaire.ecommmerce.business.dtos.UserDTO;
import org.springframework.security.crypto.password.PasswordEncoder;
import velaire.ecommmerce.infrastructure.entity.User;
import velaire.ecommmerce.infrastructure.exceptions.ConflictException;
import velaire.ecommmerce.infrastructure.exceptions.ResourceNotFoundException;
import velaire.ecommmerce.infrastructure.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserConverter userConverter;
    private final PasswordEncoder passwordEncoder;

    public UserDTO saveUser(UserDTO dto){
        emailExist(dto.getEmail());
        dto.setPassword(passwordEncoder.encode(dto.getPassword()));
        dto.setRole(RoleEnum.USER);
        User user = userConverter.toUser(dto);
        return userConverter.toUserDto(userRepository.save(user));
    }

    public void emailExist(String email){
        try {
            boolean exite = verifyEmailExist(email);
            if (exite) {
                throw new ConflictException("Email já cadastrado" + email);
            }
        } catch (ConflictException e){
            throw new ConflictException("Email já cadastrado" + e.getCause());
        }
    }

    public boolean verifyEmailExist(String email){
        return userRepository.existsByEmail(email);
    }

    public UserDTO findUserByEmail(String email) {
        try {
            return userConverter.toUserDto(
                    userRepository.findByEmail(email)
                            .orElseThrow(
                                    () -> new ResourceNotFoundException("Email não encontrado" + email)
                            )
            );
        } catch (ResourceNotFoundException e ){
            throw new ResourceNotFoundException("Email não encontrado" +email);
        }
    }

}
