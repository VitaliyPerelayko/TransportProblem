package by.vit.config;

import by.vit.security.filter.AuthenticationTokenFilter;
import by.vit.service.security.TokenService;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Security Configuration
 */
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    private final TokenService tokenService;

    private final UserDetailsService userDetailsService;

    public SecurityConfiguration(TokenService tokenService, UserDetailsService userDetailsService) {
        this.tokenService = tokenService;
        this.userDetailsService = userDetailsService;
    }

    /**
     * Gets the {@link AuthenticationManager} to use.
     *
     * @return the {@link AuthenticationManager} to use.
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * configure the {@link HttpSecurity}
     *
     * @param http the {@link HttpSecurity} to modify
     * @throws Exception if an error occurs
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().cors()
                .and()
                .authorizeRequests()
                // Swagger
                .mvcMatchers("/v2/api-docs", "/swagger-resources/configuration/ui", "/swagger-resources",
                        "/swagger-resources/configuration/security", "/api/swagger-ui.html/**", "/webjars/**").permitAll()
                // Authentication
                .mvcMatchers("/authentication/**").permitAll()
                .mvcMatchers(HttpMethod.GET,"/cars/**","/roads/**", "/carModels/**","/points/**").permitAll()
                // Solution
                .mvcMatchers("/solution/**").hasAnyRole("SUPPLIER","ADMIN")
                // Car
                .mvcMatchers(HttpMethod.POST,"/cars", "/carModels").hasAnyRole("TRANSPORTER","ADMIN")
                // User
                .mvcMatchers("/users/**").hasAnyRole("TRANSPORTER","SUPPLIER","ADMIN")
                .anyRequest().hasRole("ADMIN");
        http.addFilterBefore(new AuthenticationTokenFilter(tokenService, userDetailsService),
                UsernamePasswordAuthenticationFilter.class);
    }
}
