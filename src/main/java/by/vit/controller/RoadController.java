package by.vit.controller;

import by.vit.component.LocalizedMessageSource;
import by.vit.dto.request.RoadRequestDTO;
import by.vit.dto.response.RoadResponseDTO;
import by.vit.mapping.Mapping;
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
/**
 * Controller for entity Road.
 */
@RestController
@RequestMapping("/roads")
public class RoadController {

    private final Mapping mapping;

    private final RoadService roadService;

    private final LocalizedMessageSource localizedMessageSource;

    public RoadController(Mapping mapping, RoadService roadService,
                          LocalizedMessageSource localizedMessageSource) {
        this.mapping = mapping;
        this.roadService = roadService;
        this.localizedMessageSource = localizedMessageSource;
    }

    /**
     * Gets all roads from database.
     *
     * @return Response: RoadDTO ant http status
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<RoadResponseDTO>> getAll() {
        final List<Road> roads = roadService.findAll();
        final List<RoadResponseDTO> RoadDTOList = roads.stream()
                .map(mapping::mapRoadToRoadResponseDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(RoadDTOList, HttpStatus.OK);
    }

    /**
     * Gets road by id
     *
     * @param id1 of point
     * @param id2 of point
     * @return Response: RoadDTO ant http status
     */
    @RequestMapping(value = "/{id1}/{id2}", method = RequestMethod.GET)
    public ResponseEntity<RoadResponseDTO> getOne(@PathVariable Long id1, @PathVariable Long id2) {
        final RoadResponseDTO roadResponseDTO = mapping.mapRoadToRoadResponseDTO(roadService.findById(
                mapping.mapToRoadId(id1, id2)));
        return new ResponseEntity<>(roadResponseDTO, HttpStatus.OK);
    }

    /**
     * Save road to database
     *
     * @param roadRequestDto new road
     * @return Response:saved RoadDTO ant http status
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<RoadResponseDTO> save(@Valid @RequestBody RoadRequestDTO roadRequestDto) {
        final RoadResponseDTO RoadDTO = mapping.mapRoadToRoadResponseDTO(roadService.save(
                mapping.mapRoadRequestDTOToRoad(roadRequestDto)));
        return new ResponseEntity<>(RoadDTO, HttpStatus.OK);
    }

    /**
     * Update road in database
     *
     * @param roadRequestDto new road
     * @param id1 of point in database
     * @param id2 of point in database
     * @return Response:updated RoadDTO ant http status
     */
    @RequestMapping(value = "/{id1/{id2}", method = RequestMethod.PUT)
    public ResponseEntity<RoadResponseDTO> update(@Valid @RequestBody RoadRequestDTO roadRequestDto,
                                                  @PathVariable Long id1, @PathVariable Long id2) {
        if (!Objects.equals(id1, roadRequestDto.getPoint1id()) || !Objects.equals(id2, roadRequestDto.getPoint2id())) {
            throw new RuntimeException(localizedMessageSource.getMessage("controller.road.unexpectedId", new Object[]{}));
        }
        final RoadResponseDTO RoadDTO = mapping.mapRoadToRoadResponseDTO(roadService.save(
                mapping.mapRoadRequestDTOToRoad(roadRequestDto)));
        return new ResponseEntity<>(RoadDTO, HttpStatus.OK);
    }

    /**
     * Delete road from database
     *
     * @param id1 of point
     * @param id2 of point
     * @return http status
     */
    @RequestMapping(value = "/{id1}/{id2}", method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.OK)
    public void delete(@PathVariable Long id1, @PathVariable Long id2) {
        roadService.deleteById(mapping.mapToRoadId(id1, id2));
    }
}
