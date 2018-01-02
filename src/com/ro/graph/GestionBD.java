package com.ro.graph;

import java.sql.*;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.lang.StringBuilder;

public class GestionBD {
  private Connection conn;

  public GestionBD(){
    try {
      System.out.println("Trying to connect to the DB...");
      Class.forName("org.postgresql.Driver");
      conn = DriverManager.getConnection("jdbc:postgresql://asi-pg.insa-rouen.fr/orange-14","grtt14","grtt14");
      System.out.println("Connected to the DB");
    }catch(Exception e){
      System.out.println("Connection FAILED");
      System.out.println(e.toString());
    }
}


public void deconnexion() throws SQLException {
  this.conn.close();
  System.out.println("Disconnected from the BD");
}

public void creerVue(UniteTemporelle uTemp, UniteSpatiale uSpat){ //a completer pour uspat
    try{
      Statement statement = conn.createStatement();
      String code = this.genererRequete(uTemp);
      statement.executeUpdate(code);
    }catch(Exception e){
      System.out.println(e.toString());
    }
  }

	public String genererRequete(UniteTemporelle uTemp) throws SQLException{
    String requete;
    switch (uTemp){
      case UniteTemporelle.MOIS:
        requete = "select * from spatialisation where date in (select date from (select date, row_number() over(partition by extract(year from date), extract(month from date) order by date) from spatialisation) as foo where row_number=1);";
        break;

      case UniteTemporelle.JOUR:
        requete = "select * from spatialisation where date in (select date from (select date, row_number() over(partition by extract(year from date), extract(day from date), extract(month from date) order by date) from spatialisation) as foo where row_number=1);";
      break;

      case UniteTemporelle.HEURE:
        requete = "select * from spatialisation where date in (select date from (select date, row_number() over(partition by extract(year from date), extract(month from date), extract(day from date), extract(hour from date) order by date) from spatialisation) as foo where row_number=1);";
      break;

      case UniteTemporelle.MINUTE:
        requete = "select * from spatialisation where date in (select date from (select date, row_number() over(partition by extract(year from date), extract(month from date), extract(day from date), extract(hour from date), extract(minute from date) order by date) from spatialisation) as foo where row_number=1);";
      break;

      default:
      requete = "";
    }
    return requete;

  }

    public List<Point> obtenirPointsLocalisation() throws SQLException{
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

      public List<Point> obtenirPointsFrontiere() throws SQLException{
        Integer i;

        List<Point> lpoints = new ArrayList<Point>();

        Statement st = conn.createStatement();
        String point = "";
         String nomCol;
         // DEPARTEMENTS
         ResultSet res = st.executeQuery("select  ST_AsText((ST_DumpPoints(ST_Multi(ST_union (spatialrepresentation)))).geom) from communes where \"codeDepartement\" in (select distinct(\"idDepartement\") from Points) group by \"codeDepartement\";");
         ResultSetMetaData metaData = res.getMetaData();

          List<String> types = new ArrayList<String>();
         List<String> nomsCol = new ArrayList<String>();
         Integer nbCol = metaData.getColumnCount();


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

         /**
         //COMMUNES
        ResultSet res = st.executeQuery("select ST_AsText((ST_DumpPoints(ST_Multi(ST_union (spatialrepresentation)))).geom) from communes where \"codeInsee\" in (select distinct(\"idCommune\") from Points) group by \"codeInsee\";");
         ResultSetMetaData metaData = res.getMetaData();

          List<String> types = new ArrayList<String>();
         List<String> nomsCol = new ArrayList<String>();
         Integer nbCol = metaData.getColumnCount();


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
        }**/

         st.close();
        return lpoints;
        }

        public void afficheTable(String nomTable) throws SQLException{
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
