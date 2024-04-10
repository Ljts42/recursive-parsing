import org.junit.Test;

import java.io.ByteArrayInputStream;

import static org.junit.Assert.assertEquals;

public class ParserTest {

    @Test
    public void testCorrect() {
        compareTest("int a;", new Tree("S", false, new Tree("int", true), new Tree("P", false, new Tree("a", true)), new Tree("N", false), new Tree(";", true), new Tree("S", false)));
        compareTest("float b, c, d;", new Tree("S", false, new Tree("float", true), new Tree("P", false, new Tree("b", true)), new Tree("N", false, new Tree(",", true), new Tree("P", false, new Tree("c", true)), new Tree("N", false, new Tree(",", true), new Tree("P", false, new Tree("d", true)), new Tree("N", false))), new Tree(";", true), new Tree("S", false)));
        compareTest("double *e;", new Tree("S", false, new Tree("double", true), new Tree("P", false, new Tree("*", true), new Tree("P", false, new Tree("e", true))), new Tree("N", false), new Tree(";", true), new Tree("S", false)));
        compareTest("char ******f;", new Tree("S", false, new Tree("char", true), new Tree("P", false, new Tree("*", true), new Tree("P", false, new Tree("*", true), new Tree("P", false, new Tree("*", true), new Tree("P", false, new Tree("*", true), new Tree("P", false, new Tree("*", true), new Tree("P", false, new Tree("*", true), new Tree("P", false, new Tree("f", true)))))))), new Tree("N", false), new Tree(";", true), new Tree("S", false)));
        compareTest("foo a, *b, ***c, d;", new Tree("S", false, new Tree("foo", true), new Tree("P", false, new Tree("a", true)), new Tree("N", false, new Tree(",", true), new Tree("P", false, new Tree("*", true), new Tree("P", false, new Tree("b", true))), new Tree("N", false, new Tree(",", true), new Tree("P", false, new Tree("*", true), new Tree("P", false, new Tree("*", true), new Tree("P", false, new Tree("*", true), new Tree("P", false, new Tree("c", true))))), new Tree("N", false, new Tree(",", true), new Tree("P", false, new Tree("d", true)), new Tree("N", false)))), new Tree(";", true), new Tree("S", false)));
        compareTest("bar*a,b,c,**d;", new Tree("S", false, new Tree("bar", true), new Tree("P", false, new Tree("*", true), new Tree("P", false, new Tree("a", true))), new Tree("N", false, new Tree(",", true), new Tree("P", false, new Tree("b", true)), new Tree("N", false, new Tree(",", true), new Tree("P", false, new Tree("c", true)), new Tree("N", false, new Tree(",", true), new Tree("P", false, new Tree("*", true), new Tree("P", false, new Tree("*", true), new Tree("P", false, new Tree("d", true)))), new Tree("N", false)))), new Tree(";", true), new Tree("S", false)));
        compareTest("baz ** * **    **  f ;", new Tree("S", false, new Tree("baz", true), new Tree("P", false, new Tree("*", true), new Tree("P", false, new Tree("*", true), new Tree("P", false, new Tree("*", true), new Tree("P", false, new Tree("*", true), new Tree("P", false, new Tree("*", true), new Tree("P", false, new Tree("*", true), new Tree("P", false, new Tree("*", true), new Tree("P", false, new Tree("f", true))))))))), new Tree("N", false), new Tree(";", true), new Tree("S", false)));
        compareTest("int ***x\n, *\n*  y,\t\n z;", new Tree("S", false, new Tree("int", true), new Tree("P", false, new Tree("*", true), new Tree("P", false, new Tree("*", true), new Tree("P", false, new Tree("*", true), new Tree("P", false, new Tree("x", true))))), new Tree("N", false, new Tree(",", true), new Tree("P", false, new Tree("*", true), new Tree("P", false, new Tree("*", true), new Tree("P", false, new Tree("y", true)))), new Tree("N", false, new Tree(",", true), new Tree("P", false, new Tree("z", true)), new Tree("N", false))), new Tree(";", true), new Tree("S", false)));
        compareTest("int a;; float b;", new Tree("S", false, new Tree("int", true), new Tree("P", false, new Tree("a", true)), new Tree("N", false), new Tree(";", true), new Tree("S", false, new Tree(";", true), new Tree("S", false, new Tree("float", true), new Tree("P", false, new Tree("b", true)), new Tree("N", false), new Tree(";", true), new Tree("S", false)))));
        compareTest("", new Tree("S", false));
    }

    private void compareTest(String input, Tree expected) {
        Parser parser = new Parser();
        try {
            Tree res = parser.parse(new ByteArrayInputStream(input.getBytes()));
            assertEquals(res.toString(), expected.toString());
            System.out.println("'" + input + "' parsed correctly");
        } catch (Exception e) {
            System.out.println("error in parsing '" + input + "'");
        }
    }
}