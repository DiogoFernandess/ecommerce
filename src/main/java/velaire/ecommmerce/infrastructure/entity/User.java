package velaire.ecommmerce.infrastructure.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import velaire.ecommmerce.business.dtos.RoleEnum;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
@Builder
public class User implements UserDetails {

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        @Column(columnDefinition = "UUID", updatable = false, nullable = false)
        private UUID id;

        @Column (name = "email" , length = 100)
        private String email;

        @Column (name = "password" , length = 100)
        private String password;

        @Enumerated(EnumType.STRING)
        @Column(nullable = false)
        private RoleEnum role = RoleEnum.USER;


        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {

            return List.of(new SimpleGrantedAuthority("ROLE_" + this.role.name()));
        }

        @Override
        public String getPassword() {
            return password;
        }

        @Override
        public String getUsername() {
            return email;
        }

    }
