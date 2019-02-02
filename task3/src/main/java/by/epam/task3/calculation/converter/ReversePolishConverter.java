package by.epam.task3.calculation.converter;

import by.epam.task3.calculation.operand.Operand;
import by.epam.task3.calculation.operand.ExpressionPart;
import by.epam.task3.calculation.operand.Operator;
import by.epam.task3.calculation.operand.OperatorTypes;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.Objects;
import java.util.ArrayList;

/**
 * Class that contains tools to convert a list of expression part to
 * reverse polish notation.
 *
 * @author Rostislav Pekhovsky 2018
 * @version 0.1
 */
public final class ReversePolishConverter {

    /**
     * Logger.
     */
    private static final Logger LOGGER
            = LogManager.getLogger(ReversePolishConverter.class);

    /**
     * Result stack.
     */
    private Deque<ExpressionPart> resultStack = new ArrayDeque<>();

    /**
     * Operator stack.
     */
    private Deque<Operator> operatorStack = new ArrayDeque<>();

    /**
     * Pushes all operators after open bracket from operator stack
     * to result stack.
     */
    private void pushAllAfterCloseBracket() {
        while (Objects.nonNull(operatorStack.peekLast())
                && (operatorStack.peekLast().getOperationType()
                != OperatorTypes.OPEN_BRACKET)) {
            resultStack.addLast(operatorStack.pollLast());
        }
        operatorStack.pollLast();
    }

    /**
     * Pushes last operator from operator stack
     * to result stack and adds new operator to operator stack.
     * @param operator operator to add
     */
    private void pushLast(final Operator operator) {
        if (!operatorStack.isEmpty()) {
            Operator prevOperator = operatorStack.peekLast();
            if (prevOperator.getOperationType().getPriority()
                    <= operator.getOperationType().getPriority()) {
                resultStack.addLast(operatorStack.pollLast());
            }
        }
        operatorStack.addLast(operator);
    }

    /**
     * Pushes all operators from operator stack
     * to result stack.
     */
    private void pushAll() {
        while (!operatorStack.isEmpty()) {
            resultStack.addLast(operatorStack.pollLast());
        }
    }

    /**
     * Converts an expression list to incomplete polish reverse notation.
     *
     * @param expressionParts expression parts to convert
     * @return result
     */
    public List<ExpressionPart> convertToIncompleteForm(
            final List<ExpressionPart> expressionParts) {
        resultStack.clear();
        operatorStack.clear();
        for (ExpressionPart expressionPart : expressionParts) {
            if (expressionPart instanceof Operator) {
                Operator operator = (Operator) expressionPart;

                switch (operator.getOperationType()) {
                    case OPEN_BRACKET:
                        operatorStack.addLast(operator);
                        break;
                    case CLOSE_BRACKET:
                        pushAllAfterCloseBracket();
                        break;
                    default:
                        pushLast(operator);
                }
            } else if (expressionPart instanceof Operand) {
                Operand numberOperand = (Operand) expressionPart;
                resultStack.add(numberOperand);
            }
            LOGGER.debug("Array: " + resultStack);
            LOGGER.debug("Operation stack: " + operatorStack);
        }
        pushAll();
        LOGGER.debug("Operands: " + resultStack);
        return new ArrayList<>(resultStack);
    }
}
