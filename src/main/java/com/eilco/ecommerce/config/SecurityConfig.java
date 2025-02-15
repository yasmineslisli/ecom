package com.eilco.ecommerce.config;
import com.eilco.ecommerce.filter.JwtAuthenticationFilter;
import com.eilco.ecommerce.service.UserDetailsServiceImp;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.HiddenHttpMethodFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    private final UserDetailsServiceImp userDetailsServiceImp;

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    private final CustomLogoutHandler logoutHandler;

    public SecurityConfig(UserDetailsServiceImp userDetailsServiceImp,
                          JwtAuthenticationFilter jwtAuthenticationFilter,
                          CustomLogoutHandler logoutHandler) {
        this.userDetailsServiceImp = userDetailsServiceImp;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.logoutHandler = logoutHandler;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        req -> req
                                .requestMatchers("/", "/index", "/css/**", "/js/**", "/images/**").permitAll()
                                .requestMatchers("/login/**", "/register/**", "/refresh_token/**").permitAll()
                                .requestMatchers("/css/**", "/js/**", "/images/**", "/favicon.ico").permitAll()

                                .requestMatchers("/categories/add", "/products/add").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.POST, "/categories/**", "/products/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/categories/**", "/products/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/categories/**", "/products/**").hasRole("ADMIN")

                                // Order permissions
                                .requestMatchers("/orders/my-orders", "/orders/create", "/orders/paymentform", "/orders/payment", "/orders/success/**")
                                .hasRole("USER") // Only users can access their orders and payments
                                .requestMatchers(HttpMethod.POST, "/orders/create").hasRole("USER") // Users can create orders
                                .requestMatchers(HttpMethod.POST, "/orders/validate/**").hasRole("ADMIN") // Only admins can validate orders
                                .requestMatchers(HttpMethod.GET, "/orders/**").hasRole("ADMIN") // Admins can see all orders except /my-orders

                                .anyRequest()
                                .authenticated()
                )
                .userDetailsService(userDetailsServiceImp)
                .sessionManagement(session->session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(
                        e->e.accessDeniedHandler(
                                        (request, response, accessDeniedException)->response.setStatus(403)
                                )
                                .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)))
                .logout(l->l
                        .logoutUrl("/logout")
                        .addLogoutHandler(logoutHandler)
                        .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext()
                        ))
                .build();

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public HiddenHttpMethodFilter hiddenHttpMethodFilter() {
        return new HiddenHttpMethodFilter();
    }
}