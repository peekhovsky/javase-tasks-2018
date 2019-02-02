package by.epam.task3.parser;

import java.util.List;
import java.util.regex.Pattern;


/**
 * Text parser for lexeme string.
 * Splits string into a list of lexeme parts (words, word chars, expressions).
 *
 * @author Rostislav Pekhovksy 2018
 * @version 0.1
 */
public final class LexemeParser implements StringParser {
    /**
     * Word regex pattern.
     */
    public static final String WORD_PATTERN = "[\\w]+";

    /**
     * Special symbol regex pattern (for word chars).
     */
    public static final String SPEC_SYMBOL_PATTERN = "[\\p{Punct}]+";

    /**
     * Expression pattern.
     */
    public static final String EXPRESSION_PATTERN = "([(]*[0-9<>|&^~]+[)]*)+";

    /**
     * Regular expression for lexeme parser.
     */
    private static final Pattern REGEX =
            Pattern.compile(EXPRESSION_PATTERN + "|" + WORD_PATTERN + "|"
                    + SPEC_SYMBOL_PATTERN);

    @Override
    public List<String> parse(final String paragraph) {
        return findAllStringsMatchPattern(paragraph, REGEX);
    }
}
