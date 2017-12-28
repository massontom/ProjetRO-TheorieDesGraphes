import java.sql.*;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.lang.StringBuilder;

/* classe qui gère la connection à la BD et l'obtention des points qui nous intéressent.
S'inspirer de obtenirTableEssai pour obtenir une liste de points.

*/

public class GestionBD {
    private Connection conn;   
    public GestionBD() throws ClassNotFoundException, SQLException{
	String classedudriver="org.postgresql.Driver";
	Class.forName(classedudriver);
	this.conn = DriverManager.getConnection("jdbc:postgresql://asi-pg.insa-rouen.fr:5432/orange-13","grtt13","grtt13");
	System.out.println("Connected to the db");
    }
    public void deconnexion() throws SQLException{
	this.conn.close();
	System.out.println("Deconnected");
     }


	public List<Point> obtenirTableEssai() throws SQLException{
	Integer i;
	
	List<Point> lpoints = new ArrayList<Point>();
	Statement st = conn.createStatement();
	 String requete = "select ST_AsText((ST_DumpPoints(geom)).geom) from essai;";
	 ResultSet res = st.executeQuery(requete);
	 ResultSetMetaData metaData = res.getMetaData();
	String point = "";	 

	 List<String> types = new ArrayList<String>();
	 List<String> nomsCol = new ArrayList<String>();	 
	 Integer nbCol = metaData.getColumnCount();
	 String nomCol;
	 
	 for (i=1;i<=nbCol;i++){
	     nomCol = metaData.getColumnLabel(i);
	     nomsCol.add(nomCol);
	     types.add(metaData.getColumnClassName(i));
	 }
	 while (res.next()){
	      for (i=1;i<=nbCol;i++){
		point = res.getObject(nomsCol.get(i-1)).toString();
		lpoints.add(new Point(point));
		}
	 }
	  
	 st.close();
	return lpoints;
}
	public void afficheTableEssai() throws SQLException{
Integer i;
	 StringBuilder str = new StringBuilder();
	 Statement st = conn.createStatement();
	 String requete = "select ST_AsText((ST_DumpPoints(geom)).geom) from essai;";
	 ResultSet res = st.executeQuery(requete);
	 ResultSetMetaData metaData = res.getMetaData();	 

	 List<String> types = new ArrayList<String>();
	 List<String> nomsCol = new ArrayList<String>();	 
	 Integer nbCol = metaData.getColumnCount();
	 String nomCol;
	 
	 for (i=1;i<=nbCol;i++){
	     nomCol = metaData.getColumnLabel(i);
	str.append(" | ");
	     str.append(nomCol);
	     nomsCol.add(nomCol);
	     
	     types.add(metaData.getColumnClassName(i));
	 }
	 str.append("\n");

	 
	 
	 while (res.next()){
	      for (i=1;i<=nbCol;i++){
		  if (types.get(i-1).equals("java.lang.String")){
			  str.append(res.getString(nomsCol.get(i-1)));
		      }
		  else if (types.get(i-1).equals("java.lang.Integer")){
		      str.append(res.getInt(nomsCol.get(i-1)));
		   	}
	      else {
		str.append(res.getObject(nomsCol.get(i-1)));
		
		}
      	     str.append(" | ");
     	      }
 	 str.append("\n");
	 }
	 
	 System.out.println(str.toString());	 
	 st.close();
     }

public void afficheTable(String nomTable) throws SQLException{
	if (nomTable.equals("essai")){
		afficheTableEssai();
}
else
{
	 Integer i;
	 StringBuilder str = new StringBuilder();
	 Statement st = conn.createStatement();
	 String requete = "SELECT * FROM ";
	 requete = requete+nomTable;
	 ResultSet res = st.executeQuery(requete);
	 ResultSetMetaData metaData = res.getMetaData();	 

	 List<String> types = new ArrayList<String>();
	 List<String> nomsCol = new ArrayList<String>();	 
	 Integer nbCol = metaData.getColumnCount();
	 String nomCol;
	 
	 for (i=1;i<=nbCol;i++){
	     nomCol = metaData.getColumnLabel(i);
	     str.append(nomCol);
	     nomsCol.add(nomCol);
	     str.append(" | ");
	     types.add(metaData.getColumnClassName(i));
	 }
	 str.append("\n");

	 
	 
	 while (res.next()){
	      for (i=1;i<=nbCol;i++){
		  if (types.get(i-1).equals("java.lang.String")){
			  str.append(res.getString(nomsCol.get(i-1)));
		      }
		  else if (types.get(i-1).equals("java.lang.Integer")){
		      str.append(res.getInt(nomsCol.get(i-1)));
		   	}
	      else {
		str.append(res.getObject(nomsCol.get(i-1)));
		
		}
      	     str.append(" | ");
     	      }
 	 str.append("\n");
	 }
	 
	 System.out.println(str.toString());	 
	 st.close();
}
     }






}

