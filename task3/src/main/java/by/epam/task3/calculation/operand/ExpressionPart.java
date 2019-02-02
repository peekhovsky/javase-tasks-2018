package by.epam.task3.calculation.operand;

/**
 * Interface that describes part of expression: operand or operation.
 *
 * @author Rostislav Pekhovsky 2018
 * @version 0.1
 */
public abstract class ExpressionPart {
    /**
     * @return value of that expression
     */
    public abstract Object value();
}
