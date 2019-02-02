package by.epam.task3.calculation;

import by.epam.task3.calculation.calculator.ExpressionCalculator;
import by.epam.task3.calculation.calculator.InterpreterCalculator;
import by.epam.task3.calculation.converter.ExpressionParser;
import by.epam.task3.calculation.converter.ReversePolishConverter;
import by.epam.task3.calculation.operand.ExpressionPart;

import java.util.List;

/**
 * Class that has all tools to calculate a string expression.
 * It is a singleton.
 *
 * @author Rostislav Pekhovsky 2018
 * @version 0.1
 */
public final class SimpleExpressionCalculator {

    /**
     * This field is to make singleton.
     */
    private static SimpleExpressionCalculator simpleExpressionCalculator;

    /**
     * Expression parser.
     */
    private ExpressionParser parser;

    /**
     * Reverse polish converter.
     */
    private ReversePolishConverter converter;

    /**
     * Expression calculator.
     */
    private ExpressionCalculator calculator;

    /**
     * Private constructor.
     */
    private SimpleExpressionCalculator() {
        parser = new ExpressionParser();
        converter = new ReversePolishConverter();
        calculator = new InterpreterCalculator();
    }

    /**
     * @return an instance of that class
     */
    public static SimpleExpressionCalculator getInstance() {
        if (simpleExpressionCalculator == null) {
            simpleExpressionCalculator = new SimpleExpressionCalculator();
        }
        return simpleExpressionCalculator;
    }

    /**
     * This method make all steps to find a result of string expression.
     * @param expression string expression to process
     * @return result
     */
    public int calculate(final String expression) {
        List<ExpressionPart> expressionParts = parser.parse(expression);
        expressionParts = converter.convertToIncompleteForm(expressionParts);
        return calculator.calc(expressionParts);
    }
}
