package expression.exceptions;

import expression.AnyExpression;

public class CheckedNegate extends expression.Negate {
    public CheckedNegate(AnyExpression operand) {
        super(operand);
    }

    @Override
    public int eval(int a) {
        if (a == Integer.MIN_VALUE) {
            throw new expression.exceptions.ExpressionOverflowException(this);
        }
        return super.eval(a);
    }
}
