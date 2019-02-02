package by.epam.task3.composite.state;

import by.epam.task3.composite.implementation.TextNodeImpl;
import by.epam.task3.exception.TaskRuntimeException;
import by.epam.task3.parser.LexemeParser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

/**
 * This class describes lexeme state for expressions.
 *
 * @author Rostislav Pekhovksy 2018
 * @version 0.1
 */
public final class LexemeState extends AbstractTextState {

    /**
     * Logger.
     */
    private static final Logger LOGGER
            = LogManager.getLogger(LexemeState.class);

    /***
     * This method finds in every lexeme types of data is stored in it, makes
     * components with appropriate states and adds them to children.
     * Lexeme can store expressions, words and special symbols (word chars).
     *
     * @param lexeme lexeme to process
     * @throws TaskRuntimeException if a part of lexeme doesn't belong to any
     *                              of type
     */
    @Override
    public void createStructure(final String lexeme) {
        final List<String> lexemes = getParser().parse(lexeme);
        for (String lexemePart : lexemes) {
            if (lexemePart.matches(LexemeParser.EXPRESSION_PATTERN)) {
                LOGGER.trace("Add expression: " + lexemePart);
                this.getChildren().add(
                        new TextNodeImpl(new ExpressionState(), lexemePart));
            } else if (lexemePart.matches(LexemeParser.WORD_PATTERN)) {
                LOGGER.trace("Add word: " + lexemePart);
                this.getChildren().add(
                        new TextNodeImpl(new WordState(), lexemePart));
            } else if (lexemePart.matches(LexemeParser.SPEC_SYMBOL_PATTERN)) {
                LOGGER.trace("Add word past chars: " + lexemePart);
                this.getChildren().add(
                        new TextNodeImpl(new WordCharsState(), lexemePart));
            } else {
                throw new TaskRuntimeException("String "
                        + "does not match any of pattern.");
            }

        }
    }

    @Override
    public String createText() {
        return String.join("", makeStringListFromComponents());
    }
}
