package by.epam.task3.parser;

import java.util.List;
import java.util.regex.Pattern;

/**
 * Text parser for paragraph string.
 * Splits string into a list of sentences.
 *
 * @author Rostislav Pekhovksy 2018
 * @version 0.1
 */
public final class ParagraphParser implements StringParser {

    /**
     * Regular expression for paragraph parser.
     */
    private static final Pattern REGEX =
            Pattern.compile("(?:(?!\\.\\s).)+([!?.]{1,3})");

    @Override
    public List<String> parse(final String paragraph) {
        return findAllStringsMatchPattern(paragraph, REGEX);
    }
}
