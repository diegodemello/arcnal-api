package br.com.arcnal.arcnal.infra.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfigurations {

    @Autowired
    SecurityFilter securityFilter;

    @Autowired
    CustomAuthEntryPoint customAuthEntryPoint;

    @Autowired
    CustomBearerAuthEntryPoint customBearerAuthEntryPoint;

    @Autowired
    CustomBearerAccessDeniedHandler customBearerAccessDeniedHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(
                        SessionCreationPolicy.STATELESS
                ))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.POST, "/questao/*/responder").authenticated()
                        .requestMatchers(HttpMethod.GET, "/questao/*/resolucao").authenticated()

                        .requestMatchers(HttpMethod.POST, "/questao").hasAnyRole("PROFESSOR", "ADMIN")
                        .requestMatchers(HttpMethod.POST, "/materia").hasAnyRole("PROFESSOR", "ADMIN")
                        .requestMatchers(HttpMethod.POST, "/banca").hasAnyRole("PROFESSOR", "ADMIN")
                        .requestMatchers(HttpMethod.POST, "/assunto").hasAnyRole("PROFESSOR", "ADMIN")

                        .requestMatchers(HttpMethod.GET, "/usuario/listar").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.GET, "/questao", "/questao/filtro").permitAll()
                        .requestMatchers(HttpMethod.GET, "/materia").permitAll()
                        .requestMatchers(HttpMethod.GET, "/assunto/materia/listar/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/banca").permitAll()
                        .requestMatchers(HttpMethod.POST, "/usuario/cadastrar", "/usuario/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/senha/recuperar", "/senha/redefinir").permitAll()
                        .requestMatchers(HttpMethod.GET, "/actuator/*", "/actuator/metrics/*").permitAll() // Não sobe para PROD

                        .requestMatchers(HttpMethod.GET, "/v3/api-docs/**",
                                "/swagger-ui/**").permitAll()

                        .anyRequest().authenticated())
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .httpBasic(httpBasic -> httpBasic.authenticationEntryPoint(customAuthEntryPoint))
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(this.customBearerAuthEntryPoint)
                        .accessDeniedHandler(customBearerAccessDeniedHandler))
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
