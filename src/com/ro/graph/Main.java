package com.ro.graph;
import java.sql.*;
import java.util.Scanner;
import org.graphstream.graph.*;
import org.graphstream.graph.implementations.*;

public class Main {
  public static void main(String[] args)  throws Exception {
  UniteTemporelle uTemp = UniteTemporelle.MOIS;
  UniteSpatiale uSpat = UniteSpatiale.COMMUNES;
   GestionBD bd = new GestionBD();
   CustomGraph customGraph = new CustomGraph(bd, uTemp, uSpat);
   bd.deconnexion();
   customGraph.afficher();
  }
}
