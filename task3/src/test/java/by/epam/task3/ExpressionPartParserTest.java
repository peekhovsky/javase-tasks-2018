package by.epam.task3;

import by.epam.task3.calculation.converter.ExpressionParser;
import by.epam.task3.calculation.operand.Operand;
import by.epam.task3.calculation.operand.ExpressionPart;
import by.epam.task3.calculation.operand.Operator;
import by.epam.task3.calculation.operand.OperatorTypes;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.assertEquals;


public class ExpressionPartParserTest {

    private ExpressionParser expressionParser = new ExpressionParser();

    @SuppressWarnings("Duplicates")
    @DataProvider
    public Object[][] parseTestParseData() {
        List<ExpressionPart> expectedList1 = new ArrayList<>();
        expectedList1.add(new Operand(123));
        expectedList1.add(new Operator(OperatorTypes.LEFT_SHIFT));
        expectedList1.add(new Operand(2));

        List<ExpressionPart> expectedList2 = new ArrayList<>();
        expectedList2.add(new Operator(OperatorTypes.OPEN_BRACKET));
        expectedList2.add(new Operator(OperatorTypes.XOR));
        expectedList2.add(new Operand(5));
        expectedList2.add(new Operator(OperatorTypes.OR));
        expectedList2.add(new Operand(1));
        expectedList2.add(new Operator(OperatorTypes.AND));
        expectedList2.add(new Operand(2));
        expectedList2.add(new Operator(OperatorTypes.LEFT_SHIFT));
        expectedList2.add(new Operator(OperatorTypes.OPEN_BRACKET));
        expectedList2.add(new Operand(2));
        expectedList2.add(new Operator(OperatorTypes.OR));
        expectedList2.add(new Operand(5));
        expectedList2.add(new Operator(OperatorTypes.RIGHT_SHIFT));
        expectedList2.add(new Operand(2));
        expectedList2.add(new Operator(OperatorTypes.AND));
        expectedList2.add(new Operand(71));
        expectedList2.add(new Operator(OperatorTypes.CLOSE_BRACKET));
        expectedList2.add(new Operator(OperatorTypes.CLOSE_BRACKET));
        expectedList2.add(new Operator(OperatorTypes.OR));
        expectedList2.add(new Operand(1200));

        return new Object[][] {
                {
                    "123<<2",
                        expectedList1
                },
                {
                     "(^5|1&2<<(2|5>>2&71))|1200",
                        expectedList2
                }
        };
    }

    @Test(dataProvider = "parseTestParseData")
    public void parseTest(final String str,
                          final List<ExpressionPart> expectedList) {
        assertEquals(expressionParser.parse(str), expectedList);
    }
}
