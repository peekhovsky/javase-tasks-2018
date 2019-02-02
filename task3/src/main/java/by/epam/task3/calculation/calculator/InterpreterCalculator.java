package by.epam.task3.calculation.calculator;


import by.epam.task3.calculation.interpreter.MathOperation;
import by.epam.task3.calculation.interpreter.RightShiftOperation;
import by.epam.task3.calculation.interpreter.Context;
import by.epam.task3.calculation.interpreter.LeftShiftOperation;
import by.epam.task3.calculation.interpreter.AndOperation;
import by.epam.task3.calculation.interpreter.NotOperation;
import by.epam.task3.calculation.interpreter.OrOperation;
import by.epam.task3.calculation.interpreter.TerminalOperation;
import by.epam.task3.calculation.interpreter.XorOperation;
import by.epam.task3.calculation.operand.ExpressionPart;
import by.epam.task3.calculation.operand.Operand;
import by.epam.task3.calculation.operand.Operator;
import by.epam.task3.exception.TaskRuntimeException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Objects;


/**
 * This is calculator class to calculate a list of operands in polish reverse
 * notation. Uses interpreter pattern for calculations.
 *
 * @author Rostislav Pekhovsky 2018
 * @version 0.1
 */
public final class InterpreterCalculator implements ExpressionCalculator {

    /**
     * Logger.
     */
    private static final Logger LOGGER
            = LogManager.getLogger(InterpreterCalculator.class);


    /**
     * This method calculates a list of expression parts. It adds operations and
     * operands to expression list and them interprets them.
     *
     * @param expressionParts expression parts to calculate
     * @return result
     */
    @Override
    public int calc(final List<ExpressionPart> expressionParts) {
        List<MathOperation> expressions = new ArrayList<>();
        LOGGER.debug("Exp. parts: " + expressionParts);
        for (ExpressionPart expressionPart : expressionParts) {
            if (expressionPart instanceof Operator) {
                Operator operator = (Operator) expressionPart;
                switch (operator.getOperationType()) {
                    case AND:
                        expressions.add(new AndOperation());
                        break;
                    case OR:
                        expressions.add(new OrOperation());
                        break;
                    case XOR:
                        expressions.add(new XorOperation());
                        break;
                    case NOT:
                        expressions.add(new NotOperation());
                        break;
                    case LEFT_SHIFT:
                        expressions.add(new LeftShiftOperation());
                        break;
                    case RIGHT_SHIFT:
                        expressions.add(new RightShiftOperation());
                        break;
                    default:
                }
            } else if (expressionPart instanceof Operand) {
                expressions.add(
                        new TerminalOperation((Operand) expressionPart));
            } else {
                throw new TaskRuntimeException(
                        "Unsupported expression part type.");
            }
        }
        return interpretExpressions(expressions);
    }

    /**
     * This method interprets full list of expressions.
     *
     * @param expressions expressions to interpret
     * @return result
     */
    private int interpretExpressions(final List<MathOperation> expressions) {
        Context context = new Context();
        expressions.forEach(expression -> {
            expression.interpret(context);
            LOGGER.debug("Expression: " + expression.getClass().getName());
            LOGGER.debug("Stack: " + context.getResultStack());
        });
        Deque<Operand> resultNumberOperands = context.getResultStack();
        if (resultNumberOperands.size() > 1) {
            throw new TaskRuntimeException("Number stack is bigger than 1!");
        }
        int res = Objects
                .requireNonNull(resultNumberOperands.poll())
                .getValue();
        LOGGER.info("Result: " + res);
        return res;
    }
}
