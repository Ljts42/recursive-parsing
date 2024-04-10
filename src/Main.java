import java.text.ParseException;

public class Main {
    public static void main(String[] args) {
        Parser parser = new Parser();
        try {
            Tree tree = parser.parse(System.in);
            System.out.println(tree.toString());
        } catch (ParseException e) {
            System.err.println("Error on position " + e.getErrorOffset() + ".");
            System.err.println(e.getMessage());
        }
    }
}
