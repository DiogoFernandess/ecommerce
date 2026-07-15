package velaire.ecommmerce.business.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import velaire.ecommmerce.business.dtos.UserDTO;
import velaire.ecommmerce.infrastructure.exceptions.ConflictException;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserDTO saveUser(UserDTO dto){
        emailExist(dto.getEmail());
        dto.setPassword(passwordEncoder.encode(dto.getPassword()));
        dto.setRole(Role.USER);
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

    public UserDto findUserByEmail(String email) {
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
