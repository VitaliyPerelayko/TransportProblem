package by.vit.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ResourceBundleMessageSource;

/**
 * Configuration of MessageSource
 */
public class MessagesConfiguration {

    /**
     * @return ResourceBundleMessageSource implementation that
     * accesses resource bundles using specified base names
     */
    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("/applicationMessages");
        return messageSource;
    }
}
