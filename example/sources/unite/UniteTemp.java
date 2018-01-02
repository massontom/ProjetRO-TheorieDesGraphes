 package unite;
public enum UniteTemp{
			MOIS ("mois"),
		      JOUR ("jour"),
		      HEURE ("heure"),
		      MINUTE ("minute");

		      private String nom;

		      UniteTemp(String nom){
			  this.nom=nom;
		      }

		      public String toString(){
			  return nom;
		      }
}
