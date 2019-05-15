package by.vit.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;


/**
 * Base configuration of application
 */
@Configuration
@ComponentScan(basePackages = "by.vit")
@Import({DatabaseConfiguration.class})
public class AppConfiguration {
}
