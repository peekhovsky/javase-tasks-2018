package by.epam.task3.calculation.operand;

import java.util.Objects;


/**
 * This class describes operand as a part of expression.
 *
 * @author Rostislav Pekhovsky 2018
 * @version 0.1
 */
public final class Operand extends ExpressionPart {

    /**
     * Value.
     */
    private Integer value;

    /**
     * Constructor.
     *
     * @param valueNew int value to set
     */
    public Operand(final Integer valueNew) {
        this.value = valueNew;
    }

    /**
     * @return value
     */
    public Integer getValue() {
        return value;
    }

    @Override
    public boolean equals(final Object o) {
        boolean res;
        if (this == o) {
            res = true;
        } else if (o == null || getClass() != o.getClass()) {
            res = false;
        } else {
            Operand numberOperand1 = (Operand) o;
            res = Objects.equals(value, numberOperand1.value);
        }
        return res;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public Object value() {
        return value;
    }

    @Override
    public String toString() {
        return "Operand: " + value + " ";
    }
}
