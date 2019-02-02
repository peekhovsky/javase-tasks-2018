package by.epam.task3.composite.implementation;

import by.epam.task3.composite.TextComponent;
import by.epam.task3.composite.TextLeaf;
import by.epam.task3.exception.TaskRuntimeException;

import java.util.List;
import java.util.Objects;

/***
 * Implementation of text leaf component.
 * Text leafs contains only one character and nothing else.
 *
 * @author Rostislav Pekhovsky 2018
 * @version 0.1
 * @see TextComponent
 */
public final class TextLeafImpl implements TextLeaf {

    /**
     * Symbol of text that is stored in leaf.
     */
    private char ch;

    /***
     * Public constructor.
     * @param chNew symbol to set
     */
    public TextLeafImpl(final char chNew) {
        this.ch = chNew;
    }

    /**
     * Method requires only one symbol in string to make leaf that contains
     * only char.
     *
     * @param str string that contains only one symbol
     * @throws TaskRuntimeException if string contains more or less that
     *                              one symbol
     */
    @Override
    public void createStructure(final String str) {
        if (Objects.nonNull(str) && str.length() == 1) {
            ch = str.charAt(0);
        } else {
            throw new TaskRuntimeException("String contains more or less that"
                    + " one symbol! Required only one");
        }
    }

    /**
     * @return string that contains only one symbol
     */
    @Override
    public String createText() {
        return String.valueOf(ch);
    }

    /**
     * @throws UnsupportedOperationException if that method has been called,
     *                                       a leaf doesn't support this
     *                                       operation
     */
    @Override
    public List<TextComponent> getChildren() {
        throw new UnsupportedOperationException();
    }

    /**
     * @throws UnsupportedOperationException if that method has been called,
     *                                       a leaf doesn't support this
     *                                       operation
     */
    @Override
    public void setChildren(final List<TextComponent> components) {
        throw new UnsupportedOperationException();
    }

    @Override
    public char getChar() {
        return 0;
    }

    @Override
    public void setChar(final char chNew) {
        this.ch = chNew;
    }

    @Override
    public boolean equals(final Object o) {
        boolean res;
        if (this == o) {
            res = true;
        } else if (!(o instanceof TextLeafImpl)) {
            res = false;
        } else {
            TextLeafImpl textLeaf = (TextLeafImpl) o;
            res = (ch == textLeaf.ch);
        }
        return res;
    }

    @Override
    public int hashCode() {
        return Objects.hash(ch);
    }

    @Override
    public String toString() {
        return String.valueOf(ch);
    }
}
