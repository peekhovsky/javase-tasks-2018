package by.epam.task3.composite.implementation;

import by.epam.task3.composite.TextComponent;
import by.epam.task3.composite.TextNode;
import by.epam.task3.composite.state.AbstractTextState;
import by.epam.task3.composite.state.TextState;

import java.util.ArrayList;
import java.util.List;

/***
 * Implementation of text node component.
 *
 * @author Rostislav Pekhovsky 2018
 * @version 0.1
 * @see TextComponent
 */
public final class TextNodeImpl implements TextNode {

    /***
     * Node state.
     * @see AbstractTextState
     */
    private AbstractTextState state;

    /***
     * Children list of node.
     */
    private List<TextComponent> children = new ArrayList<>();


    /***
     * Constructor to create node with text state.
     * @param text node will create text node structure based on text string
     */
    public TextNodeImpl(final String text) {
        this.state = new TextState();
        this.state.setChildren(this.children);
        createStructure(text);
    }

    /***
     * Constructor with setting of state.
     * @param stateNew state of node
     * @param text node will create text node structure based on text string
     */
    public TextNodeImpl(final AbstractTextState stateNew, final String text) {
        this.state = stateNew;
        this.state.setChildren(this.children);
        createStructure(text);
    }

    /***
     * Copy constructor.
     * @param textNode text node to copy
     */
    public TextNodeImpl(final TextNode textNode) {
        this.children = textNode.getChildren();
        this.state = textNode.getState();
        this.state.setChildren(textNode.getChildren());
    }

    @Override
    public void createStructure(final String text) {
        state.createStructure(text);
    }

    @Override
    public String createText() {
        return state.createText();
    }

    @Override
    public void add(final TextComponent component) {
        children.add(component);
    }

    @Override
    public void remove(final TextComponent component) {
        children.remove(component);
    }


    @Override
    public String getStateName() {
        return state.getStateName();
    }

    @Override
    public AbstractTextState getState() {
        return state;
    }

    @Override
    public List<TextComponent> getChildren() {
        return children;
    }

    @Override
    public void setChildren(final List<TextComponent> childrenNew) {
        this.children = childrenNew;
        state.setChildren(children);
    }

    @Override
    public String toString() {
        return "\n" + "State: " + state.getClass().getName() + ", "
                + " components: [" + this.createText() + "]";
    }
}
