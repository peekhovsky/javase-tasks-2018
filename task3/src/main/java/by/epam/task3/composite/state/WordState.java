package by.epam.task3.composite.state;

import by.epam.task3.composite.implementation.TextLeafImpl;

import java.util.List;
import java.util.stream.Collectors;

/**
 * This class describes word state for expressions.
 *
 * @author Rostislav Pekhovksy 2018
 * @version 0.1
 */
public final class WordState extends AbstractTextState {

    /***
     * This method splits word into chars, creates leaf components
     * and adds them to children list.
     *
     * @param word word to process
     */
    @Override
    public void createStructure(final String word) {
        final List<Character> chars = this.getParser()
                .parse(word)
                .stream()
                .map(ch -> ch.charAt(0))
                .collect(Collectors.toList());

        for (Character ch : chars) {
            this.getChildren().add(new TextLeafImpl(ch));
        }
    }

    @Override
    public String createText() {
        return String.join("", makeStringListFromComponents());
    }
}

