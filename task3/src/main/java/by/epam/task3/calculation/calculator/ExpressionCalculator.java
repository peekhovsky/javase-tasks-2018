package by.epam.task3.calculation.calculator;

import by.epam.task3.calculation.operand.ExpressionPart;

import java.util.List;

/**
 * This interface describes calculator that calculates expression parts.
 *
 * @author Rostislav Pekhovsky 2018
 * @version 0.1
 */
@FunctionalInterface
public interface ExpressionCalculator {
    /**
     * @param expressionParts expression parts to calculate
     * @return result of calculation
     */
    int calc(List<ExpressionPart> expressionParts);
}
