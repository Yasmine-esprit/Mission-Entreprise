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
<<<<<<< HEAD
                        req.requestMatchers("/auth/**", "/register","/ws/**").permitAll()  // Public endpoints
                                
                                // ✅ Accès utilisateur authentifié (nécessaire pour navbar et profil)
                                .requestMatchers("/users/me").authenticated()
                                .requestMatchers("/users/**").hasAnyRole("ADMINISTRATEUR", "COORDINATEUR")
                                
                                // ✅ CRUD Institution/Département/Classe/Groupe pour ENSEIGNANT, COORDINATEUR, ADMIN
                                .requestMatchers("/api/institutions/**").hasAnyRole("ENSEIGNANT", "COORDINATEUR", "ADMINISTRATEUR")
                                .requestMatchers("/api/departements/**").hasAnyRole("ENSEIGNANT", "COORDINATEUR", "ADMINISTRATEUR")
                                .requestMatchers("/api/classes/**").hasAnyRole("ENSEIGNANT", "COORDINATEUR", "ADMINISTRATEUR")
                                .requestMatchers("/api/groupes/**").hasAnyRole("ENSEIGNANT", "COORDINATEUR", "ADMINISTRATEUR")
                                
                                // ✅ Accès lecture seule pour tous les utilisateurs authentifiés
                                .requestMatchers("/api/institutions", "/api/departements", "/api/classes", "/api/groupes").authenticated()
                                
                                // ✅ Gestion des thèmes pour ENSEIGNANT et COORDINATEUR
                                .requestMatchers("/api/themes/**").hasAnyRole("ENSEIGNANT", "COORDINATEUR", "ADMINISTRATEUR")
                                .requestMatchers("/api/themes").authenticated()
                                
                                // ✅ Gestion des tâches pour ENSEIGNANT et COORDINATEUR
                                .requestMatchers("/api/taches/**").hasAnyRole("ENSEIGNANT", "COORDINATEUR", "ADMINISTRATEUR")
                                .requestMatchers("/api/taches/all").hasAnyRole("ENSEIGNANT", "COORDINATEUR", "ADMINISTRATEUR")
                                
                                // ✅ Gestion des projets
                                .requestMatchers("/api/projets/**").hasAnyRole("ENSEIGNANT", "COORDINATEUR", "ADMINISTRATEUR")
                                
                                // ✅ Messages et discussions
                                .requestMatchers("/messages/**").authenticated()
                                .requestMatchers("/groupes/**").authenticated()
                                
                                // ✅ Endpoints étudiant pour choix
                                .requestMatchers("/api/etudiants/choisir-classe/**").hasRole("ETUDIANT")
                                .requestMatchers("/api/etudiants/choisir-groupe/**").hasRole("ETUDIANT")
                                .requestMatchers("/api/etudiants/choisir-theme/**").hasRole("ETUDIANT")
                                .requestMatchers("/api/etudiants/mon-choix").hasRole("ETUDIANT")
                                
=======
                        req.requestMatchers("/auth/**", "/register", "/ws/**"// Keep this public if needed
                                ).permitAll()
                                .requestMatchers("/messages/**", "/groupes/**", "/users/**", "/groupe/**"
                                ).authenticated()
                                .requestMatchers("/api/taches/**").authenticated()
                                .requestMatchers("/api/taches/files/upload").authenticated()
                                .requestMatchers("/api/sousTaches/**").authenticated()
                                .requestMatchers("/api/groupe/**").hasAnyAuthority("ADMINISTRATEUR", "ENSEIGNANT","COORDINATEUR")
>>>>>>> 800784042b3a6f6955d33992fcb8e5a432132e7f
                                .anyRequest().authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }




}
