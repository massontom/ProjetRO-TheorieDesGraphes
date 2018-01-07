
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
	private Database bd;
	private List<Point> frontieres;
	private List<Point> positions;

	public CustomGraph(Database bd, UniteTemporelle uTemp, UniteSpatiale uSpat) throws SQLException {
		this.bd = bd;
		this.uTemp = uTemp;
		this.uSpat = uSpat;
		frontieres = new ArrayList<Point>();
		positions = new ArrayList<Point>();
		this.bd.createView(this.uTemp);
		this.frontieres = bd.getPointsFrontiere(uSpat);
		this.positions = bd.getPointsLocalisation();
	}

	public void afficher() {
		Graph graph = new MultiGraph("spatialisation grtt14");
		graph.addAttribute("ui.stylesheet",
				"edge {fill-color: blue; fill-mode: dyn-plain;}" + "node{fill-color: blue, red; fill-mode: dyn-plain;}");
		Integer i = 0;
		String noeud;
		Integer changementFrontiere = 0;
		for (Point pt : frontieres) {
			if (!(pt.getX() == 0 && pt.getY() == 0)) {
				changementFrontiere++;
				i = i + 1;
				noeud = i.toString() + "frontiere";
				graph.addNode(noeud);
				graph.getNode(noeud).setAttribute("xy", pt.getX(), pt.getY());
				graph.getNode(noeud).addAttribute("ui.color", 0);
				graph.getNode(noeud).addAttribute("ui.style", "size : 0.5px;");
				if (changementFrontiere > 1) {
					Integer j = i - 1;
					String noeudPrecedent = j.toString() + "frontiere";
					String nomSegment = noeudPrecedent + " à " + noeud;
					graph.addEdge(nomSegment, noeudPrecedent, noeud);
				}
			} else {
				changementFrontiere = 0;
			}
		}
		for (Point pt : positions) {
			i = i + 1;
			noeud = i.toString() + "position";
			graph.addNode(noeud);
			graph.getNode(noeud).setAttribute("xy", pt.getX(), pt.getY());
			graph.getNode(noeud).addAttribute("ui.color", 1);
			graph.getNode(noeud).addAttribute("ui.style", "size : 4px;");
		}
		graph.display(false);
	}

}
