package by.vit.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({WebConfiguration.class,DatabaseConfiguration.class,SolverConfiguration.class})
public class AppConfiguration {
}
