package by.epam.task3.composite.state;

import by.epam.task3.composite.implementation.TextLeafImpl;

import java.util.List;
import java.util.stream.Collectors;

/**
 * This class describes word chars state for expressions.
 * Word chars means that it is symbols near word.
 *
 * @author Rostislav Pekhovksy 2018
 * @version 0.1
 */
public final class WordCharsState extends AbstractTextState {

    /***
     * This method splits word chars into chars, creates leaf components
     * and adds them to children list.
     *
     * @param symbols word chars to process
     */
    @Override
    public void createStructure(final String symbols) {
        final List<Character> chars = this.getParser()
                .parse(symbols)
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
