package by.epam.task3.parser;

import java.util.List;
import java.util.regex.Pattern;

/**
 * Text parser for lexeme part string.
 * Splits string into a list of strings that contains only one symbols.
 *
 * @author Rostislav Pekhovksy 2018
 * @version 0.1
 */
public final class LexemePartParser implements StringParser {

    /**
     * Regular expression for lexeme part parser.
     */
    private static final Pattern REGEX =
            Pattern.compile(".");

    @Override
    public List<String> parse(final String word) {
        return findAllStringsMatchPattern(word, REGEX);
    }
}
