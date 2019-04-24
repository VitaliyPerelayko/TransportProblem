package by.vit.controller;

import by.vit.component.LocalizedMessageSource;
import by.vit.dto.PointDTO;
import by.vit.model.Point;
import by.vit.service.PointService;
import org.dozer.Mapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
    public ResponseEntity<List<PointDTO>> getAll() {
        final List<Point> points = pointService.findAll();
        final List<PointDTO> pointDTOList = points.stream()
                .map((point) -> mapper.map(point, PointDTO.class))
                .collect(Collectors.toList());
        return new ResponseEntity<>(pointDTOList, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<PointDTO> getOne(@PathVariable Long id) {
        final PointDTO pointDTO = mapper.map(pointService.findById(id), PointDTO.class);
        return new ResponseEntity<>(pointDTO, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<PointDTO> save(@Valid @RequestBody PointDTO pointRequestDto) {
        pointRequestDto.setId(null);
        final Point point = mapper.map(pointRequestDto, Point.class);
        final PointDTO pointDTO = mapper.map(pointService.save(point), PointDTO.class);
        return new ResponseEntity<>(pointDTO, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<PointDTO> update(@Valid @RequestBody PointDTO pointRequestDto, @PathVariable Long id) {
        if (!Objects.equals(id, pointRequestDto.getId())) {
            throw new RuntimeException(localizedMessageSource.getMessage("controller.point.unexpectedId", new Object[]{}));
        }
        final Point point = mapper.map(pointRequestDto, Point.class);
        final PointDTO pointDTO = mapper.map(pointService.update(point), PointDTO.class);
        return new ResponseEntity<>(pointDTO, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.OK)
    public void delete(@PathVariable Long id) {
        pointService.deleteById(id);
    }
}
