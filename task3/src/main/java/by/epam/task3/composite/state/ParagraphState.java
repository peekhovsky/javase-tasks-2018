package by.epam.task3.composite.state;

import by.epam.task3.composite.implementation.TextNodeImpl;
import java.util.List;

/**
 * This class describes paragraph state for expressions.
 *
 * @author Rostislav Pekhovksy 2018
 * @version 0.1
 */
public final class ParagraphState extends AbstractTextState {

    /***
     * This method splits paragraph into sentences, creates sentence components
     * and adds them to children list.
     *
     * @param paragraph text to process
     */
    @Override
    public void createStructure(final String paragraph) {
        final List<String> sentences = this.getParser().parse(paragraph);
        for (String sentence : sentences) {
            this.getChildren()
                    .add(new TextNodeImpl(new SentenceState(), sentence));
        }
    }

    @Override
    public String createText() {
        return String.join(" ", makeStringListFromComponents());
    }
}
