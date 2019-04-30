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

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    private final TokenService tokenService;

    private final UserDetailsService userDetailsService;

    public SecurityConfiguration(TokenService tokenService, UserDetailsService userDetailsService) {
        this.tokenService = tokenService;
        this.userDetailsService = userDetailsService;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().cors()
                .and()
                .authorizeRequests()
                .mvcMatchers("/authentication/**").permitAll()
                .mvcMatchers("/points/**").permitAll()
                .mvcMatchers(HttpMethod.GET,"/cars/**","/roads/**","/carModels/**").permitAll()
                .mvcMatchers(HttpMethod.POST,"/solution").hasRole("SUPPLIER")
                .mvcMatchers("/cars/**").hasRole("TRANSPORTER")
                .mvcMatchers(HttpMethod.POST,"/carModels/**").hasRole("TRANSPORTER")
                .mvcMatchers(HttpMethod.GET, "/roles/**", "/users/**").hasAnyRole("ADMIN", "USER")
                .anyRequest().hasRole("ADMIN");
        http.addFilterBefore(new AuthenticationTokenFilter(tokenService, userDetailsService),
                UsernamePasswordAuthenticationFilter.class);
    }
}
