package by.epam.task3.composite.state;

import by.epam.task3.calculation.SimpleExpressionCalculator;
import by.epam.task3.composite.implementation.TextLeafImpl;

import java.util.List;
import java.util.stream.Collectors;

/***
 * This class describes text state for expressions.
 * It has calculator field for making calculations from expression. A result of
 * calculating will be stored in components.
 *
 * @author Rostislav Pekhovksy 2018
 * @version 0.1
 */
public final class ExpressionState extends AbstractTextState {

    /***
     * Expression calculator to make calculations from expression.
     */
    private SimpleExpressionCalculator calculator;

    /**
     * No args constructor.
     */
    ExpressionState() {
        calculator = SimpleExpressionCalculator.getInstance();
    }

    /**
     * This method calculates expression from string, splits result
     * into chars, creates leaf components, and adds them to children list.
     *
     * @param expression expression to process
     */
    @Override
    public void createStructure(final String expression) {
        String res = Integer.toString(
                calculator.calculate(expression));
        final List<Character> chars = getParser()
                .parse(res)
                .stream()
                .map(ch -> ch.charAt(0))
                .collect(Collectors.toList());

        for (Character ch : chars) {
            getChildren().add(new TextLeafImpl(ch));
        }
    }

    @Override
    public String createText() {
        return String.join("", makeStringListFromComponents());
    }
}
