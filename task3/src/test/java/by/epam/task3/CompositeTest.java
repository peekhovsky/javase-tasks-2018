package by.epam.task3;

import by.epam.task3.composite.TextComponent;
import by.epam.task3.composite.implementation.TextNodeImpl;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class CompositeTest {

    @DataProvider
    public Object[][] compositeConstructorParseData() {
        String expectedText = "\tIt has survived - not only (five) centuries," +
                " but " +
                "also the leap into 52 electronic typesetting, remaining " +
                "0 essentially 9 unchanged. It was popularised in the" +
                " 5 with the release of Letraset" +
                " sheets containing Lorem Ipsum passages, and more recently with" +
                " desktop publishing software like Aldus PageMaker including " +
                "versions of Lorem Ipsum...\n\tIt is a long established fact that " +
                "a reader will be distracted by the readable content of a page when" +
                "looking at its layout. The point of using " +
                "78 Ipsum is that it has a " +
                "more-or-less normal distribution of letters, as opposed to using" +
                " (Content here), content here', making it look like readable English." +
                "\n\tIt is a 1212 established fact that a" +
                " reader will be of a page when looking at its layout. Bye...";

        String text = "\tIt has survived - not only (five) centuries, but " +
                "also the leap into 13<<2 electronic typesetting, remaining " +
                "3>>5 essentially ~6&9|(3&4) unchanged. It was popularised in the" +
                " 5|(1&2&(3|(4&(4^5|6&47)|3)|2)|1) with the release of Letraset" +
                " sheets containing Lorem Ipsum passages, and more recently with" +
                " desktop publishing software like Aldus PageMaker including " +
                "versions of Lorem Ipsum... \n\tIt is a long established fact that " +
                "a reader will be distracted by the readable content of a page when" +
                "looking at its layout. The point of using " +
                "~71&(2&3|(3|(2&1>>2|2)&2)|10&2)|78 Ipsum is that it has a " +
                "more-or-less normal distribution of letters, as opposed to using" +
                " (Content here), content here', making it look like readable English. " +
                "\n\tIt is a (9^5|1&2<<(2|5>>2&71))|1200 established fact that a" +
                " reader will be of a page when looking at its layout. Bye... ";

        return new Object[][] {
                {
                        text,
                        expectedText
                },
                {
                    "\tRock.",
                    "\tRock."
                },
                {
                    "\tRock 5|34 sauce.",
                    "\tRock 39 sauce.",
                }
        };
    }

    @Test(dataProvider = "compositeConstructorParseData")
    public void compositeConstructorTest(String text, String expectedText) {
        TextComponent component = new TextNodeImpl(text);
        assertEquals(component.createText(), expectedText);
    }
}
