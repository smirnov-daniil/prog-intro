package expression.exceptions;

public class CheckedMultiply extends expression.Multiply {
    public CheckedMultiply(expression.AnyExpression firstOperand, expression.AnyExpression secondOperand) {
        super(firstOperand, secondOperand);
    }

    @Override
    public int eval(int a, int b) {
        if (a > 0 && b > 0 && Integer.MAX_VALUE / a < b ||
                a > 0 && b < 0 && Integer.MIN_VALUE / a > b ||
                a < 0 && b > 0 && Integer.MIN_VALUE / b > a ||
                a < 0 && b < 0 && Integer.MAX_VALUE / a > b) {
            throw new expression.exceptions.ExpressionOverflowException(this);
        }
        return super.eval(a, b);
    }
}
