/**
 * modelise un arbre avec la méthode UPGMA
 * @author Hornella Fosso-Kahina Lounaci
 *
 */

public class Arbre {
	private String cle;
	Sequence seqCle;
	Alignement alignCle;
	private Arbre droite;
	private Arbre gauche;
	private double hautG;
	private double hautD;
	private int nbreFeuilles;
	private Arbre pere;
	
	/**
	 * constructeur Arbre vide
	 */
	public Arbre(){
		this.cle = "";
		this.seqCle = null;
		this.alignCle = null;
		this.droite = null;
		this.gauche = null;
		this.hautD = 0;
		this.hautG = 0;
		this.nbreFeuilles = 1;
		this.pere = null;
	}
	
	/**
	 * constructeur arbre avec deux arbres en paramètres
	 * @param id
	 * @param g
	 * @param d
	 */
	public Arbre(String id, Sequence seq, Alignement al, Arbre g, Arbre d){
		this.cle = id;
		this.seqCle = seq;
		this.alignCle = al;
		this.droite = d;
		this.gauche = g;
		this.hautD = 0;
		this.hautG = 0;
		this.nbreFeuilles = 1;
		this.pere = null;
	}
	
	/**
	 * restitue la clé de l'arbre
	 * @return
	 */
	public String getCle(){
		return(cle);
	}
	
	/**
	 * restitue l'arbre droit
	 * @return
	 */
	Arbre getDroite() {
		return droite;
	}
	
	/**
	 * restitue l'arbre gauche
	 * @return
	 */
	Arbre getGauche() {
		return gauche;
	}
	
	/**
	 * restitue la hauteur de l'arbre droit
	 * @return
	 */
	public double getHautD() {
		return hautD;
	}
	
	/**
	 * assigne la hauteur de l'arbre droit
	 * @return
	 */
	public void setHautD(double hd) {
		this.hautD = hd;
	}
	
	/**
	 * restitue la hauteur de l'arbre gauche
	 * @return
	 */
	public double getHautG() {
		return hautG;
	}
	
	/**
	 * assigne la hauteur de l'arbre gauche
	 * @return
	 */
	public void setHautG(double hg) {
		this.hautG = hg;
	}
	
	/**
	 * restitue le nombre de feuilles de l'arbre
	 * @return
	 */
	public int getNbreFeuilles() {
		return nbreFeuilles;
	}
	
	/**
	 * assigne le nbre de feuilles
	 * @param nbreFeuilles
	 */
	public void setNbreFeuilles(int nbreFeuilles) {
		this.nbreFeuilles = nbreFeuilles;
	}
	
	/**
	 * assigne le pere du noeud courant
	 * @param pere
	 */
	public void setPere(Arbre pere) {
		this.pere = pere;
	}
	
	/**
	 * retourne le pere courant
	 * @return
	 */
	public Arbre getPere() {
		return pere;
	}
}
