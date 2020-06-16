/**
 * Modelise la notion de sequence avec un identifiant et une
 * chaine de caracteres représentant la séquence elle-même
 * @author Hornella Fosso - Kahina Lounaci
 *
 */

public class Sequence {
	/**
	 * longueur statique maximale de l'ID (utile pour un bon affichage)
	 */
	public final static int longeuerIdMax = 8;
	
	/**
	 * Identifiant affiche sur "longueurIdMax" caractères
	 */
	private String IdAffiche;
	
	/**
	 * Identifiant de la sequence
	 */
	private String id;
	
	/**
	 * Sequence
	 */
	private String seq;
	
	/**
	 * construction d'une sequence en donnant son identifiant et la sequence elle-même
	 * @param identifiant : l'identifiant de la sequence
	 * @param chaine : la sequence
	 */
	public Sequence(String identifiant, String chaine) {
		this.id = identifiant;
		this.seq = chaine;
		initIdAffiche();
	}
	
	/**
	 * fixe l'identifiant de la sequence
	 * @param identifiant: ID de la sequence
	 */
	public void setID(String identifiant) {
		this.id = identifiant;
	}
	
	/**
	 * fixe la chaine de caractère correspondante à la sequence
	 * @param chaine: Sequence
	 */
	public void setSEQ(String chaine) {
		this.seq = chaine;
	}
	
	/**
	 * restitue l'identifiant de la sequence à affichr sur "longeuerIdMax"
	 * @return identifiant de la sequence à afficher sur "longeuerIdMax"
	 */
	public String getIdAffiche() {
		return this.IdAffiche;
	}
	
	/**
	 * restitue l'identifiant de la sequence
	 * @return identifiant de la sequence
	 */
	public String getId() {
		return id;
	}
	
	/**
	 * restitue la sequence
	 * @return
	 */
	public String getSeq() {
		return seq;
	}
	
	/**
	 * restitue la longueur d'une sequence
	 * @return longueur
	 */
	public int longueur() {
		return seq.length();
	}
	
	/**
	 * retourne un caractere de la sequence
	 * @param index position du caractere
	 * @return caractere à la position entrée en paramètre
	 */
	public char character(int index) {
		return seq.charAt(index);
	}
	
	/**
	 * permet d'afficher en console pour une séquence, son ID et sa valeur
	 */
	public void affiche() {
		System.out.println(id + " " + seq);
	}
	
	/**
	 * Initialise l'ID de la séquence à affciher sur "longeuerIdMax"
	 */
	public void initIdAffiche() {
		String result = "";
		for (int i = 0; i < longeuerIdMax; i++) {
			if (i >= id.length()) {
				result = result + " ";
			} else {
				result = result + id.charAt(i);
			}
		}
		this.IdAffiche = result;
	}
}
