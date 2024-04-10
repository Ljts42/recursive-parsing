import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Tree {
    String node;
    boolean isTerm;
    List<Tree> children;

    public Tree(String node, boolean isTerm, Tree... children) {
        this.node = node;
        this.isTerm = isTerm;
        this.children = Arrays.asList(children);
    }

    public Tree(String node, boolean isTerm) {
        this.node = node;
        this.isTerm = isTerm;
        this.children = Collections.emptyList();
    }

    private int toString(StringBuilder graph, int number) {
        graph.append("\tnode").append(number);
        graph.append(" [label = \"").append(node).append("\"");
        if (isTerm) {
            graph.append(" shape=hexagon");
        }
        graph.append("]\n");
        int counter = 1;
        for (Tree child : children) {
            graph.append("\tnode").append(number);
            graph.append(" -> node").append(number + counter).append("\n");
            counter += child.toString(graph, number + counter);
        }
        return counter;
    }

    @Override
    public String toString() {
        StringBuilder graph = new StringBuilder();
        graph.append("digraph {\n");
        toString(graph, 0);
        graph.append("}");
        return graph.toString();

//        StringBuilder graph = new StringBuilder();
//        graph.append("new Tree(\"").append(node).append("\", ").append(isTerm);
//        for (Tree child : children) {
//            graph.append(", ").append(child.toString());
//        }
//        graph.append(")");
//        return graph.toString();
    }
}
