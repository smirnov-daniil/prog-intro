package expression;

import java.math.BigInteger;
import java.util.Objects;

public class Variable implements AnyExpression {
    private final String name;

    public Variable(String name) {
        this.name = Objects.requireNonNull(name);
    }

    @Override
    public int evaluate(int variable) {
        return variable;
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return switch (name) {
            case "x" -> x;
            case "y" -> y;
            case "z" -> z;
            default -> throw new IllegalArgumentException("Unknown variable " + name);
        };
    }

    @Override
    public BigInteger evaluate(BigInteger variable) {
        return variable;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null && obj.getClass() == getClass()) {
            Variable var = (Variable) obj;
            return Objects.equals(name, var.name);
        }
        return false;
    }

    @Override
    public String toMiniString() {
        return toString();
    }

    @Override
    public int getPriority() {
        return 100;
    }
}
