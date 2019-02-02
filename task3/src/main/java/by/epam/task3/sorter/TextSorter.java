package by.epam.task3.sorter;

import by.epam.task3.composite.TextNode;

/**
 * This interface combines all text sorters into one type.
 *
 * @author Rostislav Pekhovksy 2018
 * @version 0.1
 */
public interface TextSorter {
    /**
     * This sorts elements using some role.
     *
     * @param node text node to sorter
     */
    void sort(TextNode node);
}
