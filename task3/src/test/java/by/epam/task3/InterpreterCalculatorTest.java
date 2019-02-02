package by.epam.task3;

import by.epam.task3.calculation.calculator.ExpressionCalculator;
import by.epam.task3.calculation.calculator.InterpreterCalculator;
import by.epam.task3.calculation.converter.ExpressionParser;
import by.epam.task3.calculation.converter.ReversePolishConverter;
import by.epam.task3.calculation.operand.Operand;
import by.epam.task3.calculation.operand.ExpressionPart;
import by.epam.task3.calculation.operand.Operator;
import by.epam.task3.calculation.operand.OperatorTypes;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.assertEquals;

public class InterpreterCalculatorTest {
    private ExpressionCalculator calculator
            = new InterpreterCalculator();

    @SuppressWarnings("Dublicates")
    @DataProvider
    public Object[][] calcTestParseData() {

        //123 2 >>
        List<ExpressionPart> list1 = new ArrayList<>();
        list1.add(new Operand(123));
        list1.add(new Operand(2));
        list1.add(new Operator(OperatorTypes.RIGHT_SHIFT));

        //3 4 | 23 & 4 ^
        List<ExpressionPart> list2 = new ArrayList<>();
        list2.add(new Operand(3));
        list2.add(new Operand(4));
        list2.add(new Operator(OperatorTypes.OR));
        list2.add(new Operand(23));
        list2.add(new Operator(OperatorTypes.AND));
        list2.add(new Operand(4));
        list2.add(new Operator(OperatorTypes.XOR));

        //3 567 | 23 4 ^ 7 | & 3 ^
        List<ExpressionPart> list3 = new ArrayList<>();
        list3.add(new Operand(3));
        list3.add(new Operand(567));
        list3.add(new Operator(OperatorTypes.OR));
        list3.add(new Operand(23));
        list3.add(new Operand(4));
        list3.add(new Operator(OperatorTypes.XOR));
        list3.add(new Operand(7));
        list3.add(new Operator(OperatorTypes.OR));
        list3.add(new Operator(OperatorTypes.AND));
        list3.add(new Operand(3));
        list3.add(new Operator(OperatorTypes.XOR));

        //3 4 ~ | 23 & 9 ^ ~
        List<ExpressionPart> list4 = new ArrayList<>();
        list4.add(new Operand(3));
        list4.add(new Operand(4));
        list4.add(new Operator(OperatorTypes.NOT));
        list4.add(new Operator(OperatorTypes.OR));
        list4.add(new Operand(23));
        list4.add(new Operator(OperatorTypes.AND));
        list4.add(new Operand(9));
        list4.add(new Operator(OperatorTypes.NOT));
        list4.add(new Operator(OperatorTypes.XOR));

        return new Object[][] {
                {
                        list1,
                        123>>2
                },
                {
                        list2,
                        (3|4)&23^4
                },

                {
                        list3,
                        (3|567)&(23^4|7)^3
                },
                {
                        list4,
                        (3|(~4))&23^(~9)
                }

        };
    }

    @Test(dataProvider = "calcTestParseData")
    public void calcTest(List<ExpressionPart> expressionParts,
                            int expectedValue) {
        assertEquals(calculator.calc(expressionParts), expectedValue);
    }
}
