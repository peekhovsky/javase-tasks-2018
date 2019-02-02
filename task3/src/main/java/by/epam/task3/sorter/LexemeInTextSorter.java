package by.epam.task3.sorter;

import by.epam.task3.composite.TextComponent;
import by.epam.task3.composite.TextNode;
import by.epam.task3.composite.implementation.TextNodeImpl;
import by.epam.task3.composite.state.LexemeState;
import by.epam.task3.exception.TaskRuntimeException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This class sorts lexemes in text by quantity of particular symbol in lexeme.
 * If quantity in maximal, this lexeme is going to be first.
 * If quantity is similar, it sorts by alphabet.
 *
 * @author Rostislav Pekhovksy 2018
 * @version 0.1
 */
public final class LexemeInTextSorter implements TextSorter {

    /**
     * Logger.
     */
    private static final Logger LOGGER
            = LogManager.getLogger(LexemeInTextSorter.class);

    /**
     * Symbol.
     */
    private char symbol;

    /**
     * Lexeme iterator.
     */
    private Iterator<TextNode> lexemeIterator;

    /**
     * Constructor.
     *
     * @param symbolNew symbol to set
     */
    public LexemeInTextSorter(final char symbolNew) {
        this.symbol = symbolNew;
    }

    /**
     * @return symbol
     */
    public char getSymbol() {
        return symbol;
    }

    /**
     * @param symbolNew symbol to set
     */
    public void setSymbol(final char symbolNew) {
        this.symbol = symbolNew;
    }

    /**
     * This method sorts lexemes by quantity of particular symbol in them
     * (or by alphabet).
     * 1) it collects all lexemes in text;
     * 2) it sorts the list of lexemes by symbols (or alphabet);
     * 3) it sets new order of lexemes into componets structure
     * (using iterator).
     *
     * @param textNode text component node to sorter
     */
    @Override
    public void sort(final TextNode textNode) {
        List<TextNode> lexemes = findLexemes(textNode);
        LOGGER.debug("Unsorted lexemes: " + lexemes);
        lexemes = sortBySymbol(lexemes);
        LOGGER.debug("Sorted lexemes: " + lexemes);
        lexemeIterator = lexemes.iterator();
        setElements(textNode);
        LOGGER.info("Sorted text: " + textNode.createText());
    }


    /**
     * This method sorts a list of lexemes by quantity of symbols (in reverse
     * order), or if quantity is similar, it will sorter them by alphabet.
     *
     * @param lexemes a list of lexemes to sorter
     * @return sorted lexemes
     */
    private List<TextNode> sortBySymbol(final List<TextNode> lexemes) {
        return lexemes.stream().sorted((lexeme1, lexeme2) -> {
            String lexemeStr1 = lexeme1.createText();
            String lexemeStr2 = lexeme2.createText();
            int symbolCount1 = (int) lexemeStr1
                    .codePoints().filter(ch -> ch == symbol).count();
            int symbolCount2 = (int) lexemeStr2
                    .codePoints().filter(ch -> ch == symbol).count();

            int res = Integer.compare(symbolCount2, symbolCount1);
            if (res == 0) {
                res = lexemeStr1.compareToIgnoreCase(lexemeStr2);
            }
            return res;
        }).collect(Collectors.toList());
    }

    /**
     * This method finds all text lexemes using tail recursion.
     *
     * @param textNode text node where method will search
     * @return a list of lexemes
     */
    private List<TextNode> findLexemes(final TextNode textNode) {
        if (textNode.getStateName().equals(LexemeState.class.getName())) {
            LOGGER.debug("Add lexeme: " + textNode.createText());
            return Collections.singletonList(new TextNodeImpl(textNode));
        } else {
            List<TextNode> composites = new LinkedList<>();
            for (TextComponent component : textNode.getChildren()) {
                if (component instanceof TextNode) {
                    composites.addAll(findLexemes((TextNode) component));
                }
            }
            return composites;
        }
    }


    /**
     * This method sets all text lexemes to text using tail recursion.
     *
     * @param textNode text node where method will set new lexemes
     * @return updated text node
     */
    private TextNode setElements(final TextNode textNode) {
        if (textNode.getStateName().equals(LexemeState.class.getName())) {
            if (lexemeIterator.hasNext()) {
                TextNode newLexeme = lexemeIterator.next();
                textNode.setChildren(newLexeme.getChildren());
                return textNode;
            } else {
                throw new TaskRuntimeException("Unexpected iterator ending.");
            }
        } else {
            List<TextComponent> components = new LinkedList<>();
            for (TextComponent component : textNode.getChildren()) {
                if (component instanceof TextNode) {
                    components.add(
                            setElements((TextNode) component));
                }
            }
            textNode.setChildren(components);
            LOGGER.debug("Text node: " + components);
            return textNode;
        }
    }
}
