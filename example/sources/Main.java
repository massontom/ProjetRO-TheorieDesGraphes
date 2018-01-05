import org.graphstream.graph.*;
import org.graphstream.graph.implementations.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List; 
import unite.UniteTemp;
import java.util.Scanner;

/* Main du projet.
L'exemple un graphe à 3 noeuds et montre comment changer leurs positions
*/


public class Main{
	public static void main(String[] args) throws Exception{
		int choix=1;
		int choix2=1;
		while (choix != 2) {
		        System.out.println("--- Menu --- \n1/  Acceder a la base de donnees\n2/  Quitter\n");
		        Scanner sc = new Scanner(System.in);
		        choix = sc.nextInt();
			switch(choix) {
				case 1 :
					UniteTemp uTemp;	
					GestionBD bd;
					GrapheRO graphro;
					System.out.println("--- Choisissez une unite temporelle --- \n1/  Mois\n2/  Jour\n3/  Heure\n4/  Minute\n5/  Quitter la base de donnees\n");
					Scanner sc2 = new Scanner(System.in);
					choix2 = sc.nextInt();
					switch(choix2) {
						case 1 :
							uTemp = UniteTemp.MOIS;	
							bd = new GestionBD();
							graphro = new GrapheRO(bd, uTemp);
							graphro.afficher();
							break;
						case 2 :
							uTemp = UniteTemp.JOUR;
							bd = new GestionBD();	
							graphro = new GrapheRO(bd, uTemp);
							graphro.afficher();
							break;
						case 3 :
							uTemp = UniteTemp.HEURE;
							bd = new GestionBD();	
							graphro = new GrapheRO(bd, uTemp);
							graphro.afficher();
							break;
						case 4 :
							uTemp = UniteTemp.MINUTE;
							bd = new GestionBD();	
							graphro = new GrapheRO(bd, uTemp);
							graphro.afficher();
							break;
						case 5 :
							break;
						default :
							break;
					}
					break;
				case 2 :
					break;
				default :
					break;
		        }
		}
	/*
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
	*/


	//UniteTemp uTemp = UniteTemp.MOIS;	
	//GestionBD bd = new GestionBD();
	//GrapheRO graphro = new GrapheRO(bd, uTemp);
	//GrapheRO graphro = new GrapheRO();
	
	
	//graphro.afficher();

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
