package expression;

import expression.parser.ExpressionParser;

public class Main {
    public static void main(String[] args) {
        final Expression exp = new Add(
                new Subtract(
                        new Multiply(
                                new Variable("x"),
                                new Variable("x")
                        ),
                        new Multiply(
                                new Const(2),
                                new Variable("x")
                        )
                ),
                new Const(1)
        );
        if (args.length == 0) {
            test2();
        } else if (args.length == 1) {
            System.out.println(exp.evaluate(Integer.parseInt(args[0])));
        } else {
            System.err.println("Illegal number of arguments. Expected 1, got " + args.length + ".");
        }
    }
    private static void test2() {
        System.out.println((new ExpressionParser()).parse("10 10").toMiniString());
    }
    private static void test1() {
        Expression
                eq1 = new Multiply(new Const(2), new Variable("x")),
                eq2 = new Multiply(new Const(2), new Variable("x")),
                eq3 = new Multiply(new Variable("x"), new Const(2));
        System.out.println(
                eq1.equals(eq2)
        );
        System.out.println(
                eq1.equals(eq3)
        );
        Expression
                eval = new Subtract(
                        new Multiply(
                                new Const(2),
                                new Variable("x")
                        ),
                        new Const(3)
                );
        System.out.println(
                eval.evaluate(5)
        );
        Expression
                toStr1 = new Subtract(
                        new Multiply(
                                new Const(2),
                                new Variable("x")
                        ),
                        new Const(3)
                ),
                toStr2 = new Divide(
                        new Multiply(
                                new Negate(new Add(
                                        new Const(2),
                                        new Variable("x")
                                )),
                                new Negate(new Variable("x"))
                        ),
                        new Divide(
                                new Multiply(
                                        new Variable("x"),
                                        new Divide(
                                                new Const(3),
                                                new Const(2)
                                        )
                                ),
                                new Add(
                                        new Const(2),
                                        new Variable("x")
                                )
                        )
                ),
                toStr3 = new Divide(
                        new Multiply(
                                new Add(
                                        new Const(2),
                                        new Variable("x")
                                ),
                                new Variable("x")
                        ),
                        new Const(4)
                );
        System.out.println(
                toStr1
        );
        System.out.println(
                toStr1.toMiniString()
        );
        System.out.println(
                toStr2.toMiniString()
        );
        System.out.println(
                toStr2
        );
        System.out.println(
                toStr3.toMiniString()
        );
        System.out.println(
                toStr3
        );
    }
}
