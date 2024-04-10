import java.io.InputStream;
import java.text.ParseException;

public class Parser {
    LexicalAnalyzer lex;

    Tree S() throws ParseException {
        switch (lex.curToken().getType()) {
            case NAME:
                // name
                String name = lex.curToken().getName();
                lex.nextToken();
                // P
                Tree variable = P();
                // N
                Tree listOther = N();
                // ;
                if (lex.curToken().getType() != Type.SEMICOLON) {
                    throw new ParseException("; expected at position", lex.curPos());
                }
                lex.nextToken();
                // S
                Tree nextLine = S();

                return new Tree("S", false, new Tree(name, true), variable, listOther, new Tree(";", true), nextLine);
            case SEMICOLON:
                // ;
                lex.nextToken();
                // S
                Tree next = S();

                return new Tree("S", false, new Tree(";", true), next);
            case END:
                // eps
                return new Tree("S", false);
            default:
                throw new AssertionError();
        }
    }

    Tree P() throws ParseException {
        switch (lex.curToken().getType()) {
            case POINTER:
                // *
                lex.nextToken();
                // P
                Tree cont = P();

                return new Tree("P", false, new Tree("*", true), cont);
            case AMPERSAND:
            case NAME:
                // L
                Tree sub = L();

                return new Tree("P", false, sub);
            default:
                throw new AssertionError();
        }
    }

    Tree N() throws ParseException {
        switch (lex.curToken().getType()) {
            case COMMA:
                // ,
                lex.nextToken();
                // P
                Tree sub = P();
                // N
                Tree cont = N();

                return new Tree("N", false, new Tree(",", true), sub, cont);
            case SEMICOLON:
                return new Tree("N", false);
            default:
                throw new AssertionError();
        }
    }

    Tree L() throws ParseException {
        String name;
        switch (lex.curToken().getType()) {
            case AMPERSAND:
                // &
                lex.nextToken();
                // name
                if (lex.curToken().getType() != Type.NAME) {
                    throw new ParseException("NAME expected at position", lex.curPos());
                }
                name = lex.curToken().getName();
                lex.nextToken();

                return new Tree("L", false, new Tree("&", true), new Tree(name, true));
            case NAME:
                name = lex.curToken().getName();
                lex.nextToken();

                return new Tree("L", false, new Tree(name, true));
            default:
                throw new AssertionError();
        }
    }

    Tree parse(InputStream is) throws ParseException {
        lex = new LexicalAnalyzer(is);
        lex.nextToken();
        return S();
    }
}
