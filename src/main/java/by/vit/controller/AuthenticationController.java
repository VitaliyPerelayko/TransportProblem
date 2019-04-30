package by.vit.controller;

import by.vit.component.LocalizedMessageSource;
import by.vit.dto.TransporterRegistrationRequestDTO;
import by.vit.dto.UserRegistrationRequestDTO;
import by.vit.dto.response.TokenResponseDTO;
import by.vit.model.Transporter;
import by.vit.model.User;
import by.vit.service.RoleService;
import by.vit.service.TransporterService;
import by.vit.service.UserService;
import by.vit.service.security.TokenService;
import org.dozer.Mapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/authentication")
public class AuthenticationController {
    private final UserService userService;

    private final TransporterService transporterService;

    private final RoleService roleService;

    private final TokenService tokenService;

    private final PasswordEncoder encoder;

    private final AuthenticationManager authenticationManager;

    private final Mapper mapper;

    private final LocalizedMessageSource localizedMessageSource;

    public AuthenticationController(UserService userService, TransporterService transporterService,
                                    RoleService roleService, TokenService tokenService,
                                    PasswordEncoder encoder, AuthenticationManager authenticationManager,
                                    Mapper mapper, LocalizedMessageSource localizedMessageSource) {
        this.userService = userService;
        this.transporterService = transporterService;
        this.roleService = roleService;
        this.tokenService = tokenService;
        this.encoder = encoder;
        this.authenticationManager = authenticationManager;
        this.mapper = mapper;
        this.localizedMessageSource = localizedMessageSource;
    }

    @GetMapping("/signIn")
    public TokenResponseDTO authenticateUser(@RequestParam String username, @RequestParam String password) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);
        Authentication authentication = authenticationManager.authenticate(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new TokenResponseDTO(tokenService.generate(authentication));
    }

    @PostMapping("/refresh")
    public TokenResponseDTO refreshToken(@RequestParam String token) {
        return new TokenResponseDTO(tokenService.refresh(token));
    }

    @PostMapping("/signUp")
    public User registerUser(@Valid @RequestBody UserRegistrationRequestDTO userRegistrationRequestDTO) {
        final boolean isTransporter = userRegistrationRequestDTO.getRoles().contains("TRANSPORTER");
        if (isTransporter){
            throw new RuntimeException(
                    localizedMessageSource.getMessage("{user.role.notTransporter}",new Object[]{})
            );
        }
        final User user = mapper.map(userRegistrationRequestDTO,User.class);

//        user.setUsername(userRegistrationRequestDTO.getUsername());
//        user.setPassword(encoder.encode(userRegistrationRequestDTO.getPassword()));
//        final Set<Role> roles = userRegistrationRequestDTO.getRoles().stream()
//                .map(roleService::findByName)
//                .filter(Objects::nonNull)
//                .collect(Collectors.toSet());
//        user.setRoles(roles);
        return userService.save(user);
    }

    @PostMapping("/signUpTransporter")
    public User registerTransporter(@Valid @RequestBody TransporterRegistrationRequestDTO transporterRegistrationRequestDTO) {
        final boolean isTransporter = transporterRegistrationRequestDTO.getRoles().contains("TRANSPORTER");
        if (!isTransporter){
            throw new RuntimeException(
                    localizedMessageSource.getMessage("{transporter.role.notTransporter}",new Object[]{})
            );
        }
        final Transporter transporter = mapper.map(transporterRegistrationRequestDTO,Transporter.class);

        return transporterService.save(transporter);
    }
}
