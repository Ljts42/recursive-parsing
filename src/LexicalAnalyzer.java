import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;

public class LexicalAnalyzer {
    InputStream is;
    int curChar;
    int curPos;
    Token curToken;

    public LexicalAnalyzer(InputStream is) throws ParseException {
        this.is = is;
        curPos = 0;
        nextChar();
    }

    private boolean isBlank(int c) {
        return Character.isWhitespace(c) || c == '\n' || c == '\r' || c == '\036' || c == '\025';
    }

    private boolean isFirstNameSymbol(int c) {
        return Character.isLetter(c) || c == '_';
    }

    private boolean isRestNameSymbol(int c) {
        return isFirstNameSymbol(c) || Character.isDigit(c);
    }

    private void nextChar() throws ParseException {
        ++curPos;
        try {
            curChar = is.read();
        } catch (IOException e) {
            throw new ParseException(e.getMessage(), curPos);
        }
    }

    private Token getName() throws ParseException {
        if (isFirstNameSymbol(curChar)) {
            StringBuilder name = new StringBuilder();
            do {
                name.append((char) curChar);
                nextChar();
            } while (isRestNameSymbol(curChar));
            return new Token(Type.NAME, name.toString());
//            return new Token(Type.NAME, "name");
        }
        throw new ParseException("Illegal character " + (char) curChar, curPos);
    }

    public void nextToken() throws ParseException {
        while (isBlank(curChar)) {
            nextChar();
        }
        switch (curChar) {
            case '*':
                nextChar();
                curToken = new Token(Type.POINTER);
                break;
            case ',':
                nextChar();
                curToken = new Token(Type.COMMA);
                break;
            case ';':
                nextChar();
                curToken = new Token(Type.SEMICOLON);
                break;
            case '&':
                nextChar();
                curToken = new Token(Type.AMPERSAND);
                break;
            case -1:
            case '$':
                curToken = new Token(Type.END);
                break;
            default:
                curToken = getName();
        }
    }

    public Token curToken() {
        return curToken;
    }

    public int curPos() {
        return curPos;
    }
}
