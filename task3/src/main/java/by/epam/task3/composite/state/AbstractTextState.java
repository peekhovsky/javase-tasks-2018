package by.epam.task3.composite.state;

import by.epam.task3.composite.TextComponent;
import by.epam.task3.factory.TextParserFactory;
import by.epam.task3.parser.StringParser;

import java.util.List;
import java.util.stream.Collectors;

/***
 * This class describes field and methods for any state of text node.
 * It has children field that stores children of text node it is belong.
 *
 * @author Rostislav Pekhovksy 2018
 * @version 0.1
 * @see by.epam.task3.composite.TextNode
 */
public abstract class AbstractTextState {

    /***
     * This field stores children of text node this state is belong.
     */
    private List<TextComponent> children;

    /***
     * This field contains right text parser for current state.
     */
    private StringParser parser;

    /***
     * State name.
     */
    private String stateName;

    /***
     * Constructor. Determines correct parser (using parser factory)
     * and name of state.
     * @see TextParserFactory
     */
    public AbstractTextState() {
        this.parser = TextParserFactory.findParser(this);
        this.stateName = this.getClass().getName();
    }

    /**
     * This method creates children list and adds it to children field
     * from string for particular state.
     *
     * @param string method makes children based on that string
     */
    public abstract void createStructure(String string);

    /**
     * This method creates text from children list from string for
     * particular state.
     *
     * @return string from children
     */
    public abstract String createText();

    /**
     * @return name of state
     */
    public String getStateName() {
        return stateName;
    }

    /**
     * @return children
     */
    public List<TextComponent> getChildren() {
        return children;
    }

    /**
     * @param childrenNew to set
     */
    public void setChildren(final List<TextComponent> childrenNew) {
        this.children = childrenNew;
    }

    /**
     * @return text parser
     */
    public StringParser getParser() {
        return parser;
    }

    /**
     * @param parserNew text parser to set
     */
    public void setParser(final StringParser parserNew) {
        this.parser = parserNew;
    }

    /**
     * @return a list of component strings from components
     */
    List<String> makeStringListFromComponents() {
        return children
                .stream()
                .map(TextComponent::createText)
                .collect(Collectors.toList());
    }
}
