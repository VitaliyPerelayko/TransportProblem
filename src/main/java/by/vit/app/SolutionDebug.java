package by.vit.app;

import by.vit.service.SolutionService;
import org.springframework.stereotype.Component;

@Component
public class SolutionDebug {
    private final SolutionService solutionService;

    public SolutionDebug(SolutionService solutionService) {
        this.solutionService = solutionService;
    }


    public static void main(String[] args) {

    }
}
