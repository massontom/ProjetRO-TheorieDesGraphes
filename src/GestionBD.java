import java.sql.*;
import java.util.Scanner;
import org.graphstream.graph.*;
import org.graphstream.graph.implementations.*;

public class GestionBD {
  private Connection connect;

  public GestionBD()throws ClassNotFoundException, SQLException{
   this.connect=DriverManager.getConnection("jdbc:postgresql:asi-pg.insa-rouen.fr:5432/orange-14","grtt14","grtt14"); // ligne à changer je connais pas l'URL
}


public void deconnexion() throws SQLException {
  this.connect.close();
}


}
