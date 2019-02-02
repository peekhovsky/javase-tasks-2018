package by.epam.task3.calculation.operand;


/**
 * This enum describes different types of operations.
 *
 * @author Rostislav Pekhovsky 2018
 * @version 0.1
 * @see Operator
 */
public enum OperatorTypes {
    /**
     * Not operationSign.
     */
    NOT("~", 2),
    /**
     * Left shift operationSign.
     */
    LEFT_SHIFT("<<", 5),
    /**
     * Right shift operationSign.
     */
    RIGHT_SHIFT(">>", 5),
    /**
     * And operationSign.
     */
    AND("&", 8),
    /**
     * Xor operationSign.
     */
    XOR("^", 9),
    /**
     * Or operationSign.
     */
    OR("|", 10),
    /**
     * Opening bracket.
     */
    OPEN_BRACKET("(", 11),
    /**
     * Closing bracket.
     */
    CLOSE_BRACKET(")", 11);

    /**
     * Operation symbol sign.
     */
    private final String operationSign;
    /**
     * Operation priority (1 - top priority).
     */
    private final int priority;

    /**
     * Constructor.
     *
     * @param operationSignNew operation symbol sign
     * @param priorityNew      priority number
     */
    OperatorTypes(final String operationSignNew, final int priorityNew) {
        this.operationSign = operationSignNew;
        this.priority = priorityNew;
    }

    /**
     * @return operation symbol sign
     */
    public String getOperationSign() {
        return operationSign;
    }

    /**
     * @return priority number
     */
    public int getPriority() {
        return priority;
    }
}
