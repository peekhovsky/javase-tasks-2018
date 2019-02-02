package by.epam.task3.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Text parser for text string.
 * Splits string into a list of paragraphs.
 *
 * @author Rostislav Pekhovksy 2018
 * @version 0.1
 */
public final class TextParser implements StringParser {

    @Override
    public List<String> parse(final String text) {
        List<String> list = new ArrayList<>();
        try (Scanner scanner = new Scanner(text)) {
            while (scanner.hasNextLine()) {
                list.add(scanner.nextLine());
            }
        }
        return list;
    }
}
