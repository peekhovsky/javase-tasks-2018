package by.epam.task3.sorter;

import by.epam.task3.composite.TextNode;

import java.util.Comparator;

/**
 * This class sorts paragraph in text by quantity of sentences
 * in each paragraph.
 *
 * @author Rostislav Pekhovksy 2018
 * @version 0.1
 */
public final class ParagraphsInTextSorter implements TextSorter {

    /**
     * This method uses stream API tools to sorter paragraphs. It counts
     * children of each paragraph and them compares this numbers and sorts
     * elements by that.
     *
     * @param textNode text node to sorter
     */
    @Override
    public void sort(final TextNode textNode) {
        textNode.getChildren().sort(Comparator.comparingInt(
                paragraph -> paragraph.getChildren().size()));
    }
}
