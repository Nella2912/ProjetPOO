/**
 * Modelise la notion de sequence avec un identifiant et une
 * chaine de caracteres représentant la séquence elle-même
 * @author Hornella Fosso - Kahina Lounaci
 *
 */

public class Sequence {
	/**
	 * Identifiant de la sequence
	 */
	private String ID;
	
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
		this.ID = identifiant;
		this.seq = chaine;
	}
	
	/**
	 * fixe l'identifiant de la sequence
	 * @param identifiant: ID de la sequence
	 */
	public void setID(String identifiant) {
		this.ID = identifiant;
	}
	
	/**
	 * fixe la chaine de caractère correspondante à la sequence
	 * @param chaine: Sequence
	 */
	public void setSEQ(String chaine) {
		this.seq = chaine;
	}
	
	/**
	 * restitue l'identifiant de la sequence
	 * @return identifiant de la sequence
	 */
	public String getID() {
		return ID;
	}
	
	/**
	 * restitue la sequence
	 * @return
	 */
	public String getSEQ() {
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
		System.out.println(ID + " " + seq);
	}
}
