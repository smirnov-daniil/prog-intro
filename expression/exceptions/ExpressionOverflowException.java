package expression.exceptions;

import expression.AnyExpression;

public class ExpressionOverflowException extends ExpressionException {
    public ExpressionOverflowException(AnyExpression expression) {
        super("Overflow", expression);
    }
}
