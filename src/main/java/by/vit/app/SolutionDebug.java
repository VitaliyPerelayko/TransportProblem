package by.vit.app;

import by.vit.config.AppConfiguration;
import by.vit.model.Point;
import by.vit.model.Road;
import by.vit.model.RoadId;
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

//        Road road = new Road();
//        road.setPoint1(solution.pointService.findById(1l));
//        road.setPoint2(solution.pointService.findById(14l));
 //       road.setDistance(2D);
//
//        System.out.println(solution.roadService.save(road));

        Road r = solution.roadService.findById(new RoadId(1L,14L));

        r.setDistance(55d);

        System.out.println(solution.roadService.update(r));



// Point [] points = {solution.findByName("Grodno"),
//                solution.findByName("Lida"),
//                solution.findByName("Brest"),
//                solution.findByName("Volkovysk")};
//        Double [] order = {25d,13d,17d};
//
//        SolutionService service = solution.solutionService;
//
//        service.setConditions(points,order,"kiiv");
//        service.getAndSaveSolution();




    }
}
