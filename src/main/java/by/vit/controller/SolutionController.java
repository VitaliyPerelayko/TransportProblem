package by.vit.controller;

import by.vit.dto.request.TaskDTO;
import by.vit.dto.response.solutiondto.SolutionDTO;
import by.vit.mapping.Mapping;
import by.vit.service.SolutionService;
import by.vit.service.transportproblemsolve.factory.DistanceMatrixFactory;
import by.vit.service.transportproblemsolve.factory.SolverFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@Controller
@RequestMapping("/solution")
public class SolutionController {
    private final SolutionService solutionService;
    private final Mapping mapping;

    public SolutionController(SolutionService solutionService, Mapping mapping) {
        this.solutionService = solutionService;
        this.mapping = mapping;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<SolutionDTO> save(@Valid @RequestBody TaskDTO taskDTO) {
        solutionService.setDistanceMatrixFinder(DistanceMatrixFactory.Type.YanQi);
        solutionService.setSolver(SolverFactory.Type.CLP);

        Double[] orders = new Double[taskDTO.getOrderList().size()];
        solutionService.setConditions(mapping.mapTaskToPoint(taskDTO),
                taskDTO.getOrderList().toArray(orders));
        return new ResponseEntity<>(mapping.mapSolution(solutionService.getSolution()), HttpStatus.OK);
    }

}
