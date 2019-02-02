package by.epam.task3.composite;

import by.epam.task3.composite.state.AbstractTextState;

/**
 * This interface is for node components of text.
 * It has state field: it determines a type of composite and has appropriate
 * methods for current type.
 *
 * @author Rostislav Pekhovsky 2018
 * @version 0.1
 * @see TextComponent
 * @see AbstractTextState
 */
public interface TextNode extends TextComponent {
    /**
     * @param component component to add to children list
     */
    void add(TextComponent component);

    /**
     * @param component remove components from children list
     */
    void remove(TextComponent component);

    /**
     * @return state name of text composite
     */
    String getStateName();

    /**
     * @return state of text composite
     */
    AbstractTextState getState();
}
