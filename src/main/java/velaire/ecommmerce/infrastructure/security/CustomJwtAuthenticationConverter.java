package velaire.ecommmerce.infrastructure.security;

import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
    public class CustomJwtAuthenticationConverter implements Converter<Jwt, AbstractAuthenticationToken> {

        @Override
        public AbstractAuthenticationToken convert(Jwt jwt) {
            // 1. Extraímos as authorities (roles) do token
            Collection<GrantedAuthority> authorities = extractAuthorities(jwt);

            // 2. Retornamos o token de autenticação pronto para o Spring usar
            return new JwtAuthenticationToken(jwt, authorities);
        }

        private Collection<GrantedAuthority> extractAuthorities(Jwt jwt) {
            // ATENÇÃO: O nome "roles" aqui deve bater EXATAMENTE com o nome do campo
            // onde sua API de Login coloca as permissões dentro do Token JWT.
            // Se a sua API de login chamar de "authorities" ou "perfil", mude aqui.
            if (jwt.hasClaim("roles")) {
                List<String> roles = jwt.getClaimAsStringList("roles");

                return roles.stream()
                        .map(role -> {
                            // Garante que a role tenha o prefixo "ROLE_"
                            // Se a API de login já manda "ROLE_ADMIN", não precisa concatenar de novo.
                            if (role.startsWith("ROLE_")) {
                                return new SimpleGrantedAuthority(role);
                            } else {
                                return new SimpleGrantedAuthority("ROLE_" + role.toUpperCase());
                            }
                        })
                        .collect(Collectors.toList());
            }

            // Se não tiver a claim "roles", retorna uma lista vazia (usuário sem permissões especiais)
            return List.of();
        }
 }
