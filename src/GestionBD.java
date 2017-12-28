import java.sql.*;
import java.util.Scanner;
import org.graphstream.graph.*;
import org.graphstream.graph.implementations.*;

public class GestionBD {
  private Connection conn;

  public GestionBD()throws ClassNotFoundException, SQLException{
   Class.forName("org.postgresql.Driver");
   conn = DriverManager.getConnection("jdbc:postgresql://asi-pg.insa-rouen.fr/orange-14","grtt14","grtt14"); // ligne à changer je connais pas l'URL
}


public void deconnexion() throws SQLException {
  this.conn.close();
}


}
