
import java.util.ArrayList;
import java.util.List;
import java.lang.StringBuilder;
import java.lang.Exception;
import java.sql.SQLException;
import org.graphstream.graph.*;
import org.graphstream.graph.implementations.*;

public class CustomGraph {
	private UniteTemporelle uTemp;
	private UniteSpatiale uSpat;
	private GestionBD bd;
	private List<Point> frontieres;
	private List<Point> positions;

	public CustomGraph(GestionBD bd, UniteTemporelle uTemp, UniteSpatiale uSpat) throws SQLException {
		this.bd = bd;
		this.uTemp = uTemp;
		this.uSpat = uSpat;
		frontieres = new ArrayList<Point>();
		positions = new ArrayList<Point>();
		this.bd.creerVue(this.uTemp, this.uSpat);
		this.frontieres = bd.obtenirPointsFrontiere(uSpat);
		this.positions = bd.obtenirPointsLocalisation();
	}

	public void afficher() {
		Graph graph = new MultiGraph("Graphes des frontieres/positions");
		graph.addAttribute("ui.stylesheet",
				"edge {fill-color: blue; fill-mode: dyn-plain;}" + "node{fill-color: blue, red; fill-mode: dyn-plain;}");
		Integer i = 0;
		String nomNoeud;
		Integer indicateurChangementFrontiere = 0;
		for (Point pt : frontieres) {
			if (!(pt.getX() == 0 && pt.getY() == 0)) {
				indicateurChangementFrontiere++;
				i = i + 1;
				nomNoeud = i.toString() + "frontiere";
				graph.addNode(nomNoeud);
				graph.getNode(nomNoeud).setAttribute("xy", pt.getX(), pt.getY());
				graph.getNode(nomNoeud).addAttribute("ui.color", 0);
				graph.getNode(nomNoeud).addAttribute("ui.style", "size : 0.5px;");
				if (indicateurChangementFrontiere > 1) {
					Integer j = i - 1;
					String nomNoeudPrecedent = j.toString() + "frontiere";
					String nomSegment = nomNoeudPrecedent + " à " + nomNoeud;
					graph.addEdge(nomSegment, nomNoeudPrecedent, nomNoeud);
				}
			} else {
				indicateurChangementFrontiere = 0;
			}
		}
		for (Point pt : positions) {
			i = i + 1;
			nomNoeud = i.toString() + "position";
			graph.addNode(nomNoeud);
			graph.getNode(nomNoeud).setAttribute("xy", pt.getX(), pt.getY());
			graph.getNode(nomNoeud).addAttribute("ui.color", 1);
			graph.getNode(nomNoeud).addAttribute("ui.style", "size : 4px;");
		}
		graph.display(false);
	}

	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append("Unité temporelle : ");
		str.append(uTemp);
		str.append("\n");
		str.append("Unité Spatiale : ");
		str.append(uSpat);
		str.append("\n");
		str.append("frontières : ");
		str.append("\n");
		for (Point pt : frontieres) {
			str.append(pt.toString());
		}

		str.append("\n");
		str.append("positions : ");
		str.append("\n");
		for (Point pt : positions) {
			str.append(pt.toString());
		}
		return str.toString();
	}
}
