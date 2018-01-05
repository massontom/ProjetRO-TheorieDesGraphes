import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.lang.StringBuilder;
import unite.UniteTemp;

/* classe qui gère la connection à la BD et l'obtention des points qui nous intéressent.
   S'inspirer de obtenirTableEssai pour obtenir une liste de points.

 */

public class GestionBD {
private Connection conn;


public GestionBD() throws ClassNotFoundException, SQLException {
        String classedudriver="org.postgresql.Driver";
        Class.forName(classedudriver);
        this.conn = DriverManager.getConnection("jdbc:postgresql://asi-pg.insa-rouen.fr:5432/orange-13","grtt13","grtt13");
        System.out.println("Connected to the db");
}


public void deconnexion() throws SQLException {
        this.conn.close();
        System.out.println("Deconnected");
}


public void creerVue(UniteTemp uTemp) throws SQLException {
        Statement statement = conn.createStatement();
        String code = this.genererRequete(uTemp);
        statement.executeUpdate(code);
}

public String genererRequete(UniteTemp uTemp) throws SQLException {
        String requete = "Create or replace view Points as ";
        switch (uTemp) {
        case MOIS:
                requete = requete + "select * from spatialisation where date in (select date from (select date, row_number() over(partition by extract(year from date), extract(month from date) order by date) from spatialisation) as foo where row_number=1);";
                break;
	case JOUR:
                requete = requete + "select * from spatialisation where date in (select date from (select date, row_number() over(partition by extract(year from date), extract(day from date), extract(month from date) order by date) from spatialisation) as foo where row_number=1);";
                break;
case HEURE:
                requete = requete + "select * from spatialisation where date in (select date from (select date, row_number() over(partition by extract(year from date), extract(month from date), extract(day from date), extract(hour from date) order by date) from spatialisation) as foo where row_number=1);";
                break;
case MINUTE:
                requete = requete + "select * from spatialisation where date in (select date from (select date, row_number() over(partition by extract(year from date), extract(month from date), extract(day from date), extract(hour from date), extract(minute from date) order by date) from spatialisation) as foo where row_number=1);";
                break;
        default:
                requete = "";
        }
        return requete;

}

public List<Point> obtenirPointsLocalisation() throws SQLException {
        Integer i;

        List<Point> lpoints = new ArrayList<Point>();
        Statement st = conn.createStatement();

        ResultSet res = st.executeQuery("select distinct(ST_AsText(location)) from Points;");
        ResultSetMetaData metaData = res.getMetaData();
        String point = "";

        List<String> types = new ArrayList<String>();
        List<String> nomsCol = new ArrayList<String>();
        Integer nbCol = metaData.getColumnCount();
        String nomCol;

        for (i=1; i<=nbCol; i++) {
                nomCol = metaData.getColumnLabel(i);
                nomsCol.add(nomCol);
                types.add(metaData.getColumnClassName(i));
        }
	res.next();
        while (res.next()) {
                for (i=1; i<=nbCol; i++) {
			
                        point = res.getObject(nomsCol.get(i-1)).toString();
                        lpoints.add(new Point(point));
                }
        }

        st.close();
        return lpoints;
}


public List<Point> obtenirPointsFrontiere() throws SQLException {
        Integer i;

        List<Point> lpoints = new ArrayList<Point>();

        Statement st = conn.createStatement();
        String point = "";
        String nomCol;

        ResultSet res = st.executeQuery("select  ST_AsText((ST_DumpPoints(ST_Multi(ST_Union(spatialrepresentation)))).geom) from communes where \"codeDepartement\" in (select distinct(\"idDepartement\") from Points) group by \"codeDepartement\";");
        ResultSetMetaData metaData = res.getMetaData();

        List<String> types = new ArrayList<String>();
        List<String> nomsCol = new ArrayList<String>();
        Integer nbCol = metaData.getColumnCount();


        for (i=1; i<=nbCol; i++) {
                nomCol = metaData.getColumnLabel(i);
                nomsCol.add(nomCol);
                types.add(metaData.getColumnClassName(i));
        }
        while (res.next()) {
                for (i=1; i<=nbCol; i++) {
                        point = res.getObject(nomsCol.get(i-1)).toString();
                        lpoints.add(new Point(point));
                }
        }
	lpoints.add(new Point()); //ajout du séparateur entre les frontières
        res = st.executeQuery("select \"codeInsee\"::int, ST_AsText((ST_DumpPoints(ST_Multi(ST_Union(spatialrepresentation)))).geom) from communes where \"codeInsee\" in (select distinct(\"idCommune\") from Points) group by \"codeInsee\" order by \"codeInsee\";");
        metaData = res.getMetaData();

        types = new ArrayList<String>();
        nomsCol = new ArrayList<String>();
        nbCol = metaData.getColumnCount();


        for (i=1; i<=nbCol; i++) {
                nomCol = metaData.getColumnLabel(i);
                nomsCol.add(nomCol);
                types.add(metaData.getColumnClassName(i));
        }
	
	Integer old=new Integer(0);
	Integer nouveau=new Integer(0);

        while (res.next()) {
                for (i=1; i<=nbCol; i++) {
			if (types.get(i-1).equals("java.lang.Integer")){
				old = nouveau;
      				nouveau = res.getInt(nomsCol.get(i-1));
				if ((nouveau-old)!=0){
					lpoints.add(new Point());
					}
        			}
    			else {
        			point = res.getObject(nomsCol.get(i-1)).toString();
                        	lpoints.add(new Point(point));
                	}
        	}
	}

        st.close();
        return lpoints;
}







}
