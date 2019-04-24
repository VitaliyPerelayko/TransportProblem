package by.vit.controller;

import by.vit.component.LocalizedMessageSource;
import by.vit.dto.request.CarRequestDTO;
import by.vit.dto.response.CarResponseDTO;
import by.vit.model.Car;
import by.vit.service.CarService;
import org.dozer.Mapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/cars")
public class CarController {

    private final Mapper mapper;

    private final CarService carService;

    private final LocalizedMessageSource localizedMessageSource;

    public CarController(Mapper mapper, CarService carService,
                         LocalizedMessageSource localizedMessageSource) {
        this.mapper = mapper;
        this.carService = carService;
        this.localizedMessageSource = localizedMessageSource;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<CarResponseDTO>> getAll() {
        final List<Car> cars = carService.findAll();
        final List<CarResponseDTO> carDTOList = cars.stream()
                .map((car) -> mapper.map(car, CarResponseDTO.class))
                .collect(Collectors.toList());
        return new ResponseEntity<>(carDTOList, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<CarResponseDTO> getOne(@PathVariable Long id) {
        final CarResponseDTO carResponseDTO = mapper.map(carService.findById(id), CarResponseDTO.class);
        return new ResponseEntity<>(carResponseDTO, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<CarResponseDTO> save(@Valid @RequestBody CarRequestDTO carRequestDto) {
        carRequestDto.setId(null);
        final Car car = mapper.map(carRequestDto, Car.class);
        final CarResponseDTO carResponseDTO = mapper.map(carService.save(car), CarResponseDTO.class);
        return new ResponseEntity<>(carResponseDTO, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<CarResponseDTO> update(@Valid @RequestBody CarRequestDTO carRequestDto, @PathVariable Long id) {
        if (!Objects.equals(id, carRequestDto.getId())) {
            throw new RuntimeException(localizedMessageSource.getMessage("controller.car.unexpectedId", new Object[]{}));
        }
        final Car car = mapper.map(carRequestDto, Car.class);
        final CarResponseDTO carResponseDTO = mapper.map(carService.update(car), CarResponseDTO.class);
        return new ResponseEntity<>(carResponseDTO, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.OK)
    public void delete(@PathVariable Long id) {
        carService.deleteById(id);
    }
}
