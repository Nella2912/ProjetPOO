/**
 * 
 * @author Hornella Fosso - Kahina Lounaci
 *
 */

public class AlignementMultiple {

	/**
	 * fonction principale exécuté au depart du programme
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		MatriceDistance mat = new MatriceDistance("ADN16S.fasta");
		mat.initMatriceDistance();
		//Communes.afficheMatrice(mat.getDistMat());
		ConstructionArbre ctrA = new ConstructionArbre(mat);
		ctrA.upgma();
		
		Arbre A = ctrA.getListeArbres().get(0).parcourArbre();
		
		A.getAlignCle().afficher();
		
		Fenetre fen = new Fenetre(A.getAlignCle());
		fen.afficher();
	}
}