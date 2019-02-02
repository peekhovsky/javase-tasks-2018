package by.epam.task3.sorter;

import by.epam.task3.composite.TextComponent;
import by.epam.task3.composite.TextNode;
import by.epam.task3.composite.state.WordState;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Objects;

/**
 * This class sorts words in sentences by their length. Lexemes will save their
 * order, only words changes.
 *
 * @author Rostislav Pekhovksy 2018
 * @version 0.1
 */
public final class WordInSentenceSorter {

    /**
     * Logger.
     */
    private static final Logger LOGGER
            = LogManager.getLogger(WordInSentenceSorter.class);

    /**
     * Word state class name (to compare class names of each node to find word).
     */
    private String wordStateClassName = WordState.class.getName();

    /**
     * This method sorts words in sentences
     * It finds sentences, and them calls sentence sorter to sorter sentence.
     *
     * @param textNode text structure to sorter
     */
    public void sort(final TextNode textNode) {
        for (TextComponent paragraph : textNode.getChildren()) {
            for (TextComponent sentence : paragraph.getChildren()) {
                sortSentence((TextNode) sentence);
            }
        }
        LOGGER.info("Sorted text: " + textNode.createText());
    }

    /**
     * This method sorts words in one sentence.
     *
     * @param sentence sentence structure to sorter
     */
    private void sortSentence(final TextNode sentence) {
        List<TextComponent> lexemes = sentence.getChildren();
        for (int i = 0; i < sentence.getChildren().size(); i++) {
            for (int j = i; j < sentence.getChildren().size(); j++) {
                TextNode lexeme1 = (TextNode) lexemes.get(i);
                TextNode lexeme2 = (TextNode) lexemes.get(j);
                TextNode word1 = findWordInLexeme(lexeme1);
                TextNode word2 = findWordInLexeme(lexeme2);
                if (Objects.nonNull(word1) && Objects.nonNull(word2)
                        && (word1.getChildren().size()
                        > word2.getChildren().size())) {
                    replaceWordInLexeme(lexeme1, word2);
                    replaceWordInLexeme(lexeme2, word1);
                }
                lexemes.set(i, lexeme1);
                lexemes.set(j, lexeme2);
            }
        }
    }

    /**
     * @param lexeme this method finds word lexeme part in lexeme
     * @return word node if word has been found, otherwise "null"
     */
    private TextNode findWordInLexeme(final TextNode lexeme) {
        TextNode word = null;
        for (TextComponent lexemePart : lexeme.getChildren()) {
            if (lexemePart instanceof TextNode
                    && ((TextNode) lexemePart).getStateName()
                    .equals(wordStateClassName)) {
                word = (TextNode) lexemePart;
            }
        }
        return word;
    }

    /**
     * This method replaces word in lexeme to another lexeme.
     *
     * @param lexeme lexeme to change
     * @param word   word to set
     */
    private void replaceWordInLexeme(final TextNode lexeme,
                                     final TextNode word) {
        for (TextComponent lexemePart : lexeme.getChildren()) {
            if (lexemePart instanceof TextNode
                    && ((TextNode) lexemePart).getStateName()
                    .equals(wordStateClassName)) {
                lexeme.getChildren().set(lexeme.getChildren()
                        .indexOf(lexemePart), word);
            }
        }
    }
}
