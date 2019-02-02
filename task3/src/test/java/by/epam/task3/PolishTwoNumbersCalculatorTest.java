package by.epam.task3;

import by.epam.task3.calculation.calculator.LambdaCalculator;
import by.epam.task3.calculation.operand.Operand;
import by.epam.task3.calculation.operand.ExpressionPart;
import by.epam.task3.calculation.operand.Operator;
import by.epam.task3.calculation.operand.OperatorTypes;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.assertEquals;

public class PolishTwoNumbersCalculatorTest {

    private LambdaCalculator lambdaCalculator = new LambdaCalculator();

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

        //123>>2
        //(3|4)&23^3
        //(3|567)&(23^4|7)~3
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
                }
        };
    }

    @Test(dataProvider = "calcTestParseData")
    public void calcTest(List<ExpressionPart> expressionParts,
                            int expectedValue) {
        assertEquals(lambdaCalculator.calc(expressionParts), expectedValue);
    }
}
