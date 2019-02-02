package by.epam.task3.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * Interface for text parsers. Parser makes a list of strings from one string
 * using some rule (for instance, pattern).
 *
 * @author Rostislav Pekhovksy 2018
 * @version 0.1
 */
@FunctionalInterface
public interface StringParser {

    /***
     * This method splits string into a list of strings.
     * @param str string to process
     * @return a list of strings
     */
    List<String> parse(String str);

    /***
     * This method splits string into a list of strings using pattern.
     * @param str string to process
     * @param pattern method will find substrings in string that matches this
     *                pattern and add them to a list
     * @return a list of strings
     */
    default List<String> findAllStringsMatchPattern(final String str,
                                                    final Pattern pattern) {
        List<String> list = new ArrayList<>();
        try (Scanner scanner = new Scanner(str)) {
            while (scanner.hasNext()) {
                String temp = scanner.findInLine(pattern);
                list.add(temp);
            }
        }
        return list;
    }

}
