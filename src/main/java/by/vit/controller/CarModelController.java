package by.vit.controller;

import by.vit.component.LocalizedMessageSource;
import by.vit.dto.CarModelDTO;
import by.vit.model.CarModel;
import by.vit.service.CarModelService;
import org.dozer.Mapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Controller for entity CarModel.
 */
@RestController
@RequestMapping("/carModels")
public class CarModelController {

    private final Mapper mapper;

    private final CarModelService carModelService;

    private final LocalizedMessageSource localizedMessageSource;

    public CarModelController(Mapper mapper, CarModelService carModelService,
                              LocalizedMessageSource localizedMessageSource) {
        this.mapper = mapper;
        this.carModelService = carModelService;
        this.localizedMessageSource = localizedMessageSource;
    }

    /**
     * Gets all carModels from database.
     *
     * @return Response: CarModelDTO ant http status
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<CarModelDTO>> getAll() {
        final List<CarModel> carModels = carModelService.findAll();
        final List<CarModelDTO> carModelDTOList = carModels.stream()
                .map((carModel) -> mapper.map(carModel, CarModelDTO.class))
                .collect(Collectors.toList());
        return new ResponseEntity<>(carModelDTOList, HttpStatus.OK);
    }

    /**
     * Gets carModel by id
     *
     * @param id of carModel
     * @return Response: CarModelDTO ant http status
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<CarModelDTO> getOne(@PathVariable Long id) {
        final CarModelDTO carModelDTO = mapper.map(carModelService.findById(id), CarModelDTO.class);
        return new ResponseEntity<>(carModelDTO, HttpStatus.OK);
    }

    /**
     * Save carModel to database
     *
     * @param carModelRequestDto new carModel
     * @return Response: saved CarModelDTO ant http status
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<CarModelDTO> save(@Valid @RequestBody CarModelDTO carModelRequestDto) {
        carModelRequestDto.setId(null);
        final CarModel carModel = mapper.map(carModelRequestDto, CarModel.class);
        final CarModelDTO carModelDTO = mapper.map(carModelService.save(carModel), CarModelDTO.class);
        return new ResponseEntity<>(carModelDTO, HttpStatus.OK);
    }

    /**
     * Update carModel in database
     *
     * @param carModelRequestDto new carModel
     * @param id                 of carModel in database
     * @return Response: updated CarModelDTO ant http status
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<CarModelDTO> update(@Valid @RequestBody CarModelDTO carModelRequestDto,
                                              @PathVariable Long id) {
        if (!Objects.equals(id, carModelRequestDto.getId())) {
            throw new RuntimeException(
                    localizedMessageSource.getMessage("controller.carModel.unexpectedId", new Object[]{}));
        }
        final CarModel carModel = mapper.map(carModelRequestDto, CarModel.class);
        final CarModelDTO carModelDTO = mapper.map(carModelService.update(carModel), CarModelDTO.class);
        return new ResponseEntity<>(carModelDTO, HttpStatus.OK);
    }

    /**
     * Delete carModel from database
     *
     * @param id of carModel
     * @return http status
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.OK)
    public void delete(@PathVariable Long id) {
        carModelService.deleteById(id);
    }
}
