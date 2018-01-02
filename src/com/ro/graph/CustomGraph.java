package com.ro.graph;
import java.util.ArrayList;
import java.util.List;
import java.lang.StringBuilder;
import org.graphstream.graph.*;
import org.graphstream.graph.implementations.*;
/* Classe permettant de manipuler ce que l'on veux afficher
paramTemp et paramSpat seront renseignés par l'utilisateur,
les points frontières représentent les frontières de l'unité spatiale choisie, et les points positions représentent les positions du détenteur du téléphone, flitrées selon l'unité temporelle choisie.

*/

public class CustomGraph{
	private UniteTemporelle uTemp;
	private UniteSpatiale uSpat;
	private GestionBD bd;
	private List<Point> frontieres;
	private List<Point> positions;

	public void CustomGraph(GestionBD bd, UniteTemporelle uTemp, UniteSpatiale uSpat){
		this.bd = bd;
		this.uTemp = uTemp;
		this.uSpat = uSpat;
		frontieres = new ArrayList<Point>();
		positions = new ArrayList<Point>();
		this.bd.creerVue(this.uTemp, this.uSpat);
		frontieres = this.bd.obtenirPointsFrontiere();
		positions = this.bd.obtenirPointsLocalisation();
	}

	/* A coder : création de 1 graphes à partir des 2 listes de points
	S'inspirer de grapheFrontiere() mais le généraliser pour que les 2 listes de points soient traitées. Gerer aussi le style du graphe dans cette partie (ajouter des arretes, gerer le css du graphe pour différencier à l'affichage les points frontières des points d'information).
	*/
	public void afficher(){

	}



	// Ne plus utiliser les méthodes setFrontieres et grapheFrontiere: c'était des tests pour afficher la ligne de commande de MM. On peut s'inspirer de grapheFrontiere pour la méthode afficher().
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
		str.append(uTemp);
		str.append("\n");
		str.append("Unité Spatiale : ");
		str.append(uSpat);
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
