/**
 * Contient les fonctions statiques communes au projet
 * @author Hornella Fosso-Kahina Lounaci
 *
 */

public class Communes {
	/**
	 * fonction prennant une matrice carrée en entrée et l'affiche à la console
	 * @param matrice
	 */
	public static void afficheMat(double[][] matrice) {
		int dim1 = matrice[0].length;
		int dim2 = matrice.length;
		for (int i= 0; i < dim2; i++) {
			for(int j = 0; j < dim1; j++) {
				System.out.print(matrice[i][j] + " ");
			}
			System.out.println("");
		}
	}
}
