package by.vit.app;

import by.vit.config.AppConfiguration;
import by.vit.service.SolutionService;
import by.vit.service.impl.SolutionServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class SolutionDebug {
    private final SolutionService solutionService;

    public SolutionDebug(SolutionService solutionService) {
        this.solutionService = solutionService;
    }


    public static void main(String[] args) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfiguration.class);

        SolutionDebug solutionDebug = applicationContext.getBean("solutionServiceImpl",SolutionDebug.class);


        solutionDebug.solutionService.setConditions();




    }
}
