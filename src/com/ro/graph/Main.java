
import java.sql.*;
import java.util.Scanner;
import org.graphstream.graph.*;
import org.graphstream.graph.implementations.*;

public class Main {
  public static void main(String[] args) throws Exception {
    UniteTemporelle uTemp = UniteTemporelle.MOIS;
    UniteSpatiale uSpat = UniteSpatiale.COMMUNES;
    GestionBD bd = new GestionBD();
    Scanner sc = new Scanner(System.in); //test menus
    boolean arret = false;
    CustomGraph customGraph = new CustomGraph(bd, uTemp, uSpat);

  bd.deconnexion();


  while (!arret){
	System.out.println("Par communes : tapez 1");
	System.out.println("Par départements : tapez 2");
  System.out.println("quitter : tapez 9");
  int choix= sc.nextInt();

	switch(choix)
	{
		case 1 :{
					int secondChoice = 0;
					System.out.println("Par mois : tapez 1");
					System.out.println("Par jour : tapez 2");
					System.out.println("Par heure : tapez 3");
          System.out.println("Par minute : tapez 4");
          System.out.println("quitter : tapez 9");
					secondChoice = sc.nextInt();
					switch(secondChoice){
						case 1 : {bd.genererRequete(UniteTemporelle.MOIS);
                      System.out.println(bd.genererRequete(UniteTemporelle.MOIS));
                      System.out.println("Par communes et mois");
                      } break; //ajoutez les requetes correspondantes
						case 2 :{bd.genererRequete(UniteTemporelle.JOUR);
                      System.out.println(bd.genererRequete(UniteTemporelle.JOUR));
                      System.out.println("Par communes et jour");
                    } break;
            case 3 : {bd.genererRequete(UniteTemporelle.HEURE);
                      System.out.println(bd.genererRequete(UniteTemporelle.HEURE));
                      System.out.println("Par communes et heure");
                      } break;
            case 4 : {bd.genererRequete(UniteTemporelle.MINUTE);
                      System.out.println(bd.genererRequete(UniteTemporelle.MINUTE));
                      System.out.println("Par communes et minute");
                      } break;
            case 9 : arret = true; break;
            default : System.out.println("entrez un choix entre 1 et 4"); break;
					}
				} break;
		case 2 :{
        System.out.println("Par mois : tapez 1");
        System.out.println("Par jour : tapez 2");
        System.out.println("Par heure : tapez 3");
        System.out.println("Par minute : tapez 4");
        System.out.println("quitter : tapez 9");
        int thirdChoice = sc.nextInt();
        switch(thirdChoice){
            case 1 : System.out.println("Par département et mois"); break; //requetes à ajouter ici aussi
            case 2 : System.out.println("Par département et jour"); break;
            case 3 : System.out.println("Par departement et heure"); break;
            case 4 : System.out.println("Par département et minute"); break;
            case 9 : arret = true; break;
            default : System.out.println("entrez un choix entre 1 et 4"); break;
          }
        } break;
    case 9 : arret = true; break;
		default : System.out.println("entrez un choix entre 1 et 3"); break;
	}
                }
  customGraph.afficher();
  }
}
