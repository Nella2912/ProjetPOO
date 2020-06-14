/**
 * modelise un arbre avec la méthode UPGMA
 * @author Hornella Fosso-Kahina Lounaci
 *
 */

public class Arbre {
	/**
	 * Séquence à une feuille de l'arbre
	 */
	private Sequence seqCle;
	
	/**
	 * Alignement à un noeud de l'arbre autre qu'une feuille
	 */
	private Alignement alignCle;
	
	/**
	 * fils droit d'un noeud
	 */
	private Arbre droite;
	
	/**
	 * fils gauche d'un noeud
	 */
	private Arbre gauche;
	
	/**
	 * hauteur de fils gauche d'un noeud
	 */
	private double hautG;
	
	/**
	 * hauteur de fils droit d'un noeud
	 */
	private double hautD;
	
	/**
	 * nbre de feuille d'un noeud
	 */
	private int nbreFeuilles;
	
	/**
	 * constructeur Arbre vide
	 */
	public Arbre(){
		this.seqCle = null;
		this.alignCle = null;
		this.droite = null;
		this.gauche = null;
		this.hautD = 0;
		this.hautG = 0;
		this.nbreFeuilles = 1;
	}
	
	/**
	 * constructeur arbre avec deux arbres en paramètres
	 * @param id
	 * @param g
	 * @param d
	 */
	public Arbre(Sequence seq, Alignement al, Arbre g, Arbre d){
		this.seqCle = seq;
		this.alignCle = al;
		this.droite = d;
		this.gauche = g;
		this.hautD = 0;
		this.hautG = 0;
		this.nbreFeuilles = 1;
	}
	
	/**
	 * fonction faisant le parcours de l'arbre et les alignements à chaque noeud de l'arbre
	 * @param abr
	 * @return
	 */
	public Arbre parcourArbre() {
		if (this.gauche == null && this.droite == null) {
			return this;
		}
		
		this.gauche = this.gauche.parcourArbre();
		this.droite = this.droite.parcourArbre();
		Alignement al = initAlignementAbr(this);
		al.aligner();
		this.alignCle = al;
		
		return this;
	}
	
	/**
	 * fonction permettant d'initialiser un alignement lors du parcours de l'arbre
	 * @param abr
	 * @return
	 */
	private Alignement initAlignementAbr(Arbre abr) {
		if (abr.gauche.seqCle != null && abr.droite.seqCle != null) {
			return new Alignement(abr.gauche.seqCle, abr.droite.seqCle);
		} else {
			if (abr.gauche.seqCle != null && abr.droite.seqCle == null) {
				return new Alignement(abr.droite.alignCle, abr.gauche.seqCle);
			} else {
				if (abr.gauche.seqCle == null && abr.droite.seqCle != null) {
					return new Alignement(abr.gauche.alignCle, abr.droite.seqCle);
				} else {
					return new Alignement(abr.gauche.alignCle, abr.droite.alignCle);
				}
			}
		}
	}
	
	/**
	 * assigne l'arbre droit
	 * @param droite
	 */
	public void setDroite(Arbre droite) {
		this.droite = droite;
	}
	
	/**
	 * restitue l'arbre droit
	 * @return
	 */
	public Arbre getDroite() {
		return droite;
	}
	
	/**
	 * assigne l'arbre gauche
	 * @param gauche
	 */
	public void setGauche(Arbre gauche) {
		this.gauche = gauche;
	}
	
	/**
	 * restitue l'arbre gauche
	 * @return
	 */
	public Arbre getGauche() {
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
	 * assigne la clé de séquence au niveau des noeuds
	 * @param seqCle
	 */
	public void setSeqCle(Sequence seqCle) {
		this.seqCle = seqCle;
	}
	
	/**
	 * retourne la clé de séquence au niveau des noeuds
	 * @return
	 */
	public Sequence getSeqCle() {
		return seqCle;
	}
	
	/**
	 * retourne la clé d'alignement au niveau des noeuds
	 * @return
	 */
	public Alignement getAlignCle() {
		return alignCle;
	}
	
	/**
	 * assigne la clé d'alignement au niveau des noeuds
	 * @param alignCle
	 */
	public void setAlignCle(Alignement alignCle) {
		this.alignCle = alignCle;
	}
}
