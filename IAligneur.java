/**
 * Interface de modelisation des alignements (Sequence => Sequence, Alignement<=> Sequence, Alignement <=> Alignement)
 * @author Hornella Fosso-Kahina Lounaci
 *
 */

public interface IAligneur {
	public void aligner();
	public double scoreAlignement();
	public void afficher();
	public int longueur();
	public String colonne(int index);
}
