package by.epam.task3.parser;

import java.util.List;
import java.util.regex.Pattern;

/**
 * Text parser for paragraph string.
 * Splits string into a list of lexemes.
 *
 * @author Rostislav Pekhovksy 2018
 * @version 0.1
 */
public final class SentenceParser implements StringParser {

    /**
     * Regular expression for sentence parser.
     */
    private static final Pattern PATTERN =
            Pattern.compile("[\\w\\p{Punct}]+");

    @Override
    public List<String> parse(final String sentence) {
        return findAllStringsMatchPattern(sentence, PATTERN);
    }
}
