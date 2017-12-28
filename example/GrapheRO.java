import java.util.ArrayList;
import java.util.List; 
import java.lang.StringBuilder;
import org.graphstream.graph.*;
import org.graphstream.graph.implementations.*;


/* Classe permettant de manipuler ce que l'on veux afficher
paramTemp et paramSpat seront renseignés par l'utilisateur,
les points frontières représentent les frontières de l'unité spatiale choisie, et les points positions représentent les positions du détenteur du téléphone, flitrées selon l'unité temporelle choisie.

*/

public class GrapheRO{
	// A modifier : creer des classes paramTemp et paramSpat
	private String paramTemp;
	private String paramSpat;
	private List<Point> frontieres;
	private List<Point> positions;
	
	public void GrapheRO(String uTemp, String uSpat){
		paramTemp = uTemp;
		paramSpat = uSpat;

		// A modifier : creer les bonnes requetes pour aller chercher les bonnes infos
		frontieres = new ArrayList<Point>();
		positions = new ArrayList<Point>();
	}
	
	/* A coder : création de 1 graphes à partir des 2 listes de points
	S'inspirer de grapheFrontiere() mais le généraliser pour que les 2 listes de points soient traitées. Gerer aussi le style du graphe dans cette partie (ajouter des arretes, gerer le css du graphe pour différencier à l'affichage les points frontières des points d'information).
	*/
	public void afficher(){
	
	}



	/*
	public void setFrontieres(List<Point> frontiere){
		frontieres = frontiere;
	}
	
	
	public Graph grapheFrontiere(){
	Graph graph = new SingleGraph("Graphe des frontieres");
	Integer i=0;
	String nomNoeud;
	for (Point pt : frontieres){
		i = i+1;
		nomNoeud = i.toString();
		graph.addNode(nomNoeud);
		Node n = graph.getNode(nomNoeud);
		n.setAttribute("xy",pt.getX(),pt.getY());
		}
	return graph;
	}
	*/
	public String toString(){
		StringBuilder str = new StringBuilder();
		str.append("Unité temporelle : ");
		str.append(paramTemp);
		str.append("\n");
		str.append("Unité Spatiale : ");
		str.append(paramSpat);	
		str.append("\n");
		str.append("frontières : ");
		str.append("\n");
		for (Point pt : frontieres){
			str.append(pt.toString());
		}

		str.append("\n");
		str.append("positions : ");
		str.append("\n");
		for (Point pt : positions){
			str.append(pt.toString());
		}
	return str.toString();
	}
}
	
		
		
