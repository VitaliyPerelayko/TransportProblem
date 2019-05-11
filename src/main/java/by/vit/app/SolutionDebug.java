package by.vit.app;

import by.vit.config.AppConfiguration;
import by.vit.model.Point;
import by.vit.model.solution.Solution;
import by.vit.service.PointService;
import by.vit.service.RoadService;
import by.vit.service.SolutionService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class SolutionDebug {
    private final SolutionService solutionService;
    private final RoadService roadService;
    private final PointService pointService;

    public SolutionDebug(SolutionService solutionService, RoadService roadService, PointService pointService) {
        this.solutionService = solutionService;
        this.roadService = roadService;
        this.pointService = pointService;
    }

    private Point findByName(String name){
        return pointService.findByName(name);
    }


    public static void main(String[] args) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfiguration.class);

        SolutionDebug solution = applicationContext.getBean("solutionDebug",SolutionDebug.class);

        Point [] points = {solution.findByName("Grodno"),
                solution.findByName("Lida"),
                solution.findByName("Brest"),
                solution.findByName("Volkovysk")};
        Double [] order = {25d,13d,17d};

        SolutionService service = solution.solutionService;

        service.setConditions(points,order,"vit");
        service.getAndSaveSolution();




    }
}
