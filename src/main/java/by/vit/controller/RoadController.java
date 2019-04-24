package by.vit.controller;

import by.vit.component.LocalizedMessageSource;
import by.vit.dto.request.RoadRequestDTO;
import by.vit.dto.response.RoadResponseDTO;
import by.vit.model.Road;
import by.vit.service.RoadService;
import org.dozer.Mapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/roads")
public class RoadController {

    private final Mapper mapper;

    private final RoadService roadService;

    private final LocalizedMessageSource localizedMessageSource;

    public RoadController(Mapper mapper, RoadService roadService,
                           LocalizedMessageSource localizedMessageSource) {
        this.mapper = mapper;
        this.roadService = roadService;
        this.localizedMessageSource = localizedMessageSource;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<RoadResponseDTO>> getAll() {
        final List<Road> roads = roadService.findAll();
        final List<RoadResponseDTO> RoadDTOList = roads.stream()
                .map((road) -> mapper.map(road, RoadResponseDTO.class))
                .collect(Collectors.toList());
        return new ResponseEntity<>(RoadDTOList, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<RoadResponseDTO> getOne(@PathVariable Long[] id) {
        final RoadResponseDTO RoadDTO = mapper.map(roadService.findById(id), RoadResponseDTO.class);
        return new ResponseEntity<>(RoadDTO, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<RoadResponseDTO> save(@Valid @RequestBody RoadRequestDTO roadRequestDto) {
        roadRequestDto.setId(null);
        final Road road = mapper.map(roadRequestDto, Road.class);
        final RoadResponseDTO RoadDTO = mapper.map(roadService.save(road), RoadResponseDTO.class);
        return new ResponseEntity<>(RoadDTO, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<RoadResponseDTO> update(@Valid @RequestBody RoadRequestDTO roadRequestDto,
                                                  @PathVariable Long id) {
        if (!Objects.equals(id, roadRequestDto.getId())) {
            throw new RuntimeException(localizedMessageSource.getMessage("controller.road.unexpectedId", new Object[]{}));
        }
        final Road road = mapper.map(roadRequestDto, Road.class);
        final RoadResponseDTO RoadDTO = mapper.map(roadService.update(road), RoadResponseDTO.class);
        return new ResponseEntity<>(RoadDTO, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.OK)
    public void delete(@PathVariable Long id) {
        roadService.deleteById(id);
    }
}
