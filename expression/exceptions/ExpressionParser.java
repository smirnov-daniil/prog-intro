package expression.exceptions;

import expression.TripleExpression;
import expression.parser.RecursiveParser;
import expression.parser.exceptions.*;

/**
 * @author DS2
 */
public class ExpressionParser implements TripleParser {
    @Override
    public TripleExpression parse(String expression)
            throws ParserInvalidOperatorException, ParserInvalidConstantException,
            ParserTokenException, ParserEndOfFileException {
        return new RecursiveParser(expression, new CheckedOperators()).parse();
    }
}
