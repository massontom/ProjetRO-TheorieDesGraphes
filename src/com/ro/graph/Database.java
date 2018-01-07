
import java.sql.*;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.lang.StringBuilder;

public class Database {
  private Connection conn;

  public Database() {
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

  public void logout() throws SQLException {
    this.conn.close();
    System.out.println("Disconnected from BD");
  }

  public void createView(UniteTemporelle uTemp) {
    try {
      Statement statement = conn.createStatement();
      statement.executeUpdate(this.getRequest(uTemp));
    } catch (Exception e) {
      System.out.println(e.toString());
    }
  }

  public String getRequest(UniteTemporelle uTemp) throws SQLException {
    String request = "Create or replace view Points as ";
    switch (uTemp) {
      case MINUTE:
        request += "select * from spatialisation where date in (select date from (select date, row_number() over(partition by extract(year from date), extract(month from date), extract(day from date), extract(hour from date), extract(minute from date) order by date) from spatialisation) as foo where row_number=1);";
        break;

      case HEURE:
        request += "select * from spatialisation where date in (select date from (select date, row_number() over(partition by extract(year from date), extract(month from date), extract(day from date), extract(hour from date) order by date) from spatialisation) as foo where row_number=1);";
        break;

      case JOUR:
        request += "select * from spatialisation where date in (select date from (select date, row_number() over(partition by extract(year from date), extract(day from date), extract(month from date) order by date) from spatialisation) as foo where row_number=1);";
        break;

      case MOIS:
        request += "select * from spatialisation where date in (select date from (select date, row_number() over(partition by extract(year from date), extract(month from date) order by date) from spatialisation) as foo where row_number=1);";
        break;

      default:
        request = "";
    }
    return request;

  }

  public List<Point> getPointsLocalisation() throws SQLException {
    Integer i;
    String point = "";
    List<String> types = new ArrayList<String>();
    List<String> colNames = new ArrayList<String>();
    List<Point> pointsList = new ArrayList<Point>();
    String colName;

    Statement st = conn.createStatement();
    ResultSet result = st.executeQuery("select distinct(ST_AsText(location)) from Points;");
    ResultSetMetaData metaData = result.getMetaData();

    Integer nbCol = metaData.getColumnCount();

    for (i = 1; i <= nbCol; i++) {
      colName = metaData.getColumnLabel(i);
      colNames.add(colName);
      types.add(metaData.getColumnClassName(i));
    }
    result.next();
    while (result.next()) {
      for (i = 1; i <= nbCol; i++) {
        point = result.getObject(colNames.get(i - 1)).toString();
        pointsList.add(new Point(point));
      }
    }
    st.close();
    return pointsList;
  }

  public List<Point> getPointsFrontiere(UniteSpatiale uSpat) throws SQLException {
    List<Point> frontieres = new ArrayList<Point>();
    if (uSpat == UniteSpatiale.DEPARTEMENTS) {
      frontieres = getDepartements();
    } else if (uSpat == UniteSpatiale.COMMUNES) {
      frontieres = getCommunes();
    }
    return frontieres;
  }

  private List<Point> getCommunes() throws SQLException {
    List<Point> frontiereCommunes = new ArrayList<Point>();
    Integer i;
    Statement st = conn.createStatement();
    String point = "";
    String colName;
    ResultSet resultSet;
    ResultSetMetaData metaData;
    List<String> types = new ArrayList<String>();
    List<String> colNames = new ArrayList<String>();
    Integer nbCol;

    resultSet = st.executeQuery("select  ST_AsText((ST_DumpPoints(ST_Multi(ST_Union(spatialrepresentation)))).geom) from communes where \"codeDepartement\" in (select distinct(\"idDepartement\") from Points) group by \"codeDepartement\";");
    metaData = resultSet.getMetaData();
    nbCol = metaData.getColumnCount();
    for (i = 1; i <= nbCol; i++) {
      colName = metaData.getColumnLabel(i);
      colNames.add(colName);
      types.add(metaData.getColumnClassName(i));
    }
    while (resultSet.next()) {
      for (i = 1; i <= nbCol; i++) {
        point = resultSet.getObject(colNames.get(i - 1)).toString();
        frontiereCommunes.add(new Point(point));
      }
    }
    frontiereCommunes.add(new Point());

    resultSet = st.executeQuery("select \"codeInsee\"::int, ST_AsText((ST_DumpPoints(ST_Multi(ST_Union(spatialrepresentation)))).geom) from communes where \"codeInsee\" in (select distinct(\"idCommune\") from Points) group by \"codeInsee\" order by \"codeInsee\";");
    metaData = resultSet.getMetaData();
    nbCol = metaData.getColumnCount();
    for (i = 1; i <= nbCol; i++) {
      colName = metaData.getColumnLabel(i);
      colNames.add(colName);
      types.add(metaData.getColumnClassName(i));
    }
    Integer old = new Integer(0);
    Integer newest = new Integer(0);
    while (resultSet.next()) {
      for (i = 1; i <= nbCol; i++) {
        if (types.get(i - 1).equals("java.lang.Integer")) {
          old = newest;
          newest = resultSet.getInt(colNames.get(i - 1));
          if ((newest - old) != 0) {
            frontiereCommunes.add(new Point());
          }
        } else {
          point = resultSet.getObject(colNames.get(i - 1)).toString();
          frontiereCommunes.add(new Point(point));
        }
      }
    }

    return frontiereCommunes;
  }

  private List<Point> getDepartements() throws SQLException {
    List<Point> frontiereDpt = new ArrayList<Point>();
    Integer i;
    Statement st = conn.createStatement();
    String point = "";
    String colName;
    ResultSet resultSet;
    ResultSetMetaData metaData;
    List<String> types = new ArrayList<String>();
    List<String> colNames = new ArrayList<String>();
    Integer nbCol;

    resultSet = st.executeQuery("select  ST_AsText((ST_DumpPoints(ST_Multi(ST_Union(spatialrepresentation)))).geom) from communes where \"codeDepartement\" in (select distinct(\"idDepartement\") from Points) group by \"codeDepartement\";");
    metaData = resultSet.getMetaData();
    nbCol = metaData.getColumnCount();
    for (i = 1; i <= nbCol; i++) {
      colName = metaData.getColumnLabel(i);
      colNames.add(colName);
      types.add(metaData.getColumnClassName(i));
    }
    while (resultSet.next()) {
      for (i = 1; i <= nbCol; i++) {
        point = resultSet.getObject(colNames.get(i - 1)).toString();
        frontiereDpt.add(new Point(point));
      }
    }
    return frontiereDpt;
  }
}
