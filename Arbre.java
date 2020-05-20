/**
 * modelise un arbre avec la méthode UPGMA
 * @author Hornella Fosso-Kahina Lounaci
 *
 */

public class Arbre {
	private String idsequence;
	private Arbre Droite;
	private Arbre Gauche;
	//double distanceG;
	//double distanceD;
	
	public Arbre(String id,Arbre g,Arbre d){
		this.idsequence=id;
		this.Droite=d;
		this.Gauche=g;
		
	}
	
	public String  getid(){
		return(idsequence);

	}
	
	public Arbre getArbreG(){
		return(Gauche);
		
	}
	public Arbre getArbreD(){
		return(Droite);
		
	}
	
	
	
	public Arbre fusion(Arbre a, Arbre b){
		Arbre g = null;
		Arbre d = null;
		String id= null;
		Arbre c = new Arbre(id,g,d);
		c.Gauche=a;
		c.Droite=b;
		
		return c;
	}
	
	public double (Arbre A){ // calcul la distance  d'un noeud a ces feuilles
		
	}
}
