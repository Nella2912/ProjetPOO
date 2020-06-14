/**
 * modelise un arbre avec la méthode UPGMA
 * @author Hornella Fosso-Kahina Lounaci
 *
 */

import java.util.ArrayList;
import java.util.List;

public class ConstructionArbre {
	private MatriceDistance mD;               // une matrice de distances prise en paramètre
	private List<Arbre> listeArbres;           // Liste des arbres
	
	/**
	 * 
	 * @param matDist
	 */
	public ConstructionArbre(MatriceDistance matDist) {
		this.mD = matDist;
		this.initArbre();
	}
	
	/**
	 * méthode permettant de créer une liste d'arbres réduit à des feuilles qui sont des séquences
	 */
	private void initArbre() {
		try {
			listeArbres = new ArrayList<Arbre>();
			for (Sequence seq : mD.getListeDesSequences()) {
				Arbre a = new Arbre(seq, null, null, null);
				listeArbres.add(a);
			}
		}
		catch(Exception e) {
			throw e;
		}
	}
	
	/**
	 * 
	 * @param gA
	 * @param dA
	 * @param distGD
	 * @return la fusion des arbres gauche et droit en paramètre
	 */
	public Arbre fusionAbr(Arbre gA, Arbre dA, double distGD){
		try {
			Arbre pere = new Arbre(null, null, gA, dA);
			
			if (gA.getDroite() == null && gA.getGauche() == null && dA.getDroite() == null && dA.getGauche() == null) {
				pere.setHautD(distGD / 2);
				pere.setHautG(distGD / 2);
				pere.setNbreFeuilles(2);
			} else {
				if (gA.getDroite() == null && gA.getGauche() == null && !(dA.getDroite() == null && dA.getGauche() == null)) {
					pere.setHautG(distGD / 2);
					pere.setHautD(pere.getHautG() - Math.max(dA.getHautG(), dA.getHautD()));
					pere.setNbreFeuilles(1 + dA.getNbreFeuilles());
				} else {
					if (!(gA.getDroite() == null && gA.getGauche() == null) && dA.getDroite() == null && dA.getGauche() == null) {
						pere.setHautD(distGD / 2);
						pere.setHautG(pere.getHautD() - Math.max(gA.getHautG(), gA.getHautD()));
						pere.setNbreFeuilles(1 + gA.getNbreFeuilles());
					} else {
						pere.setHautG((distGD / 2) - Math.max(gA.getHautG(), gA.getHautD()));
						pere.setHautD((distGD / 2) - Math.max(dA.getHautG(), dA.getHautD()));
						pere.setNbreFeuilles(gA.getNbreFeuilles() + dA.getNbreFeuilles());
					}
				}
			}
			
			return pere;
		}
		catch(Exception e) {
			throw e;
		}
	}
	
	/**
	 * prend en argument la matrice  de distance et retourne le couple d'index de la lus petite valeur de la matrice
	 * @return
	 * @throws Exception
	 */
	private int[] distMin() throws Exception {
		try {
			double matDeDist[][] = mD.getDistMat();
			int ligmin = 0;
			int colmin = 1;
			double min = matDeDist[ligmin][colmin];
			int longueur = matDeDist.length;
			
			for( int lig = 0; lig < longueur; lig++) {
				for( int col = lig + 1; col < longueur; col++) { // ici faut comprendre comment la matice de score est construite
					if (min > matDeDist[lig][col]) {
						min = matDeDist[lig][col];
						ligmin = lig;
						colmin = col;
					}
				}
			}
			
			int result[] = new int[2];
			result[0] = ligmin;
			result[1] = colmin;
			
			return result;
		}
		catch(Exception e) {
			throw e;
		}
	}
	
	/**
	 * fonction prenant un index et une matrice carré et qui supprime la ligne et la colonne correspondante à cet index
	 * @param ancienMat
	 * @param index
	 * @return
	 */
	public double[][] suppressionLigneColMatrice(double[][] ancienMat, int index) {
		try {
			double[][] newMat = new double[ancienMat.length - 1][ancienMat.length - 1];
			int k = 0;
			
			if(index < ancienMat.length - 1) {
				for(int i = 0; i < ancienMat.length; i++) {
					if(i == index) {
						i++;
					}
					System.arraycopy(ancienMat[i], 0, newMat[k], 0, index);
					if (ancienMat[i].length != index) {
						System.arraycopy(ancienMat[i], index + 1, newMat[k], index, ancienMat[i].length - index - 1);
					}
					k++;
				}
			} else {
				for(int i = 0; i < ancienMat.length - 1; i++) {
					for(int j = i + 1; j < ancienMat.length - 1; j++) {
						newMat[i][j] = ancienMat[i][j];
						newMat[j][i] = ancienMat[j][i];
					}
				}
			}
			
			return newMat;
		}
		catch(Exception e) {
			throw e;
		}
	}
	
	/**
	 * fonction permettant de calculer la nouvelle matrice des distances
	 * @param ligmin
	 * @param colmin
	 * @param A
	 */
	private void calcMatrice(int ligmin, int colmin, Arbre A){
		try {
			double ancienMat[][] = this.mD.getDistMat();
			
			// Suppression des lignes et colonnes correspondant à la distance minimale dans la matrice précédente
			double tempMat[][] = suppressionLigneColMatrice(ancienMat, ligmin);
			tempMat = suppressionLigneColMatrice(tempMat, colmin - 1);
			
			// Initialisation des dernières colonnes avant calcul des nouvelles distances
			double nouvMat[][] = new double[tempMat.length + 1][tempMat.length + 1];
			for(int i = 0; i < tempMat.length; i++) {
				for (int j = i + 1; j < tempMat.length; j++) {
					nouvMat[i][j] = tempMat[i][j];
					nouvMat[j][i] = tempMat[j][i];
				}
			}
			
			// Mise à jour de la liste des index de l'ancienne matrice pour matcher avec la nouvelle
			// Tableau des précédent index en fonction de la nouvelle matrice index correspond à nouvelle matrice et valeur corresponds index ancienne matrice)
			List<Integer> listAncienIndexMatDist = this.mD.getListAncienIndexMatDist();
			listAncienIndexMatDist.remove(ligmin);
			listAncienIndexMatDist.remove(colmin - 1);
			
			int j = nouvMat.length - 1;
			int indexK = 0;
			List<Integer> tempList = new ArrayList<Integer>();
			for(int k = 0; k < nouvMat.length - 1 ; k++) {
				double dik = 0.0;
				double djk = 0.0;
				double duk = 0.0;
				
				double ni = A.getGauche().getNbreFeuilles();
				double nj = A.getDroite().getNbreFeuilles();
				
				indexK = listAncienIndexMatDist.get(k);
				dik = ancienMat[indexK][ligmin];
				djk = ancienMat[indexK][colmin];
				duk = ((ni / (ni + nj)) * dik) + ((nj / (ni + nj)) * djk);
				
				nouvMat[k][j] = duk;
				nouvMat[j][k] = duk;
				
				tempList.add(k);
			}
			tempList.add(nouvMat.length - 1);
			
			this.mD.setListAncienIndexMatDist(tempList);
			this.mD.setDistMat(nouvMat);
		}
		catch(Exception e) {
			throw e;
		}
	}
	
	/**
	 * procédure respectant l'algorithme UPGMA
	 * @throws Exception 
	 */
	public void upgma() throws Exception {
		try {
			while(listeArbres.size() > 1) {
				double matDist[][] = this.mD.getDistMat();
				
				int distMin[] = this.distMin();                                // On récupère les indexs dans la matrice des distances de la valeur minimale
				double distGD = matDist[distMin[0]][distMin[1]];              // On prends la distance minimale
				Arbre abrFusione = this.fusionAbr(listeArbres.get(distMin[0]), listeArbres.get(distMin[1]), distGD);    // On fusionne les arbres
				
				listeArbres.remove(distMin[0]);                              // On supprime les deux indexs min de la liste des arbres
				listeArbres.remove(distMin[1] - 1);
				listeArbres.add(abrFusione);                                 // On ajoute l'arbre fusionné en fin de Liste
				if(matDist.length > 2) {
					this.calcMatrice(distMin[0], distMin[1], abrFusione);        // On calcule la nouvelle matrice des distances
				}
			}
		} catch (Exception e) {
			throw e;
		}
	}
	
	/**
	 * restitue la matrice des distances
	 * @return
	 */
	public MatriceDistance getmD() {
		return mD;
	}
	
	/**
	 * restitue la liste des Arbres
	 * @return
	 */
	public List<Arbre> getListeArbres() {
		return listeArbres;
	}
}
