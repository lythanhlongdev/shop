package com.ltldev.shop.configuration;


import com.ltldev.shop.filters.JwtTokenFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class WebSecurityConfig {

    @Value("${api.prefix}")
    private String apiPrefix;

    private final JwtTokenFilter jwtTokenFilter;

    // lọc quyền
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(request -> {
                    request
                            .requestMatchers(
                                    String.format("%s/users/register", apiPrefix),
                                    String.format("%s/users/login", apiPrefix)
                            )
                            .permitAll()
                            .requestMatchers(HttpMethod.GET,String.format("/%s/orders/**",apiPrefix)).hasAnyRole("USER","ADMIN")
                            .requestMatchers(HttpMethod.POST,String.format("/%s/orders/**",apiPrefix)).hasRole("USER")
                            .requestMatchers(HttpMethod.PUT,String.format("/%s/orders/**",apiPrefix)).hasRole("ADMIN")
                            .requestMatchers(HttpMethod.DELETE,String.format("/%s/orders/**",apiPrefix)).hasRole("ADMIN")
                            .anyRequest().authenticated();
                    ;
                });
        return http.build();
    }
}
