package by.vit.controller;

import by.vit.component.LocalizedMessageSource;
import by.vit.dto.RoleDTO;
import by.vit.model.Role;
import by.vit.service.RoleService;
import org.dozer.Mapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Controller for entity Role.
 */
@RestController
@RequestMapping("/roles")
public class RoleController {

    private final Mapper mapper;

    private final RoleService roleService;

    private final LocalizedMessageSource localizedMessageSource;

    public RoleController(Mapper mapper, RoleService roleService,
                          LocalizedMessageSource localizedMessageSource) {
        this.mapper = mapper;
        this.roleService = roleService;
        this.localizedMessageSource = localizedMessageSource;
    }

    /**
     * Gets all roles from database.
     *
     * @return Response: RoleDTO ant http status
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<RoleDTO>> getAll() {
        final List<Role> roles = roleService.findAll();
        final List<RoleDTO> roleDTOList = roles.stream()
                .map((role) -> mapper.map(role, RoleDTO.class))
                .collect(Collectors.toList());
        return new ResponseEntity<>(roleDTOList, HttpStatus.OK);
    }

    /**
     * Gets role by id
     *
     * @param id of role
     * @return Response: RoleDTO ant http status
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<RoleDTO> getOne(@PathVariable Long id) {
        final RoleDTO roleDTO = mapper.map(roleService.findById(id), RoleDTO.class);
        return new ResponseEntity<>(roleDTO, HttpStatus.OK);
    }

    /**
     * Save role to database
     *
     * @param roleRequestDto new role
     * @return Response:saved RoleDTO ant http status
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<RoleDTO> save(@Valid @RequestBody RoleDTO roleRequestDto) {
        roleRequestDto.setId(null);
        final Role role = mapper.map(roleRequestDto, Role.class);
        final RoleDTO roleDTO = mapper.map(roleService.save(role), RoleDTO.class);
        return new ResponseEntity<>(roleDTO, HttpStatus.OK);
    }

    /**
     * Update role in database
     *
     * @param roleRequestDto new role
     * @param id             of role in database
     * @return Response:updated RoleDTO ant http status
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<RoleDTO> update(@Valid @RequestBody RoleDTO roleRequestDto, @PathVariable Long id) {
        if (!Objects.equals(id, roleRequestDto.getId())) {
            throw new RuntimeException(localizedMessageSource.getMessage("controller.role.unexpectedId", new Object[]{}));
        }
        final Role role = mapper.map(roleRequestDto, Role.class);
        final RoleDTO roleDTO = mapper.map(roleService.update(role), RoleDTO.class);
        return new ResponseEntity<>(roleDTO, HttpStatus.OK);
    }

    /**
     * Delete role from database
     *
     * @param id of role
     * @return http status
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.OK)
    public void delete(@PathVariable Long id) {
        roleService.deleteById(id);
    }
}
