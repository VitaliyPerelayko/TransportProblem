package by.vit.controller;

import by.vit.component.LocalizedMessageSource;
import by.vit.dto.request.TransporterRequestDTO;
import by.vit.dto.response.TransporterResponseDTO;
import by.vit.model.Transporter;
import by.vit.service.TransporterService;
import org.dozer.Mapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Controller for entity Transporter.
 */
@RestController
@RequestMapping("/transporters")
public class TransporterController {

    private final Mapper mapper;

    private final TransporterService transporterService;

    private final LocalizedMessageSource localizedMessageSource;

    public TransporterController(Mapper mapper, TransporterService transporterService,
                          LocalizedMessageSource localizedMessageSource) {
        this.mapper = mapper;
        this.transporterService = transporterService;
        this.localizedMessageSource = localizedMessageSource;
    }

    /**
     * Gets all transporters from database.
     *
     * @return Response: TransporterDTO ant http status
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<TransporterResponseDTO>> getAll() {
        final List<Transporter> transporters = transporterService.findAll();
        final List<TransporterResponseDTO> transporterDTOList = transporters.stream()
                .map((transporter) -> mapper.map(transporter, TransporterResponseDTO.class))
                .collect(Collectors.toList());
        return new ResponseEntity<>(transporterDTOList, HttpStatus.OK);
    }

    /**
     * Gets transporter by id
     *
     * @param id of transporter
     * @return Response: TransporterDTO ant http status
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<TransporterResponseDTO> getOne(@PathVariable Long id) {
        final TransporterResponseDTO transporterResponseDTO = mapper.map(transporterService.findById(id), TransporterResponseDTO.class);
        return new ResponseEntity<>(transporterResponseDTO, HttpStatus.OK);
    }

    /**
     * Save transporter to database
     *
     * @param transporterRequestDto new transporter
     * @return Response:saved TransporterDTO ant http status
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<TransporterResponseDTO> save(@Valid @RequestBody TransporterRequestDTO transporterRequestDto) {
        transporterRequestDto.setId(null);
        final Transporter transporter = mapper.map(transporterRequestDto, Transporter.class);
        final TransporterResponseDTO transporterResponseDTO = mapper.map(transporterService.save(transporter), TransporterResponseDTO.class);
        return new ResponseEntity<>(transporterResponseDTO, HttpStatus.OK);
    }

    /**
     * Update transporter in database
     *
     * @param transporterRequestDto new transporter
     * @param id             of transporter in database
     * @return Response:updated TransporterDTO ant http status
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<TransporterResponseDTO> update(@Valid @RequestBody TransporterRequestDTO transporterRequestDto, @PathVariable Long id) {
        if (!Objects.equals(id, transporterRequestDto.getId())) {
            throw new RuntimeException(localizedMessageSource.getMessage("{controller.transporter.unexpectedId}", new Object[]{}));
        }
        final Transporter transporter = mapper.map(transporterRequestDto, Transporter.class);
        final TransporterResponseDTO transporterResponseDTO = mapper.map(transporterService.update(transporter), TransporterResponseDTO.class);
        return new ResponseEntity<>(transporterResponseDTO, HttpStatus.OK);
    }

    /**
     * Delete transporter from database
     *
     * @param id of transporter
     * @return http status
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.OK)
    public void delete(@PathVariable Long id) {
        transporterService.deleteById(id);
    }
}

