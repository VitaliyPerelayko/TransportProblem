package by.vit.controller;

import by.vit.dto.request.AuthenticationRequestDTO;
import by.vit.dto.request.UserRequestDTO;
import by.vit.dto.response.TokenResponseDTO;
import by.vit.mapping.Mapping;
import by.vit.model.User;
import by.vit.service.UserService;
import by.vit.service.security.TokenService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Authentication Controller
 */
@RestController
@RequestMapping("/authentication")
public class AuthenticationController {
    private final UserService userService;

    private final TokenService tokenService;
    private final PasswordEncoder encoder;
    private final AuthenticationManager authenticationManager;
    private final Mapping mapping;



    public AuthenticationController(UserService userService, TokenService tokenService,
                                    PasswordEncoder encoder, AuthenticationManager authenticationManager,
                                    Mapping mapping) {
        this.userService = userService;
        this.tokenService = tokenService;
        this.encoder = encoder;
        this.authenticationManager = authenticationManager;
        this.mapping = mapping;
    }

    /**
     * Authenticate user, and return token
     *
     * @param authenticationRequestDTO DTO contains username and password
     * @return TokenResponseDTO
     */
    @PostMapping("/signIn")
    public TokenResponseDTO authenticateUser(@Valid @RequestBody AuthenticationRequestDTO authenticationRequestDTO) {
        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(authenticationRequestDTO.getUsername(),
                        authenticationRequestDTO.getPassword());
        Authentication authentication = authenticationManager.authenticate(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new TokenResponseDTO(tokenService.generate(authentication));
    }

    /**
     * Refresh old token
     *
     * @param token old token
     * @return new token
     */
    @PostMapping("/refresh")
    public TokenResponseDTO refreshToken(@RequestParam String token) {
        return new TokenResponseDTO(tokenService.refresh(token));
    }

    /**
     * Register new user and save him to the database
     *
     * @param userRequestDTO userRequestDTO information about new user
     * @return saved User
     */
    @PostMapping("/signUp")
    public User registerUser(@Valid @RequestBody UserRequestDTO userRequestDTO) {
        final User user = mapping.mapUserRequestDTOTOUser(userRequestDTO);
        user.setPassword(encoder.encode(userRequestDTO.getPassword()));

        return userService.save(user);
    }

}
