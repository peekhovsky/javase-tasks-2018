package by.epam.task3.calculation.interpreter;

import by.epam.task3.calculation.operand.Operand;

import java.util.ArrayDeque;
import java.util.Deque;


/**
 * Class that stores all data for interpreter.
 *
 * @author Rostislav Pekhovsky 2018
 * @version 0.1
 * @see MathOperation
 */

public class Context {

    /**
     * Result stack.
     */
    private Deque<Operand> resultStack = new ArrayDeque<>();

    /**
     * @return last number in result stack (and delete it from stack)
     */
    public Operand pullNumber() {
        return resultStack.pollLast();
    }

    /**
     * @param operand operand to push to result stack
     */
    public void pushNumber(final Operand operand) {
        resultStack.addLast(operand);
    }

    /**
     * @return result stack
     */
    public Deque<Operand> getResultStack() {
        return resultStack;
    }
}
