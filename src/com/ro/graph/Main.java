
import java.sql.*;
import java.util.Scanner;
import org.graphstream.graph.*;
import org.graphstream.graph.implementations.*;

public class Main {
  public static void main(String[] args) throws Exception {
    UniteTemporelle uTemp = UniteTemporelle.MOIS;
    UniteSpatiale uSpat = UniteSpatiale.DEPARTEMENTS;
    Database bd = new Database();
    Scanner sc = new Scanner(System.in);
    int choix;
    boolean loop = true;

    do {
      System.out.println("---Choix de la plus petite unité spatiale ---");
      System.out.println("Par communes : tapez 1");
      System.out.println("Par départements : tapez 2");
      choix = sc.nextInt();
      switch (choix) {
      case 1:
        uSpat = UniteSpatiale.COMMUNES;
        System.out.println("com");
        loop = false;
        break;
      case 2:
        uSpat = UniteSpatiale.DEPARTEMENTS;
        System.out.println("dpt");
        loop = false;
        break;
      default:
        System.out.println("Veuillez faire un choix");
      }
    } while (loop);

    loop = true;

    do {
      System.out.println("---Choix de l'unité temporelle---");
      System.out.println("Par mois : tapez 1");
      System.out.println("Par jour : tapez 2");
      System.out.println("Par heure : tapez 3");
      System.out.println("Par minute : tapez 4");
      choix = sc.nextInt();
      switch (choix) {
      case 1:
        uTemp = UniteTemporelle.MOIS;
        System.out.println("mois");
        loop = false;
        break;
      case 2:
        uTemp = UniteTemporelle.JOUR;
        System.out.println("jour");
        loop = false;
        break;
      case 3:
        uTemp = UniteTemporelle.HEURE;
        System.out.println("heure");
        loop = false;
        break;
      case 4:
        uTemp = UniteTemporelle.MINUTE;
        System.out.println("minute");
        loop = false;
        break;
      default:
        System.out.println("Veuillez faire un choix");
      }
    } while (loop);

    sc.close();
    System.out.println(String.format("Vos choix sont : %s, %s", uTemp, uSpat));

    CustomGraph customGraph = new CustomGraph(bd, uTemp, uSpat);
    customGraph.afficher();
    bd.logout();
  }
}
