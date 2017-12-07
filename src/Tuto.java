import org.graphstream.graph.*;
import org.graphstream.graph.implementations.*;

public class Tuto {
  public static void main(String args[]) {
    Graph graph = new SingleGraph("Tuto 1");
    graph.addNode("A");
    graph.addNode("B");
    graph.addNode("C");
    graph.addEdge("AB", "A", "B");
    graph.addEdge("BC", "B", "C");
    graph.addEdge("C", "C", "A");
    graph.display();
  }
}
