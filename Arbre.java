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
	public Alignement parcourArbre() {
		/*
		Arbre A =new Arbre();
		A=listeArbres.get(0); recupere l'arbre en entier construit
		*/
		Arbre A1 = this.gauche;
		Arbre A2 = this.droite;
		
		System.out.println("rien");
		if(A1.gauche==null){System.out.println("merde1");}
		if(A2==null){System.out.println("merde2");}
		
		if(A1 ==null && A2==null){
			
			Alignement AS = new Alignement(A1.pere.seqCle,A2.pere.seqCle);
			AS.aligner(); // alignement au niveau des feuilles
			System.out.println("merdeif");
			return AS;
		}
		else {
			System.out.println("merde");
			Alignement AS1 = A1.parcourArbre();
			
			AS1.aligner();
			
			Alignement AS2 = A2.parcourArbre();
			AS2.aligner();
			Alignement AS = new Alignement(AS1,AS2);
		
			return AS;
		}
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
