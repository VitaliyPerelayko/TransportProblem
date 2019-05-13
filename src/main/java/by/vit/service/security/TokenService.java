package by.vit.service.security;

import org.springframework.security.core.Authentication;

/**
 * Service for JWT (JSON Web Token)
 */
public interface TokenService {

    /**
     * generate new token
     *
     * @param authentication token for an authentication request
     * @return token
     */
    String generate(Authentication authentication);

    /**
     * refresh old token
     *
     * @param token JWT
     * @return new token
     */
    String refresh(String token);

    /**
     * extract username from token
     *
     * @param token JWT
     * @return username
     */
    String extractUsername(String token);

    /**
     * validate token
     *
     * @param authToken JWT
     * @return true or trows Exception
     */
    boolean validate(String authToken);
}
