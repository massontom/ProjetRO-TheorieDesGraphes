import java.sql.*;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

/* Main pour faire des tests de connection à la bd + récupérer des points. Lancer avec testBD.sh
*/

public class TestGestionBD{
    public static void main(String[] args)  throws Exception {
	GestionBD bd = new GestionBD();
	List<Point> lpoints = new ArrayList<Point>();
	lpoints = bd.obtenirTableEssai();
	bd.deconnexion();
	System.out.println(lpoints.toString());	
    } 
}
