package by.epam.task3;

import by.epam.task3.calculation.SimpleExpressionCalculator;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class SimpleExpressionCalculatorTest {

    //13<<2
    //3>>5
    //~6&9|(3&4)
    //5|(1&2&(3|(4&(4^5|6&47)|3)|2)|1)
    //(~71&(2&3|(3|(2&1>>2|2)&2)|10&2)|78
    //(9^5|1&2<<(2|5>>2&71))|1200

    private SimpleExpressionCalculator calculator
            = SimpleExpressionCalculator.getInstance();

    @SuppressWarnings("Dublicates")
    @DataProvider
    public Object[][] calcTestParseData() {

        return new Object[][] {
                {
                        "13<<2",
                        13<<2
                },
                {
                        "3>>5",
                        3>>5
                },

                {
                        "~6&9|(3&4)",
                        ~6&9|(3&4)
                },
                {
                        "5|(1&2&(3|(4&(4^5|6&47)|3)|2)|1)",
                        5|(1&2&(3|(4&(4^5|6&47)|3)|2)|1)
                },
                {
                        "~71&(2&3|(3|(2&1>>2|2)&2)|10&2)|78",
                        ~71&(2&3|(3|(2&1>>2|2)&2)|10&2)|78
                },
                {
                        "(9^5|1&2<<(2|5>>2&71))|120",
                        (9 ^ 5 | 1 & 2 << ( 2 | 5 >> 2 & 71 ) ) | 120
                }

        };
    }

    @Test(dataProvider = "calcTestParseData")
    public void calcTest(String expression, int expectedValue) {
        assertEquals(calculator.calculate(expression),
                expectedValue);
    }
}
