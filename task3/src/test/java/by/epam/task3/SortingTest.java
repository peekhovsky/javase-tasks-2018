package by.epam.task3;

import by.epam.task3.composite.TextNode;
import by.epam.task3.composite.implementation.TextNodeImpl;
import by.epam.task3.sorter.LexemeInTextSorter;
import by.epam.task3.sorter.ParagraphsInTextSorter;
import by.epam.task3.sorter.WordInSentenceSorter;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class SortingTest {
    @DataProvider
    public Object[][] sortBySentencesTestParseData() {
        String text = "\tRock n rookk ee ef, ef. Rererr e re e r. Tr er." +
                "\n\tToeffef fef efef. E efefe fee fe efe fe fe fe." +
                "\n\tGrrgrgr grg.";
        String expectedText =   "\tGrrgrgr grg."+
                "\n\tToeffef fef efef. E efefe fee fe efe fe fe fe." +
                "\n\tRock n rookk ee ef, ef. Rererr e re e r. Tr er.";

        return new Object[][] {
                {
                        text,
                        expectedText
                }
        };
    }

    @Test(dataProvider = "sortBySentencesTestParseData")
    public void sortBySentencesTest(String text, String expectedText) {
        TextNode component = new TextNodeImpl(text);
        new ParagraphsInTextSorter().sort(component);
        assertEquals(component.createText(), expectedText);
    }

    @DataProvider
    public Object[][] sortByWordsInSentencesTestParseData() {
        String text = "\tTerrewewe wew ewww w, wergrh, wewew wdwd. Wwddwd dv, rgr!";
        String expectedText =  "\tw wew ewww wdwd, wewew, wergrh Terrewewe. dv rgr, Wwddwd!";

        return new Object[][] {
                {
                        text,
                        expectedText
                }
        };
    }

    @Test(dataProvider = "sortByWordsInSentencesTestParseData")
    public void sortByWordsInSentencesTest(String text, String expectedText) {
        TextNode component = new TextNodeImpl(text);
        new WordInSentenceSorter().sort(component);
        assertEquals(component.createText(), expectedText);
    }


    @DataProvider
    public Object[][] sortByLexemesInTextTestParseData() {
        String text1 = "\twew terrewewe ewww w, wergrh, wewew"
                + " wdwd. Wwddwd dv, rgr!";

        String expectedText1 =  "\tewww wewew terrewewe wdwd. "
                + "wew Wwddwd w, wergrh, dv, rgr!";

        String text2 = "\trrrock or stone is a natural substance, a solid"
                + " aggregate of one or more minerals or mineraloids."
                + " For example, granite, a corrmmon rock, is a combination "
                + "of the minerals quartz, feldspar and biotite. "
                + "The Earth's outer solid layerrrrrrr, the lithosphere, "
                + "is made of rock.";

        String expectedText2 =  "\tlayerrrrrrr, rrrock corrmmon aggregate "
                + "Earth's feldspar For granite, lithosphere, mineraloids."
                + " minerals minerals more natural or or or outer quartz, rock,"
                + " rock. a a a a and biotite. combination example, is is is"
                + " made of of of one solid solid stone substance, the The the";

        return new Object[][] {
                {
                        text1,
                        expectedText1
                },
                {
                        text2,
                        expectedText2
                },
        };
    }

    @Test(dataProvider = "sortByLexemesInTextTestParseData")
    public void sortByLexemesInTextTest(String text, String expectedText) {
        TextNode component = new TextNodeImpl(text);
        new LexemeInTextSorter(text.charAt(1)).sort(component);
        assertEquals(component.createText(), expectedText);
    }
}
