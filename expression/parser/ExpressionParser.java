package expression.parser;

import expression.TripleExpression;
import expression.parser.exceptions.*;

/**
 * @author DS2
 */
public class ExpressionParser implements TripleParser {
    @Override
    public TripleExpression parse(String expression) {
        try {
            return new RecursiveParser(expression, new Operators() {}).parse();
        } catch (ParserInvalidOperatorException | ParserInvalidConstantException |
                 ParserTokenException | ParserEndOfFileException e) {
            System.err.println(e.getMessage());
            Throwable cause = e.getCause();
            while (cause != null) {
                System.err.println("Caused by: " + cause.getMessage());
                cause = cause.getCause();
            }
            System.exit(1);
        }
        return null;
    }
}
