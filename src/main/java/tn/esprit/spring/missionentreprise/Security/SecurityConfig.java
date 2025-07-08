package tn.esprit.spring.missionentreprise.Security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity(securedEnabled = true )
public class SecurityConfig {
    private final AuthenticationProvider authenticationProvider;
    private final JwtFilter jwtAuthFilter;



    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(request -> {
                    CorsConfiguration config = new CorsConfiguration();
                    config.setAllowedOrigins(List.of("http://localhost:4200")); // ✅ Pas '*'
                    config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE","PATCH", "OPTIONS"));
                    config.setAllowedHeaders(List.of("*"));
                    config.setAllowCredentials(true); // ✅ Obligatoire si tu as un JWT ou cookie
                    config.addExposedHeader("Authorization"); // si tu veux que le header soit visible
                    return config;
                }))

                .csrf(csrf -> csrf.disable())  // Keep CSRF disabled for stateless API
                .authorizeHttpRequests(req ->

                        req.requestMatchers("/auth/**", "/register", "/ws/**", "/topic/**").permitAll()


                                .requestMatchers("/messages/**", "/groupes/**", "/users/**", "/groupe/**"
                                ).authenticated()
                                .requestMatchers("/api/taches/**").authenticated()
                                .requestMatchers("/api/taches/files/upload").authenticated()
                                .requestMatchers("/api/sousTaches/**").authenticated()
                                .requestMatchers("/api/groupe/**").hasAnyAuthority("ADMINISTRATEUR", "ENSEIGNANT","COORDINATEUR")
                                .anyRequest().authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }




}
