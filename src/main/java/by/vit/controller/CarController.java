package by.vit.controller;

import by.vit.component.LocalizedMessageSource;
import by.vit.dto.request.CarRequestDTO;
import by.vit.dto.response.CarResponseDTO;
import by.vit.mapping.Mapping;
import by.vit.model.Car;
import by.vit.service.CarService;
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
 * Controller for entity Car.
 */
@RestController
@RequestMapping("/cars")
public class CarController {

    private final Mapping mapping;
    private final CarService carService;
    private final LocalizedMessageSource localizedMessageSource;

    public CarController(Mapping mapping, CarService carService,
                         LocalizedMessageSource localizedMessageSource) {
        this.mapping = mapping;
        this.carService = carService;
        this.localizedMessageSource = localizedMessageSource;
    }

    /**
     * Gets all cars from database.
     *
     * @return Response: CarDTO ant http status
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<CarResponseDTO>> getAll() {
        final List<Car> cars = carService.findAll();
        final List<CarResponseDTO> carDTOList = cars.stream()
                .map(mapping::mapCarToCarResponseDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(carDTOList, HttpStatus.OK);
    }

    /**
     * Gets car by id
     *
     * @param id of car
     * @return Response: CarDTO ant http status
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<CarResponseDTO> getOne(@PathVariable Long id) {
        final CarResponseDTO carResponseDTO = mapping.mapCarToCarResponseDTO(carService.findById(id));
        return new ResponseEntity<>(carResponseDTO, HttpStatus.OK);
    }

    /**
     * Save car to database
     *
     * @param carRequestDto new car
     * @return Response:saved CarDTO ant http status
     */
    @PreAuthorize("hasRole('ADMIN') or " +
            "(hasRole('TRANSPORTER') and userServiceImpl.findById(#carRequestDto.transporterId).username.equals(authentication.name))")
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<CarResponseDTO> save(@Valid @RequestBody CarRequestDTO carRequestDto) {
        carRequestDto.setId(null);
        final Car car = mapping.mapCarRequestDTOToCar(carRequestDto);
        final CarResponseDTO carResponseDTO = mapping.mapCarToCarResponseDTO(carService.save(car));
        return new ResponseEntity<>(carResponseDTO, HttpStatus.OK);
    }

    /**
     * Update car in database
     *
     * @param carRequestDto new car
     * @param id of car in database
     * @return Response:updated CarDTO ant http status
     */
    @PreAuthorize("hasRole('ADMIN') or " +
            "(hasRole('TRANSPORTER') and carServiceImpl.findById(#id).transporter.username.equals(authentication.name))")
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<CarResponseDTO> update(@Valid @RequestBody CarRequestDTO carRequestDto, @PathVariable Long id) {
        if (!Objects.equals(id, carRequestDto.getId())) {
            throw new RuntimeException(localizedMessageSource.getMessage("controller.car.unexpectedId", new Object[]{}));
        }
        final Car car = mapping.mapCarRequestDTOToCar(carRequestDto);
        final CarResponseDTO carResponseDTO = mapping.mapCarToCarResponseDTO(carService.update(car));
        return new ResponseEntity<>(carResponseDTO, HttpStatus.OK);
    }

    /**
     * Delete car from database
     *
     * @param id of car
     * @return http status
     */
    @PreAuthorize("hasRole('ADMIN') or " +
            "(hasRole('TRANSPORTER') and carServiceImpl.findById(#id).transporter.username.equals(authentication.name))")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.OK)
    public void delete(@PathVariable Long id) {
        carService.deleteById(id);
    }
}
