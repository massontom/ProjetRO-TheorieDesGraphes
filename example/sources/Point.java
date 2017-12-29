import java.util.ArrayList;
import java.util.List;

/* Classe représentant un point en 2D. Sert pour transiter de la bd à la structure graphe
*/

public class Point{
	private Float X;
	private Float Y;
	
	public Point(){
	X =new Float(0);
	Y =new Float(0);
	}
	public Point(float x, float y){
	X =new Float(x);
	Y =new Float(y);
	}

	/* Constructeur utilisé pour transformer une ligne de la bd de forme "POINT(x y)" en objet de classe point. 
	*/
	public Point(String point){
	String[] coord;
	String regex = new String("[\\(\\)\\s]");
	coord = point.split(regex);
	X = new Float(coord[1]);
	Y = new Float(coord[2]);	
	}

	public float getX(){
	return X.floatValue();
	}

	public float getY(){
	return Y.floatValue();
	}

	public void setX(float x){
	X = new Float(x);
	}

	public void setY(float y){
	Y = new Float(y);
	}

	public String toString(){
	return X.toString() + " " + Y.toString();
}
}	
	
	 
