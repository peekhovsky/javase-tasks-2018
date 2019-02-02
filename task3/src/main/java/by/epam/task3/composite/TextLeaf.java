package by.epam.task3.composite;

/***
 * Leaf interface of text structure component.
 * There is no methods (except getters and setters)
 * in this class because it is used just to determine
 * that this component is leaf.
 *
 * @author Rostislav Pekhovsky 2018
 * @version 0.1
 * @see TextComponent
 */
@SuppressWarnings("WeakerAccess")
public interface TextLeaf extends TextComponent {
    /**
     * @return symbol that is stored in leaf
     */
    char getChar();

    /**
     * @param ch symbol to set
     */
    void setChar(char ch);
}
