import org.graphstream.graph.*;
import org.graphstream.graph.implementations.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List; 


/* Main du projet.
L'exemple un graphe à 3 noeuds et montre comment changer leurs positions
*/


public class Main{
    public static void main(String[] args) throws Exception{
	
	Graph graph = new SingleGraph("Tutorial 1");
	graph.addNode("A" );
	graph.addNode("B" );
	graph.addNode("C" );
	Node A = graph.getNode("A");
	A.setAttribute("xy",3.2,10);

       	Node B = graph.getNode("B");
	B.setAttribute("xy",5,10);
 	Node C = graph.getNode("C");
	C.setAttribute("xy",5,3);
	graph.addEdge("AB", "A", "B");
	graph.addEdge("BC", "B", "C");
	graph.addEdge("CA", "C", "A");
	graph.display(false);

	// Ce qui suis était un test, ne plus l'utiliser
	
	/*
	GrapheRO graph = new GrapheRO();
	GestionBD bd = new GestionBD();
	List<Point> lpoints = new ArrayList<Point>();
	lpoints = bd.obtenirTableEssai();
	bd.deconnexion();
	
	graph.setFrontieres(lpoints);
	Graph graphe = graph.grapheFrontiere();
	graphe.display(false);
	*/
	
    }
}
