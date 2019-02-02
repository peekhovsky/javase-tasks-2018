package by.epam.task3;

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


public class ReversePolishConverterTest {

    private ReversePolishConverter convertTestParseData
            = new ReversePolishConverter();

    @SuppressWarnings("Dublicates")
    @DataProvider
    public Object[][] convertToIncompleteFormTestParseData() {

        //123>>2
        List<ExpressionPart> list1 = new ArrayList<>();
        list1.add(new Operand(123));
        list1.add(new Operator(OperatorTypes.RIGHT_SHIFT));
        list1.add(new Operand(2));

        List<ExpressionPart> expectedList1 = new ArrayList<>();
        expectedList1.add(new Operand(123));
        expectedList1.add(new Operand(2));
        expectedList1.add(new Operator(OperatorTypes.RIGHT_SHIFT));

        //(3|4)&23^4
        List<ExpressionPart> list2 = new ArrayList<>();
        list2.add(new Operator(OperatorTypes.OPEN_BRACKET));
        list2.add(new Operand(3));
        list2.add(new Operator(OperatorTypes.OR));
        list2.add(new Operand(4));
        list2.add(new Operator(OperatorTypes.CLOSE_BRACKET));
        list2.add(new Operator(OperatorTypes.AND));
        list2.add(new Operand(23));
        list2.add(new Operator(OperatorTypes.XOR));
        list2.add(new Operand(4));

        //3 4 | 23 & 4 ^
        List<ExpressionPart> expectedList2 = new ArrayList<>();
        expectedList2.add(new Operand(3));
        expectedList2.add(new Operand(4));
        expectedList2.add(new Operator(OperatorTypes.OR));
        expectedList2.add(new Operand(23));
        expectedList2.add(new Operator(OperatorTypes.AND));
        expectedList2.add(new Operand(4));
        expectedList2.add(new Operator(OperatorTypes.XOR));

        //(3|567)&(23^4|7)|~3
        List<ExpressionPart> list3 = new ArrayList<>();
        list3.add(new Operator(OperatorTypes.OPEN_BRACKET));
        list3.add(new Operand(3));
        list3.add(new Operator(OperatorTypes.OR));
        list3.add(new Operand(567));
        list3.add(new Operator(OperatorTypes.CLOSE_BRACKET));
        list3.add(new Operator(OperatorTypes.AND));
        list3.add(new Operator(OperatorTypes.OPEN_BRACKET));
        list3.add(new Operand(23));
        list3.add(new Operator(OperatorTypes.XOR));
        list3.add(new Operand(4));
        list3.add(new Operator(OperatorTypes.OR));
        list3.add(new Operand(7));
        list3.add(new Operator(OperatorTypes.CLOSE_BRACKET));
        list3.add(new Operator(OperatorTypes.OR));
        list3.add(new Operator(OperatorTypes.NOT));
        list3.add(new Operand(3));

        //3 567 | 23 4 ^ 7 | & 3 ~ |
        List<ExpressionPart> expectedList3 = new ArrayList<>();
        expectedList3.add(new Operand(3));
        expectedList3.add(new Operand(567));
        expectedList3.add(new Operator(OperatorTypes.OR));
        expectedList3.add(new Operand(23));
        expectedList3.add(new Operand(4));
        expectedList3.add(new Operator(OperatorTypes.XOR));
        expectedList3.add(new Operand(7));
        expectedList3.add(new Operator(OperatorTypes.OR));
        expectedList3.add(new Operator(OperatorTypes.AND));
        expectedList3.add(new Operand(3));
        expectedList3.add(new Operator(OperatorTypes.NOT));
        expectedList3.add(new Operator(OperatorTypes.OR));

        return new Object[][] {
                {
                    list1,
                        expectedList1,
                },
                {
                    list2,
                        expectedList2
                },
                {
                        list3,
                        expectedList3
                }
        };
    }

    @Test(dataProvider = "convertToIncompleteFormTestParseData")
    public void convertToIncompleteFormTest(List<ExpressionPart> expressionParts,
                                            List<ExpressionPart> expectedList) {
        assertEquals(convertTestParseData
                .convertToIncompleteForm(expressionParts), expectedList);
    }
}
