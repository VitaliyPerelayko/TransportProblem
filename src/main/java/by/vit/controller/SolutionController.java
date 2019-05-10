package by.vit.controller;

import by.vit.dto.request.TaskDTO;
import by.vit.dto.response.SolutionResponseDTO;
import by.vit.mapping.Mapping;
import by.vit.model.solution.Solution;
import by.vit.service.SolutionService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Solution controller.
 */
@Controller
@RequestMapping("/solution")
public class SolutionController {
    private final SolutionService solutionService;
    private final Mapping mapping;


    public SolutionController(SolutionService solutionService, Mapping mapping) {
        this.solutionService = solutionService;
        this.mapping = mapping;
    }


    /**
     * Gets all solutions from database.
     *
     * @return Response: SolutionResponseDTO ant http status
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<SolutionResponseDTO>> getAll() {
        final List<Solution> solutions = solutionService.findAll();
        final List<SolutionResponseDTO> solutionResponseDTOList = solutions.stream()
                .map(mapping::mapSolutionToSolutionResponseDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(solutionResponseDTOList, HttpStatus.OK);
    }

    /**
     * Gets solution by id
     *
     * @param id of solution
     * @return Response: SolutionResponseDTO ant http status
     */
    @PreAuthorize("hasRole('ROLE_ADMIN') or " +
            "(hasRole('ROLE_SUPPLIER') and solutionServiceImpl.findById(#id).supplier.username.equals(authentication.name))")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<SolutionResponseDTO> getOne(@PathVariable Long id) {
        final SolutionResponseDTO solutionResponseDTO =
                mapping.mapSolutionToSolutionResponseDTO(solutionService.findById(id));
        return new ResponseEntity<>(solutionResponseDTO, HttpStatus.OK);
    }

    /**
     * Gets solution of transport problem with given conditions.
     * Method getAndSaveSolution() save Solution to database.
     * Remark: if user will not press the button "Save solution", this solution should be
     * deleted from database.
     *
     * @param taskDTO task for solve: points of deliver and mass of cargo
     * @return Response: solution(car: points, mass, cost) and http status
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<SolutionResponseDTO> getSolve(@Valid @RequestBody TaskDTO taskDTO) {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        Double[] orders = new Double[taskDTO.getOrderList().size()];
        solutionService.setConditions(mapping.mapTaskToPoint(taskDTO),
                taskDTO.getOrderList().toArray(orders), username);
        return new ResponseEntity<>(mapping.mapSolutionToSolutionResponseDTO(
                solutionService.getAndSaveSolution()), HttpStatus.OK);
    }

    /**
     * Delete solution from database
     *
     * @param id of solution
     * @return http status
     */
    @PreAuthorize("hasRole('ROLE_ADMIN') or " +
            "(hasRole('ROLE_SUPPLIER') and solutionServiceImpl.findById(#id).supplier.username.equals(authentication.name))")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.OK)
    public void delete(@PathVariable Long id) {
        solutionService.deleteById(id);
    }
}
