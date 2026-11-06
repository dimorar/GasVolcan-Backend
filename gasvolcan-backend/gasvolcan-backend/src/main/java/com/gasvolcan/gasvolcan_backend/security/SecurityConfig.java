package gasvolcan.config;

import gasvolcan.security.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    SecurityFilterChain securityFilterChain(
        HttpSecurity http
    ) throws Exception {

        http
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session ->
                session.sessionCreationPolicy(
                    SessionCreationPolicy.STATELESS
                )
            )
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/v1/auth/**").permitAll()
                .requestMatchers(
                    HttpMethod.GET,
                    "/api/v1/productos/**",
                    "/api/v1/categorias/**"
                ).permitAll()
                .requestMatchers(
                    "/api/v1/productos/**",
                    "/api/v1/categorias/**"
                ).hasAnyRole("ADMINISTRADOR", "VENDEDOR")
                .requestMatchers("/api/v1/boletas/**").authenticated()
                .anyRequest().authenticated()
            )
            .httpBasic(basic -> basic.disable())
            .formLogin(form -> form.disable())
            .addFilterBefore(
                jwtFilter,
                UsernamePasswordAuthenticationFilter.class
            );

        return http.build();
    }
}