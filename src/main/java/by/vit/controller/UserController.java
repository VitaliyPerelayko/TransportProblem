package by.vit.controller;

import by.vit.component.LocalizedMessageSource;
import by.vit.dto.request.UserRequestDTO;
import by.vit.dto.response.UserResponseDTO;
import by.vit.model.User;
import by.vit.service.UserService;
import org.dozer.Mapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Controller for entity User.
 */
@RestController
@RequestMapping("/users")
public class UserController {

    private final Mapper mapper;

    private final UserService userService;

    private final LocalizedMessageSource localizedMessageSource;

    public UserController(Mapper mapper, UserService userService,
                          LocalizedMessageSource localizedMessageSource) {
        this.mapper = mapper;
        this.userService = userService;
        this.localizedMessageSource = localizedMessageSource;
    }

    /**
     * Gets all users from database.
     *
     * @return Response: UserDTO ant http status
     */
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<UserResponseDTO>> getAll() {
        final List<User> users = userService.findAll();
        final List<UserResponseDTO> userDTOList = users.stream()
                .map((user) -> mapper.map(user, UserResponseDTO.class))
                .collect(Collectors.toList());
        return new ResponseEntity<>(userDTOList, HttpStatus.OK);
    }

    /**
     * Gets user by id
     *
     * @param id of user
     * @return Response: UserDTO ant http status
     */
    @PreAuthorize("hasRole('ADMIN') or " +
            "(userServiceImpl.findById(#id).username.equals(authentication.name))")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<UserResponseDTO> getOne(@PathVariable Long id) {
        final UserResponseDTO userResponseDTO = mapper.map(userService.findById(id), UserResponseDTO.class);
        return new ResponseEntity<>(userResponseDTO, HttpStatus.OK);
    }

    /**
     * Save user to database
     *
     * @param userRequestDto new user
     * @return Response:saved UserDTO ant http status
     */
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<UserResponseDTO> save(@Valid @RequestBody UserRequestDTO userRequestDto) {
        userRequestDto.setId(null);
        final User user = mapper.map(userRequestDto, User.class);
        final UserResponseDTO userResponseDTO = mapper.map(userService.save(user), UserResponseDTO.class);
        return new ResponseEntity<>(userResponseDTO, HttpStatus.OK);
    }

    /**
     * Update user in database
     *
     * @param userRequestDto new user
     * @param id             of user in database
     * @return Response:updated UserDTO ant http status
     */
    @PreAuthorize("hasRole('ADMIN') or " +
            "(userServiceImpl.findById(#id).username.equals(authentication.name))")
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<UserResponseDTO> update(@Valid @RequestBody UserRequestDTO userRequestDto, @PathVariable Long id) {
        if (!Objects.equals(id, userRequestDto.getId())) {
            throw new RuntimeException(localizedMessageSource.getMessage("controller.user.unexpectedId", new Object[]{}));
        }
        final User user = mapper.map(userRequestDto, User.class);
        final UserResponseDTO userResponseDTO = mapper.map(userService.update(user), UserResponseDTO.class);
        return new ResponseEntity<>(userResponseDTO, HttpStatus.OK);
    }

    /**
     * Delete user from database
     *
     * @param id of user
     * @return http status
     */
    @PreAuthorize("hasRole('ADMIN') or " +
            "(userServiceImpl.findById(#id).username.equals(authentication.name))")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.OK)
    public void delete(@PathVariable Long id) {
        userService.deleteById(id);
    }
}
