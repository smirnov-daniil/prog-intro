package expression.exceptions;

import expression.AnyExpression;

public class ExpressionException extends ArithmeticException {
    public ExpressionException(final String message, final AnyExpression expression) {
        super(message + " got while evaluating f = " + expression + ".");
    }
}
