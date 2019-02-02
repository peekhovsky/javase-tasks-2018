package by.epam.task3;

import by.epam.task3.parser.*;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

import static org.testng.AssertJUnit.assertEquals;

public class ScannerParserImplTest {

    @DataProvider
    public Object[][] parseTextParserParseData() {
        return new Object[][]{
                {
                        "Rostik is good.\nThis is not good.\nI feel bad.",
                        Arrays.asList("Rostik is good.",
                                "This is not good.", "I feel bad.")
                }
        };
    }

    @Test(dataProvider = "parseTextParserParseData")
    public void parseTextParserTest(String text,
                                    List<String> expectedList) {
        StringParser parser = new TextParser();
        List<String> list = parser.parse(text);
        assertEquals(expectedList, list);
    }


    @DataProvider
    public Object[][] parseSentenceParserParseData() {
        return new Object[][]{
                {
                        "Rostik is good, I feel, bad!",
                        Arrays.asList("Rostik", "is", "good,", "I", "feel,", "bad!")
                }
        };
    }

    @Test(dataProvider = "parseSentenceParserParseData")
    public void parseSentenceParserTest(String text,
                                        List<String> expectedList) {
        StringParser parser = new SentenceParser();
        List<String> list = parser.parse(text);
        assertEquals(expectedList, list);
    }


    @DataProvider
    public Object[][] parseLexemeParserParseData() {
        return new Object[][]{
                {
                        "Rostik, is good- This is not good. I feel bad.",
                        Arrays.asList("Rostik", ",", "is", "good", "-", "This", "is",
                                "not", "good", ".", "I", "feel", "bad", ".")
                }
        };
    }

    @Test(dataProvider = "parseLexemeParserParseData")
    public void parseLexemeParserTest(String text, List<String> expectedList) {
        StringParser parser = new LexemeParser();
        List<String> list = parser.parse(text);
        assertEquals(expectedList, list);
    }

    @DataProvider
    public Object[][] parseWordParserParseData() {
        return new Object[][]{
                {
                        "Rostik",
                        Arrays.asList("R", "o", "s", "t", "i", "k")
                },
                {
                        ",",
                        Collections.singletonList(",")

                },
                {
                        "R",
                        Collections.singletonList("R")

                }
        };
    }

    @Test(dataProvider = "parseWordParserParseData")
    public void parseWordParserTest(String text, List<String> expectedList) {
        StringParser parser = new LexemePartParser();
        List<String> list = parser.parse(text);
        assertEquals(expectedList, list);
    }

    @DataProvider
    public Object[][] checkWordRegexParseData() {
        return new Object[][]{
                {
                        "Rostik",
                        true
                },
                {
                        ",",
                        false

                },
                {
                        " ,;wdd,.;+))",
                        false

                }
        };
    }

    @Test(dataProvider = "checkWordRegexParseData")
    public void checkWordRegexText(String str, boolean expected) {
        assertEquals(str.matches(LexemeParser.WORD_PATTERN), expected);
    }

    @DataProvider
    public Object[][] checkExpressionRegexParseData() {
        return new Object[][]{
                {
                        "Rostik",
                        false
                },
                {
                        "(223)||",
                        true

                },
                {
                        " ,;wdd,.;+))",
                        false
                },
                {
                        "(",
                        false
                },
                {
                    "(rock3r334)",
                        false
                }
        };
    }

    @Test(dataProvider = "checkExpressionRegexParseData")
    public void checkExpressionRegexTest(String str, boolean expected) {
        Pattern pattern = Pattern.compile("([(]*[0-9<>|&^~]+[)]*)+");
        assertEquals(pattern.matcher(str).matches(), expected);
    }

    @DataProvider
    public Object[][] checkWordPastCharsRegexParseData() {
        return new Object[][]{
                {
                        "Rostik",
                        false
                },
                {
                        ",",
                        true

                },
                {
                        ")",
                        true

                },
                {
                        ")wwd",
                        false

                },
                {
                        "...",
                        true
                }
        };
    }

    @Test(dataProvider = "checkWordPastCharsRegexParseData")
    public void checkWordPastCharsRegexTest(String str, boolean expected) {
        assertEquals(str.matches(LexemeParser.SPEC_SYMBOL_PATTERN), expected);
    }
}
