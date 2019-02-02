package by.epam.task3.calculation.calculator;

import by.epam.task3.calculation.operand.Operand;
import by.epam.task3.calculation.operand.ExpressionPart;
import by.epam.task3.calculation.operand.Operator;
import by.epam.task3.exception.TaskRuntimeException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.Objects;

/**
 * (NOT A MAIN SOLUTION, TO SEE MAIN SOLUTION GO TO InterpreterCalculator CLASS)
 * <p>
 * Calculator class to calculate a list of operands in polish reverse notation.
 * Uses lambda expressions to calculate expressions.
 *
 * @author Rostislav Pekhovsky 2018
 * @version 0.1
 */
public final class LambdaCalculator implements ExpressionCalculator {

    /**
     * Logger.
     */
    private static final Logger LOGGER
            = LogManager.getLogger(LambdaCalculator.class);

    /**
     * OR two number calculator.
     */
    private TwoNumbersCalculator calcOr = (num1, num2) ->
            new Operand(num1.getValue() | num2.getValue());

    /**
     * AND two number calculator.
     */
    private TwoNumbersCalculator calcAnd = (num1, num2) ->
            new Operand(num1.getValue() & num2.getValue());

    /**
     * XOR two number calculator.
     */
    private TwoNumbersCalculator calcXor = (num1, num2) ->
            new Operand(num1.getValue() ^ num2.getValue());

    /**
     * NOT two number calculator.
     * (Only second number value is needed)
     */
    private TwoNumbersCalculator calcNot = (num1, num2) ->
            new Operand(~num2.getValue());

    /**
     * LEFT_SHIFT two number calculator.
     */
    private TwoNumbersCalculator calcLeftShift = (num1, num2) ->
            new Operand(num1.getValue() << num2.getValue());

    /**
     * RIGHT_SHIFT two number calculator.
     */
    private TwoNumbersCalculator calcRightShift = (num1, num2) ->
            new Operand(num1.getValue() >> num2.getValue());

    @Override
    public int calc(final List<ExpressionPart> expressionParts) {
        Deque<Operand> stack = new ArrayDeque<>();

        for (ExpressionPart expressionPart : expressionParts) {
            LOGGER.debug("Stack: " + stack);
            if (expressionPart instanceof Operator) {
                Operator operator = (Operator) expressionPart;
                if (stack.size() >= 2) {
                    Operand num2 = stack.pollLast();
                    Operand num1 = stack.pollLast();
                    LOGGER.debug("Num2 = " + num2);
                    LOGGER.debug("Num1 = " + num1);

                    TwoNumbersCalculator calculator;
                    switch (operator.getOperationType()) {
                        case AND:
                            calculator = calcAnd;
                            break;
                        case OR:
                            calculator = calcOr;
                            break;
                        case XOR:
                            calculator = calcXor;
                            break;
                        case NOT:
                            calculator = calcNot;
                            break;
                        case LEFT_SHIFT:
                            calculator = calcLeftShift;
                            break;
                        case RIGHT_SHIFT:
                            calculator = calcRightShift;
                            break;
                        default:
                            throw new TaskRuntimeException(
                                    "Unsupported operation type.");

                    }
                    stack.addLast(calculator.calc(num1, num2));
                } else {
                    LOGGER.warn("Stack size is lower than 2. "
                            + "Cannot calculate getOperationType: "
                            + expressionPart.value());
                }
            } else if (expressionPart instanceof Operand) {
                stack.add((Operand) expressionPart);
            }
        }
        LOGGER.debug("Stack: " + stack);

        int res;
        res = Objects.requireNonNull(stack.poll()).getValue();
        LOGGER.info("Result: " + res);
        return res;
    }

    /**
     * Interface to describe a calculator that performs operation with two
     * numbers.
     */
    @FunctionalInterface
    public interface TwoNumbersCalculator {
        /**
         * @param numberOperand1 first number
         * @param numberOperand2 second number
         * @return operand result
         */
        Operand calc(Operand numberOperand1,
                     Operand numberOperand2);
    }
}
