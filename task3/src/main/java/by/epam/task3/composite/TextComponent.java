package by.epam.task3.composite;

import java.util.List;

/**
 * This interface is for all components of text structure.
 * Text structure (every node has many children).
 * Text (Node) -> Paragraph (Node) -> Sentence (Node)
 * -> Lexemes (Node) -> Word (Node), Expression (Node),
 * WordChars (Node) -> Char (Leaf)
 *
 * @author Rostislav Pekhovsky 2018
 * @version 0.1
 * @see TextComponent
 */
public interface TextComponent {

    /**
     * This method is to create text structure from string depending on node
     * state and children components.
     *
     * @param str method makes structure based on that string
     */
    void createStructure(String str);

    /**
     * This method is to create text string from text structure depending on
     * node state and children components.
     *
     * @return return completed text string
     */
    String createText();

    /**
     * @return children of component
     */
    List<TextComponent> getChildren();

    /**
     * @param children children to set for component
     */
    void setChildren(List<TextComponent> children);
}
