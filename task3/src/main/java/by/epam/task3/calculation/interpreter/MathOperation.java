package by.epam.task3.calculation.interpreter;

/**
 * Operation interpreter interface.
 *
 * @author Rostislav Pekhovsky 2018
 * @version 0.1
 */
@FunctionalInterface
public interface MathOperation {
    /**
     * This method interprets operation and depending on operation makes
     * something in context object.
     *
     * @param context context to change
     */
    void interpret(Context context);
}
