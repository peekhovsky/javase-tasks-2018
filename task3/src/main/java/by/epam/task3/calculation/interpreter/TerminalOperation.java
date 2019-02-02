package by.epam.task3.calculation.interpreter;

import by.epam.task3.calculation.operand.Operand;

/**
 * Terminal operation class.
 * Terminal operations only add operands into context.
 *
 * @author Rostislav Pekhovsky 2018
 * @version 0.1
 */
public final class TerminalOperation implements MathOperation {

    /**
     * Operand.
     */
    private Operand numberOperand;

    /**
     * Constructor.
     *
     * @param operandNew operand to set
     */
    public TerminalOperation(final Operand operandNew) {
        this.numberOperand = operandNew;
    }

    @Override
    public void interpret(final Context context) {
        context.pushNumber(numberOperand);
    }
}
