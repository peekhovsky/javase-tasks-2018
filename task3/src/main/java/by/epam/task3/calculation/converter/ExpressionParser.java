package by.epam.task3.calculation.converter;

import by.epam.task3.calculation.operand.ExpressionPart;
import by.epam.task3.calculation.operand.Operand;
import by.epam.task3.calculation.operand.Operator;
import by.epam.task3.calculation.operand.OperatorTypes;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This class is used to make a list of expression parts from string expression.
 *
 * @author Rostislav Pekhovsky 2018
 * @version 0.1
 */
public final class ExpressionParser {

    /**
     * Logger.
     */
    private static final Logger LOGGER
            = LogManager.getLogger(ExpressionParser.class);

    /**
     * Left shift operation symbols.
     */
    private static final String LEFT_SHIFT = "<<";

    /**
     * Right shift operation symbols.
     */
    private static final String RIGHT_SHIFT = ">>";

    /**
     * Number regular expression.
     */
    private static final String NUMBER_REGEX = "[0-9]+";

    /**
     * This method makes a list of expression parts from string with expression.
     *
     * @param expression expression to parse
     * @return a list of expression parts
     */
    public List<ExpressionPart> parse(final String expression) {
        List<ExpressionPart> exprParts = new ArrayList<>();
        List<Character> chars = expression.chars().mapToObj(e -> (char) e)
                .collect(Collectors.toList());
        chars.add('\n');
        StringBuilder compOperand = new StringBuilder();
        for (char ch : chars) {
            if (compOperand.length() > 0) {
                if (compOperand.toString().equals(LEFT_SHIFT)) {
                    exprParts.add(new Operator(OperatorTypes.LEFT_SHIFT));
                    compOperand = new StringBuilder();

                } else if (compOperand.toString().equals(RIGHT_SHIFT)) {
                    exprParts.add(new Operator(OperatorTypes.RIGHT_SHIFT));
                    compOperand = new StringBuilder();

                } else if (compOperand.toString().matches(NUMBER_REGEX)
                        && !isDigit(ch)) {
                    int value = Integer.parseInt(compOperand.toString());
                    exprParts.add(new Operand(value));
                    compOperand = new StringBuilder();
                }
            }
            if (isDigit(ch) || ch == '>' || ch == '<') {
                compOperand.append(ch);
            } else if (ch != '\n') {
                exprParts.add(new Operator(Character.toString(ch)));
            }
            LOGGER.debug("Operands: " + exprParts);
            LOGGER.debug("Complicated operands: " + compOperand);
        }
        return exprParts;
    }

    /**
     * Determines if char is a digit or not.
     *
     * @param ch char
     * @return true if yes, no if not
     */
    private boolean isDigit(final char ch) {
        return (ch >= '0' && ch <= '9');
    }
}
