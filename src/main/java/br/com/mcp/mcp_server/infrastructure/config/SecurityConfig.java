package br.com.mcp.mcp_server.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Opcional: desabilita CSRF para APIs
            .authorizeHttpRequests(auth -> auth
                .anyRequest().hasRole("CLIENT") // compatível com .roles("CLIENT")
            )
            .httpBasic();

        return http.build();
    }

    @Bean
    public UserDetailsService users() {
        return new InMemoryUserDetailsManager(
            User.withUsername("client")
                .password("{noop}secret") // Apenas para testes
                .roles("CLIENT")
                .build()
        );
    }
}
