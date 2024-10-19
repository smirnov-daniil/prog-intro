package expression.exceptions;

public class CheckedSubtract extends expression.Subtract {
    public CheckedSubtract(expression.AnyExpression firstOperand, expression.AnyExpression secondOperand) {
        super(firstOperand, secondOperand);
    }

    @Override
    public int eval(int a, int b) {
        if (b < 0 && a > Integer.MAX_VALUE + b || b > 0 && a < Integer.MIN_VALUE + b) {
            throw new expression.exceptions.ExpressionOverflowException(this);
        }

        return super.eval(a, b);
    }
}
