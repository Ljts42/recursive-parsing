public class Token {
    final Type type;
    final String name;

    public Token(Type type) {
        this.type = type;
        this.name = "";
    }

    public Token(Type type, String name) {
        this.type = type;
        this.name = name;
    }

    public Type getType() {
        return type;
    }

    public String getName() {
        return name;
    }
}
