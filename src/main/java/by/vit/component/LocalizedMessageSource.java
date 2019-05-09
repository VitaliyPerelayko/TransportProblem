package by.vit.component;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

/**
 *  Component for resolving messages, with support for the parameterization
 * and internationalization of such messages.
 */
@Component
public class LocalizedMessageSource {
    private List<Locale> localeList = Arrays.asList(new Locale("ru"), new Locale("en"));

    private MessageSource messageSource;

    LocalizedMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    /**
     * Try to resolve the message. Treat as an error if the message can't be found.
     *
     * @param messageCode the code to lookup up, such as 'calculator.noRateSet'
     * @param arguments   an array of arguments that will be filled in for params within
     *                    the message (params look like "{0}", "{1,date}", "{2,time}" within a message),
     *                    or {@code null} if none.
     * @return the resolved message
     * @throws org.springframework.context.NoSuchMessageException if the message wasn't found
     */
    public String getMessage(String messageCode, Object[] arguments) {
        Locale locale = LocaleContextHolder.getLocale();
        locale = localeList.contains(locale) ? locale : Locale.getDefault();
        return messageSource.getMessage(messageCode, arguments, locale);
    }
}
