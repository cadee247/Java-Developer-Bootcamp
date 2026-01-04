package eventai.eventai_backend.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

@Configuration // Marks this class as a Spring security configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        // Used to hash passwords before saving them to the database
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // Disable CSRF protection (commonly done for APIs)
                .csrf(csrf -> csrf.disable())

                // Allow all requests without authentication
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll()
                )

                // Disable default login form
                .formLogin(login -> login.disable())

                // Disable HTTP Basic authentication
                .httpBasic(basic -> basic.disable());

        // Build and return the security configuration
        return http.build();
    }
}
