package by.vit.controller;

import by.vit.component.LocalizedMessageSource;
import by.vit.dto.response.PointResponseDTO;
import by.vit.model.Point;
import by.vit.service.PointService;
import org.dozer.Mapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/points")
public class PointController {

    private final Mapper mapper;

    private final PointService pointService;

    private final LocalizedMessageSource localizedMessageSource;

    public PointController(Mapper mapper, PointService pointService,
                           LocalizedMessageSource localizedMessageSource) {
        this.mapper = mapper;
        this.pointService = pointService;
        this.localizedMessageSource = localizedMessageSource;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<PointResponseDTO>> getAll() {
        final List<Point> users = pointService.findAll();
        final List<UserResponseDto> userResponseDtoList = users.stream()
                .map((user) -> mapper.map(user, UserResponseDto.class))
                .collect(Collectors.toList());
        return new ResponseEntity<>(userResponseDtoList, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<UserResponseDto> getOne(@PathVariable Long id) {
        final UserResponseDto userResponseDto = mapper.map(userService.findById(id), UserResponseDto.class);
        return new ResponseEntity<>(userResponseDto, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<UserResponseDto> save(@Valid @RequestBody UserRequestDto userRequestDto) {
        userRequestDto.setId(null);
        final UserResponseDto userResponseDto = mapper.map(userService.save(getUser(userRequestDto)), UserResponseDto.class);
        return new ResponseEntity<>(userResponseDto, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<UserResponseDto> update(@Valid @RequestBody UserRequestDto userRequestDto, @PathVariable Long id) {
        if (!Objects.equals(id, userRequestDto.getId())) {
            throw new RuntimeException(localizedMessageSource.getMessage("controller.user.unexpectedId", new Object[]{}));
        }
        final UserResponseDto userResponseDto = mapper.map(userService.update(getUser(userRequestDto)), UserResponseDto.class);
        return new ResponseEntity<>(userResponseDto, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.OK)
    public void delete(@PathVariable Long id) {
        userService.deleteById(id);
    }


}
