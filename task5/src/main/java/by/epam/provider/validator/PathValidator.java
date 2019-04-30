package by.epam.provider.validator;


import by.epam.provider.exception.ProviderException;
import lombok.extern.log4j.Log4j2;

import java.util.*;

/**
 * @author Rostislav Pekhovsky 2019
 * @version 0.1
 */
@Log4j2
public final class PathValidator {

    private static final String[] AVAILABLE_REQ_WITH_INNER = {
            "static/"
    };

    private static final List<String> AVAILABLE_REQ = Arrays.asList(
            "sign_up", "sign_in", "tariffs", "bills", "log_out",
            "client_info", "user_info", "main", "tariff_info",
            "set_tariff", "pay", "discounts", "users_list", "date",
            "add_tariff", "add_discount", "ban_user", "block_tariff");

    private static final String DEFAULT_PATH_REDIRECT = "main";

    private PathValidator() {
    }

    public static String validatePath(String path) throws ProviderException {
        if (path == null || path.isEmpty()) {
            path = DEFAULT_PATH_REDIRECT;
        }

        String res = null;
        log.debug("Validating of [" + path + "]. ");

        if (AVAILABLE_REQ.contains(path)) {
            res = path;
        } else {
            for (String s : AVAILABLE_REQ_WITH_INNER) {
                if (path.startsWith(s)) {
                    res = path;
                    break;
                }
            }
        }
        if (res == null) {
            throw new ProviderException("Invalid path.");
        } else {
            return res;
        }
    }
}

