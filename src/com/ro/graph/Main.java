
import java.sql.*;
import java.util.Scanner;
import org.graphstream.graph.*;
import org.graphstream.graph.implementations.*;

public class Main {
  public static void main(String[] args) throws Exception {
    UniteTemporelle uTemp = UniteTemporelle.MOIS;
    UniteSpatiale uSpat = UniteSpatiale.DEPARTEMENTS;
    GestionBD bd = new GestionBD();
    Scanner sc = new Scanner(System.in); //test menus
    int choix;
    boolean loop = true;

    do {
      System.out.println("---Choix de l'unité spatiale ---");
      System.out.println("Par communes : tapez 1");
      System.out.println("Par départements : tapez 2");
      System.out.println("quitter : tapez 9");
      choix = sc.nextInt();
      switch (choix) {
      case 1:
        uSpat = UniteSpatiale.COMMUNES;
        loop = false;
      case 2:
        uSpat = UniteSpatiale.DEPARTEMENTS;
        loop = false;
      case 9:
        loop = false;
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
      System.out.println("quitter : tapez 9");
      choix = sc.nextInt();
      switch (choix) {
      case 1:
        uTemp = UniteTemporelle.MOIS;
        loop = false;
      case 2:
        uTemp = UniteTemporelle.JOUR;
        loop = false;
      case 3:
        uTemp = UniteTemporelle.HEURE;
        loop = false;
      case 4:
        uTemp = UniteTemporelle.MINUTE;
        loop = false;
      case 9:
        loop = false;
      default:
        System.out.println("Veuillez faire un choix");
      }
    } while (loop);
    sc.close();
    System.out.println(String.format("Vos choix sont : %s, %s", uTemp, uSpat));

    CustomGraph customGraph = new CustomGraph(bd, uTemp, uSpat);
    customGraph.afficher();
    bd.deconnexion();
  }
}
