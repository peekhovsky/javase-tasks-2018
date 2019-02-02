package by.epam.task3.composite.state;

import by.epam.task3.composite.implementation.TextNodeImpl;

import java.util.List;

/**
 * This class describes text state for expressions.
 *
 * @author Rostislav Pekhovksy 2018
 * @version 0.1
 */
public final class TextState extends AbstractTextState {

    /***
     * This method splits text into paragraphs, creates paragraph components
     * and adds them to children list.
     *
     * @param text word chars to process
     */
    @Override
    public void createStructure(final String text) {
        final List<String> paragraphs = this.getParser()
                .parse(text);
        for (String paragraph : paragraphs) {
            this.getChildren()
                    .add(new TextNodeImpl(new ParagraphState(), paragraph));
        }
    }

    @Override
    public String createText() {
        return "\t" + String.join("\n\t",
                makeStringListFromComponents());
    }
}
