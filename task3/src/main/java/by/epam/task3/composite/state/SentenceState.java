package by.epam.task3.composite.state;

import by.epam.task3.composite.implementation.TextNodeImpl;

import java.util.List;

/**
 * This class describes sentence state for expressions.
 *
 * @author Rostislav Pekhovksy 2018
 * @version 0.1
 */
public final class SentenceState extends AbstractTextState {

    /***
     * This method splits sentences into lexemes, creates lexeme components
     * and adds them to children list.
     *
     * @param sentence sentence to process
     */
    @Override
    public void createStructure(final String sentence) {
        final List<String> lexemes = this.getParser().parse(sentence);
        for (String lexeme : lexemes) {
            this.getChildren()
                    .add(new TextNodeImpl(new LexemeState(), lexeme));
        }
    }

    @Override
    public String createText() {
        return String.join(" ", makeStringListFromComponents());
    }
}
