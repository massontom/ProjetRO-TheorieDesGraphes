
import java.sql.*;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.lang.StringBuilder;

public class GestionBD {
  private Connection conn;

  public GestionBD() {
    try {
      System.out.println("Trying to connect to the DB...");
      Class.forName("org.postgresql.Driver");
      this.conn = DriverManager.getConnection("jdbc:postgresql://asi-pg.insa-rouen.fr/orange-14", "grtt14", "grtt14");
      System.out.println("Connected to the DB");
    } catch (Exception e) {
      System.out.println("Connection FAILED");
      System.out.println(e.toString());
    }
  }

  public void deconnexion() throws SQLException {
    this.conn.close();
    System.out.println("Disconnected from BD");
  }

  public void creerVue(UniteTemporelle uTemp, UniteSpatiale uSpat) { //a completer pour uspat
    try {
      Statement statement = conn.createStatement();
      String code = this.genererRequete(uTemp);
      statement.executeUpdate(code);
    } catch (Exception e) {
      System.out.println(e.toString());
    }
  }

  public String genererRequete(UniteTemporelle uTemp) throws SQLException {
    String requete = "Create or replace view Points as ";
    switch (uTemp) {
    case MOIS:
      requete += "select * from spatialisation where date in (select date from (select date, row_number() over(partition by extract(year from date), extract(month from date) order by date) from spatialisation) as foo where row_number=1);";
      break;

    case JOUR:
      requete += "select * from spatialisation where date in (select date from (select date, row_number() over(partition by extract(year from date), extract(day from date), extract(month from date) order by date) from spatialisation) as foo where row_number=1);";
      break;

    case HEURE:
      requete += "select * from spatialisation where date in (select date from (select date, row_number() over(partition by extract(year from date), extract(month from date), extract(day from date), extract(hour from date) order by date) from spatialisation) as foo where row_number=1);";
      break;

    case MINUTE:
      requete += "select * from spatialisation where date in (select date from (select date, row_number() over(partition by extract(year from date), extract(month from date), extract(day from date), extract(hour from date), extract(minute from date) order by date) from spatialisation) as foo where row_number=1);";
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

    for (i = 1; i <= nbCol; i++) {
      nomCol = metaData.getColumnLabel(i);
      nomsCol.add(nomCol);
      types.add(metaData.getColumnClassName(i));
    }
    res.next();
    while (res.next()) {
      for (i = 1; i <= nbCol; i++) {

        point = res.getObject(nomsCol.get(i - 1)).toString();
        lpoints.add(new Point(point));
      }
    }

    st.close();
    return lpoints;
  }

  public List<Point> obtenirPointsFrontiere(UniteSpatiale uSpat) throws SQLException {
    List<Point> frontieres = new ArrayList<Point>();
    if (uSpat == UniteSpatiale.DEPARTEMENTS) {
      frontieres = requeteDepartement();
    } else if (uSpat == UniteSpatiale.COMMUNES) {
      frontieres = requeteCommune();
    }
    return frontieres;
  }

  private List<Point> requeteDepartement() throws SQLException {

    List<Point> frontiereDpt = new ArrayList<Point>();
    Integer i;
    Statement st = conn.createStatement();
    String point = "";
    String nomCol;
    ResultSet res;
    ResultSetMetaData metaData;
    List<String> types = new ArrayList<String>();
    List<String> nomsCol = new ArrayList<String>();
    Integer nbCol;

    res = st.executeQuery(
        "select  ST_AsText((ST_DumpPoints(ST_Multi(ST_Union(spatialrepresentation)))).geom) from communes where \"codeDepartement\" in (select distinct(\"idDepartement\") from Points) group by \"codeDepartement\";");
    metaData = res.getMetaData();
    nbCol = metaData.getColumnCount();
    for (i = 1; i <= nbCol; i++) {
      nomCol = metaData.getColumnLabel(i);
      nomsCol.add(nomCol);
      types.add(metaData.getColumnClassName(i));
    }
    while (res.next()) {
      for (i = 1; i <= nbCol; i++) {
        point = res.getObject(nomsCol.get(i - 1)).toString();
        frontiereDpt.add(new Point(point));
      }
    }

    return frontiereDpt;
  }

  private List<Point> requeteCommune() throws SQLException {
    List<Point> frontiereCommunes = new ArrayList<Point>();
    Integer i;
    Statement st = conn.createStatement();
    String point = "";
    String nomCol;
    ResultSet res;
    ResultSetMetaData metaData;
    List<String> types = new ArrayList<String>();
    List<String> nomsCol = new ArrayList<String>();
    Integer nbCol;

    res = st.executeQuery(
        "select  ST_AsText((ST_DumpPoints(ST_Multi(ST_Union(spatialrepresentation)))).geom) from communes where \"codeDepartement\" in (select distinct(\"idDepartement\") from Points) group by \"codeDepartement\";");
    metaData = res.getMetaData();
    nbCol = metaData.getColumnCount();
    for (i = 1; i <= nbCol; i++) {
      nomCol = metaData.getColumnLabel(i);
      nomsCol.add(nomCol);
      types.add(metaData.getColumnClassName(i));
    }
    while (res.next()) {
      for (i = 1; i <= nbCol; i++) {
        point = res.getObject(nomsCol.get(i - 1)).toString();
        frontiereCommunes.add(new Point(point));
      }
    }
    frontiereCommunes.add(new Point());

    res = st.executeQuery(
        "select \"codeInsee\"::int, ST_AsText((ST_DumpPoints(ST_Multi(ST_Union(spatialrepresentation)))).geom) from communes where \"codeInsee\" in (select distinct(\"idCommune\") from Points) group by \"codeInsee\" order by \"codeInsee\";");
    metaData = res.getMetaData();
    nbCol = metaData.getColumnCount();
    for (i = 1; i <= nbCol; i++) {
      nomCol = metaData.getColumnLabel(i);
      nomsCol.add(nomCol);
      types.add(metaData.getColumnClassName(i));
    }
    Integer old = new Integer(0);
    Integer nouveau = new Integer(0);
    while (res.next()) {
      for (i = 1; i <= nbCol; i++) {
        if (types.get(i - 1).equals("java.lang.Integer")) {
          old = nouveau;
          nouveau = res.getInt(nomsCol.get(i - 1));
          if ((nouveau - old) != 0) {
            frontiereCommunes.add(new Point());
          }
        } else {
          point = res.getObject(nomsCol.get(i - 1)).toString();
          frontiereCommunes.add(new Point(point));
        }
      }
    }

    return frontiereCommunes;
  }
}
