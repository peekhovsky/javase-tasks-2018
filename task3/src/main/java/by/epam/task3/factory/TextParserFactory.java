package by.epam.task3.factory;

import by.epam.task3.composite.state.AbstractTextState;
import by.epam.task3.composite.state.LexemeState;
import by.epam.task3.composite.state.WordState;
import by.epam.task3.composite.state.TextState;
import by.epam.task3.composite.state.ExpressionState;
import by.epam.task3.composite.state.ParagraphState;
import by.epam.task3.composite.state.SentenceState;
import by.epam.task3.composite.state.WordCharsState;
import by.epam.task3.exception.TaskRuntimeException;
import by.epam.task3.parser.StringParser;
import by.epam.task3.parser.TextParser;
import by.epam.task3.parser.LexemeParser;
import by.epam.task3.parser.ParagraphParser;
import by.epam.task3.parser.SentenceParser;
import by.epam.task3.parser.LexemePartParser;

/**
 * Text parser factory class.
 * It stores singletons of text parsers and allows to get appropriate
 * parser depending on a type of text state.
 * This class is needed to avoid making new objects of parsers every time
 * program need any type of parser.
 * Supports lazy-initialization.
 *
 * @author Rostislav Pekhovksy 2018
 * @version 0.1
 */
public final class TextParserFactory {

    /**
     * Text parser field.
     */
    private static StringParser textParser;

    /**
     * Paragraph parser field.
     */
    private static StringParser paragraphParser;

    /**
     * Sentence parser field.
     */
    private static StringParser sentenceParser;

    /**
     * Lexeme parser field.
     */
    private static StringParser lexemeParser;

    /**
     * Lexeme part parser field.
     */
    private static StringParser lexemePartParser;

    /**
     * Private no args constructor to avoid creating of object of that class.
     */
    private TextParserFactory() {
    }

    /**
     * This method finds an appropriate parser for particular text node state.
     *
     * @param state a state of text node
     * @return an appropriate parser
     */
    public static StringParser findParser(final AbstractTextState state) {
        StringParser parser;
        if (state instanceof TextState) {
            parser = findTextParser();
        } else if (state instanceof ParagraphState) {
            parser = findParagraphParser();
        } else if (state instanceof SentenceState) {
            parser = findSentenceParser();
        } else if (state instanceof LexemeState) {
            parser = findLexemeParser();
        } else if (state instanceof WordState
                || state instanceof WordCharsState
                || state instanceof ExpressionState) {
            parser = findLexemePartParser();
        } else {
            throw new TaskRuntimeException("Factory didn't find any parser.");
        }
        return parser;
    }

    /**
     * @return text parser
     */
    private static StringParser findTextParser() {
        if (textParser == null) {
            textParser = new TextParser();
        }
        return textParser;
    }

    /**
     * @return paragraph parser
     */
    private static StringParser findParagraphParser() {
        if (paragraphParser == null) {
            paragraphParser = new ParagraphParser();
        }
        return paragraphParser;
    }

    /**
     * @return sentence parser
     */
    private static StringParser findSentenceParser() {
        if (sentenceParser == null) {
            sentenceParser = new SentenceParser();
        }
        return sentenceParser;
    }

    /**
     * @return lexeme parser
     */
    private static StringParser findLexemeParser() {
        if (lexemeParser == null) {
            lexemeParser = new LexemeParser();
        }
        return lexemeParser;
    }

    /**
     * @return lexeme part parser
     */
    private static StringParser findLexemePartParser() {
        if (lexemePartParser == null) {
            lexemePartParser = new LexemePartParser();
        }
        return lexemePartParser;
    }
}
