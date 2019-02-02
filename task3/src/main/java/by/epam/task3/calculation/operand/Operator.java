package by.epam.task3.calculation.operand;

import by.epam.task3.exception.TaskRuntimeException;

import java.util.Objects;

/**
 * This class describes operator as a part of expression.
 *
 * @author Rostislav Pekhovsky 2018
 * @version 0.1
 * @see OperatorTypes
 */
public final class Operator extends ExpressionPart {

    /**
     * Operation type.
     */
    private OperatorTypes operationType;

    /**
     * Constructor.
     *
     * @param operationTypeNew operation type to set
     */
    public Operator(final OperatorTypes operationTypeNew) {
        this.operationType = operationTypeNew;
    }

    /**
     * Constructor.
     *
     * @param operationNew operation sign to set in operation type
     */
    public Operator(final String operationNew) {
        switch (operationNew) {
            case "(":
                operationType = OperatorTypes.OPEN_BRACKET;
                break;
            case ")":
                operationType = OperatorTypes.CLOSE_BRACKET;
                break;
            case "&":
                operationType = OperatorTypes.AND;
                break;
            case "|":
                operationType = OperatorTypes.OR;
                break;
            case "^":
                operationType = OperatorTypes.XOR;
                break;
            case "~":
                operationType = OperatorTypes.NOT;
                break;
            case ">>":
                operationType = OperatorTypes.RIGHT_SHIFT;
                break;
            case "<<":
                operationType = OperatorTypes.LEFT_SHIFT;
                break;
            default:
                throw new TaskRuntimeException("Operation is not valid!");
        }
    }

    /**
     * @return operation type
     */
    public OperatorTypes getOperationType() {
        return operationType;
    }

    @Override
    public Object value() {
        return operationType;
    }

    @Override
    public boolean equals(final Object o) {
        boolean res;
        if (this == o) {
            res = true;
        } else if (o == null || getClass() != o.getClass()) {
            res = false;
        } else {
            Operator numberOperand1 = (Operator) o;
            res = Objects
                    .equals(operationType, numberOperand1.getOperationType());
        }
        return res;
    }

    @Override
    public int hashCode() {
        return Objects.hash(operationType);
    }

    @Override
    public String toString() {
        return "Operation: " + operationType + " ";
    }
}
