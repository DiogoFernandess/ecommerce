package velaire.ecommmerce.business.converter;

import org.springframework.stereotype.Component;
import velaire.ecommmerce.business.dtos.UserDTO;
import velaire.ecommmerce.infrastructure.entity.User;

import java.util.List;
import java.util.UUID;

@Component
public class UserConverter {
    public User toUser(UserDTO userDto){
        return User.builder()
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .role(userDto.getRole())
                .build();
    }

    //Parâmetros utilizando entity's
    public UserDTO toUserDto(User user){
        return UserDTO.builder()
                .email(user.getEmail())
                .password(user.getPassword())
                .role(user.getRole())
                .build();
    }

    public User updateUser (UserDTO dto, User entity){
        return User.builder()
                .id(entity.getId())
                .password(dto.getPassword()!=null ? dto.getPassword() : entity.getPassword())
                .email(dto.getEmail()!=null ? dto.getEmail() : entity.getEmail())
                .build();
    }
}