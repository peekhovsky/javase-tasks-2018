package by.epam.task3.calculation.interpreter;

import by.epam.task3.calculation.operand.Operand;


/**
 * And (&) operation interpreter.
 *
 * @author Rostislav Pekhovsky 2018
 * @version 0.1
 */
public final class AndOperation implements MathOperation {
    @Override
    public void interpret(final Context context) {
        Operand numberOperand2 = context.pullNumber();
        Operand numberOperand1 = context.pullNumber();
        int res = numberOperand1.getValue() & numberOperand2.getValue();
        context.pushNumber(new Operand(res));
    }
}
