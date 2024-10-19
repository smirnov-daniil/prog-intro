package expression.exceptions;

import expression.AnyExpression;

public class ExpressionDivisionByZeroException extends ExpressionException {
    public ExpressionDivisionByZeroException(AnyExpression expression) {
        super("Division by zero", expression);
    }
}
