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
	private int ID;
	
	/**
	 * Sequence
	 */
	private String seq;
	
	/**
	 * construction d'une sequence en donnant son identifiant et la sequence elle-même
	 * @param identifiant : l'identifiant de la sequence
	 * @param chaine : la sequence
	 */
	public Sequence(int identifiant, String chaine) {
		this.ID = identifiant;
		this.seq = chaine;
	}
	
	/**
	 * fixe l'identifiant de la sequence
	 * @param identifiant: ID de la sequence
	 */
	public void setID(int identifiant) {
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
	public int getID() {
		return ID;
	}
	
	/**
	 * restitue la sequence
	 * @return
	 */
	public String getSEQ() {
		return seq;
	}
}
