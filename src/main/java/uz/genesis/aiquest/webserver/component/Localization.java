package uz.genesis.aiquest.webserver.component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.util.Locale;


@Component
@RequiredArgsConstructor
@Slf4j
public class Localization {

    private final MessageSource messageSource;

    /**
     * Gets message from message bundle
     */
    public String getMessage(String message) {
        try {
            return messageSource.getMessage(message,
                    null,
                    LocaleContextHolder.getLocale());
        } catch (NoSuchMessageException e) {
            log.error("No message found under " + message);
            return message;
        }
    }

    public String getMessage(String message, Locale locale) {
        return messageSource.getMessage(message,
                null,
                locale);

    }
}
