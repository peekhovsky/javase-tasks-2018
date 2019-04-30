package by.epam.provider.validator;


import by.epam.provider.exception.ProviderException;
import lombok.extern.log4j.Log4j2;

import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * @author Rostislav Pekhovsky 2019
 * @version 0.1
 */
@Log4j2
public final class LanguageValidator {


    private static final String[] AVAILABLE_LANGUAGES = {
            "en_US",
            "ru_RU"
    };

    private LanguageValidator() {
    }

    public static String validateLang(String lang) throws ProviderException {
        if (lang == null) {
            lang = AVAILABLE_LANGUAGES[0];
        } else {
            if (!Arrays.asList(AVAILABLE_LANGUAGES).contains(lang)) {
                throw new ProviderException("Illegal language", 503);
            }
        }
        return lang;
    }

    public static String defaultLang() {
        return AVAILABLE_LANGUAGES[0];
    }
}

