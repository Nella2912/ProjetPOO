/**
 * modelise les alignements (Sequence => Sequence, Alignement<=> Sequence, Alignement <=> Alignement)
 * @author Hornella Fosso-Kahina Lounaci
 *
 */

import java.util.ArrayList;
import java.util.List;

public class Alignement implements IAligneur{
	/**
	 * score pour un match extrait de la matrice de substitution NUC4.4
	 */
	private final static int  MATCH = 5;
	
	/**
	 * score pour un mismatch extrait de la matrice de substitution NUC4.4
	 */
	private final static int MISMATCH = -4;
	
	/**
	 * score pour un gap extrait de la matrice de substitution NUC4.4
	 */
	private final static int GAP = -4;
	
	/**
	 * première Sequence qui doit être alignée
	 */
	private Sequence s1;
	
	/**
	 * deuxième Sequence qui doit être alignée
	 */
	private Sequence s2;
	
	/**
	 * premier alignement qui doit être aligné
	 */
	private Alignement aln1;
	
	/**
	 * deuxième alignement qui doit être aligné
	 */
	private Alignement aln2;
	
	/**
	 * Matrice des scores à remplir en fonction des deux sequences à aligner 
	 */
	private double [][] mat;
	
	/**
	 * Alignement des objets (séquences ou alignements)
	 */
	private List<String> listAlignementSeq;
	
	/**
	 * construction d'un alignement en donnant deux sequences
	 * @param seq1 première sequence
	 * @param seq2 deuxième sequence
	 */
	public Alignement(Sequence seq1, Sequence seq2) {
		this.s1 = seq1;
		this.s2 = seq2;
		this.mat = new double [seq2.longueur() + 1][seq1.longueur() + 1];
		this.listAlignementSeq = new ArrayList<String>();
	}
	
	/**
	 * construction d'un alignement en un alignement et une sequence
	 * @param a1 un alignement
	 * @param seq1 une sequence
	 */
	public Alignement(Alignement a1, Sequence seq1) {
		this.aln1 = a1;
		this.s1 = seq1;
		this.mat = new double [seq1.longueur() + 1][a1.longueur() + 1];
		this.listAlignementSeq = new ArrayList<String>();
	}
	
	/**
	 * construction d'un alignement en un alignement et un autre alignement
	 * @param a1 premier alignement
	 * @param a2 deuxième alignement
	 */
	public Alignement(Alignement a1, Alignement a2) {
		this.aln1 = a1;
		this.aln2 = a2;
		this.mat = new double [a2.longueur() + 1][a1.longueur() + 1];
		this.listAlignementSeq = new ArrayList<String>();
	}
	
	
	/**
	 * remplit la matrice de scores pour un alignement : sequence <=> sequence
	 * @param seq1
	 * @param seq2
	 */
	private void matriceDeScore(Sequence seq1, Sequence seq2) {
		int lig, col;                                                                    // index de la matrice
		double NO, h, g;                                                                    // éléments parmis lesquels on cherche le max
		double max;
		int xlen = seq2.longueur();
		int ylen = seq1.longueur();
		
		for(col = 0; col <= ylen; col++) {                                          // on remplit la première ligne
			mat[0][col] = GAP * col;
		}
		for(lig = 0; lig <= xlen; lig++) { 										 // on remplit la première colonne
			mat[lig][0] = GAP * lig;
		}
		
		for (lig = 1; lig <= xlen; lig++) { 
			for (col = 1; col <= ylen; col++) {
				if (seq2.character(lig - 1) == seq1.character(col - 1)) {
					if(seq2.character(lig - 1) == 'N') { // on a N en face de N et donc le score d'alignement est -1
						NO = mat[lig - 1][col - 1] - 1;
					} else { // y'a pas de N
						NO = mat[lig - 1][col - 1] + MATCH;
					}
		        } else {
		        	if(seq2.character(lig - 1) == 'N' || seq1.character(col - 1) == 'N') { // on a N en face d'un aa quelconque et donc le score d'alignement est -2
		        		NO = mat[lig-1][col-1] - 2;           
		        	} else {
		        		NO = mat[lig - 1][col - 1] + MISMATCH;
		        	}
		        }
		        g = mat[lig][col - 1] + GAP;
		        h = mat[lig - 1][col] + GAP;
		        max = NO;
		        if (h > max) max = h;
		        if (g > max) max = g;
		        mat[lig][col] = max;
	      	}
		}
	}
	
	/**
	 * remplit la matrice de scores pour un alignement : alignement <=> sequence
	 * @param a1
	 * @param seq1
	 */
	private void matriceDeScore(Alignement a1, Sequence seq1) {
		int lig, col;                                                                    // index de la matrice
		double score;
		int ylen = a1.longueur();
		int xlen = seq1.longueur();
		
		for(col = 0; col < ylen + 1; col++) {
			mat[0][col] = GAP * col;
		}
		
		for(lig = 0; lig < xlen + 1; lig++) {
			mat[lig][0] = GAP * lig;
		}
		
		for(lig = 1; lig < xlen + 1; lig++) {
			String c2 = seq1.character(lig - 1) + "";
			for(col = 1; col < ylen + 1; col++) {
				score = 0;
				String c1 = a1.colonne(col - 1);
				for(int i = 0; i < c1.length(); i++) {
					for(int j = 0; j < c2.length(); j++) {
						if(c1.charAt(i) == '-' || c2.charAt(j) == '-') {
							score = score + GAP;
						}else {
							if(c1.charAt(i) == c2.charAt(j)) {
								if (c1.charAt(i) == 'N') {
									score = score - 1;
								}else {
									score = score + MATCH;
								}
							}else {
								if(c1.charAt(i) == 'N' || c2.charAt(j) == 'N') { 
									score = score - 2;
								}else {
									score = score + MISMATCH;
								}
							}
						}
					}
				}
				score = score /(ylen * xlen);
				mat[lig][col] = score;
			}
		}
	}
	
	/**
	 * remplit la matrice de scores pour un alignement : alignement <=> alignement
	 * @param a1
	 * @param a2
	 */
	private void matriceDeScore(Alignement a1, Alignement a2) {
		int lig, col;                                                                    // index de la matrice
		double score;
		int ylen = a1.longueur();
		int xlen = a2.longueur();
		
		for(col = 0; col < ylen + 1; col++) {
			mat[0][col] = GAP * col;
		}
		
		for(lig = 0; lig < xlen + 1; lig++) {
			mat[lig][0] = GAP * lig;
		}
		
		for(lig = 1; lig < xlen + 1; lig++) {
			String c2 = a2.colonne(lig - 1);
			for(col = 1; col < ylen + 1; col++) {
				score = 0;
				String c1 = a1.colonne(col - 1);
				for(int i = 0; i < c1.length(); i++) {
					for(int j = 0; j < c2.length(); j++) {
						if(c1.charAt(i) == '-' || c2.charAt(j) == '-') {
							score = score + GAP;
						}else {
							if(c1.charAt(i) == c2.charAt(j)) {
								if (c1.charAt(i) == 'N') {
									score = score - 1;
								}else {
									score = score + MATCH;
								}
							}else {
								if(c1.charAt(i) == 'N' || c2.charAt(j) == 'N') { 
									score = score - 2;
								}else {
									score = score + MISMATCH;
								}
							}
						}
					}
				}
				score = score /(ylen * xlen);
				mat[lig][col] = score;
			}
		}
	}
	
	/**
	 * methode pour le declenchement des alignements en fonction des cas trouvé : (Sequence => Sequence, Alignement<=> Sequence, Alignement <=> Alignement)
	 */
	public void aligner() {
		if (this.aln1 == null && this.aln2 == null) {
			this.aligner(this.s1, this.s2);
		} else {
			if (this.aln2 == null) {
				this.aligner(this.aln1, this.s1);
			} else {
				this.aligner(this.aln1, this.aln2);
			}
		}
	}
	
	/**
	 * effectue l'alignement de deux séquences à partir de la matrice des scores
	 * @param seq1
	 * @param seq2
	 */
	private void aligner(Sequence seq1, Sequence seq2) {
		// Calcul de la matrice des scores entre les deux séquences
		this.matriceDeScore(seq1, seq2);
		
		// Alignement des deux séquences
		String alignementSeq1 = "", alignementSeq2 = "";
		int i = seq2.longueur();
		int j = seq1.longueur();
		while(i > 0 && j > 0) {
			double score = mat[i][j];
			double scoreG = mat[i - 1][j];
			double scoreNO = mat[i - 1][j - 1];
			if (seq2.character(i - 1) == seq1.character(j - 1)) {
				if(seq2.character(i - 1) == 'N') {
					scoreNO = scoreNO - 1;
				}else {
					scoreNO = scoreNO + MATCH;
				}
			}else {
	        	if(seq2.character(i - 1) == 'N' || seq1.character(j - 1) == 'N') {
	        		scoreNO = scoreNO - 2;
	        	}else {
	        		scoreNO = scoreNO + MISMATCH;
	        	}
			}
			
			if(score == scoreNO) {
				alignementSeq1 = seq1.character(j - 1) + alignementSeq1;
				alignementSeq2 = seq2.character(i - 1) + alignementSeq2;
				i--;
				j--;
			}else {
				if(score == scoreG + GAP) {
					alignementSeq1 = seq1.character(j - 1) + alignementSeq1;
					alignementSeq2 = '-' + alignementSeq2;
					j--;
				}else{                                                         //(score == scoreH + gap)
					alignementSeq1 = '-' + alignementSeq1;
					alignementSeq2 = seq2.character(i - 1) + alignementSeq2;
					i--;
				}
			}
		}
		
		while(j > 0) {
			alignementSeq1 = seq1.character(j - 1) + alignementSeq1;
			alignementSeq2 = '-' + alignementSeq2;
			j--;
		}
		
		while(i > 0) {
			alignementSeq1 = '-' + alignementSeq1;
			alignementSeq2 = seq2.character(i - 1) + alignementSeq2;
			i--;
		}
		
		this.listAlignementSeq.add(alignementSeq1);
		this.listAlignementSeq.add(alignementSeq2);
	}
	
	/**
	 * effectue l'alignement d'un alignement et d'une séquence
	 * @param a1
	 * @param seq1
	 */
	private void aligner(Alignement a1, Sequence seq1) {
		// Calcul de la matrice des scores entre un alignement et une séquence
		this.matriceDeScore(a1, seq1);
		
		// Alignement d'un alignement et d'une séquence
		String alignementSeq1 = "";
		int i = seq1.longueur();
		int j = a1.longueur();
		
		List<String> tmpListAln1 = new ArrayList<String>();
		int count = a1.getListAlignementSeq().size();
		for (int l = 0; l < count; l++) {
			tmpListAln1.add("");
		}
		
		while(i > 0 && j > 0) {
			double scoreH = mat[i - 1][j] + GAP;
			double scoreG = mat[i][j - 1] + GAP;
			double scoreNO = mat[i - 1][j - 1];
		
			double max = Math.max(scoreNO, Math.max(scoreG, scoreH));
			
			if(max == scoreNO) {
				tmpListAln1 = miseAJourListAlignementSeq(a1, count, j, tmpListAln1);
				alignementSeq1 = seq1.character(i - 1) + alignementSeq1;
				i--;
				j--;
			}else {
				if(max == scoreG){
					tmpListAln1 = miseAJourListAlignementSeq(a1, count, j, tmpListAln1);
					alignementSeq1 = "-" + alignementSeq1;
					j--;
				}else{                                                    //(max == scoreH)
					tmpListAln1 = miseAJourListAlignementSeq(a1, count, -1, tmpListAln1);				
					alignementSeq1 = seq1.character(i - 1) + alignementSeq1;
					i--;
				}
			}
		}
		while(j > 0) {
			tmpListAln1 = miseAJourListAlignementSeq(a1, count, j, tmpListAln1);
			alignementSeq1 = "-" + alignementSeq1;
			j--;
		}
		
		while(i > 0) {
			tmpListAln1 = miseAJourListAlignementSeq(a1, count, -1, tmpListAln1);
			alignementSeq1 = seq1.character(i - 1) + alignementSeq1;
			i--;
		}
		
		tmpListAln1.add(alignementSeq1);
		this.setListAlignementSeq(tmpListAln1);
	}
	
	/**
	 * effectue l'alignement de deux alignements
	 * @param a1
	 * @param a2
	 */
	private void aligner(Alignement a1, Alignement a2) {
		// Calcul de la matrice des scores entre les deux alignements
		this.matriceDeScore(a1, a2);
		
		// Alignement des deux alignements
		int i = a2.longueur();
		int j = a1.longueur();
		
		List<String> tmpListAln1 = new ArrayList<String>();
		int countA1 = a1.getListAlignementSeq().size();
		for (int l = 0; l < countA1; l++) {
			tmpListAln1.add("");
		}
		
		List<String> tmpListAln2 = new ArrayList<String>();
		int countA2 = a2.getListAlignementSeq().size();
		for (int l = 0; l < countA2; l++) {
			tmpListAln2.add("");
		}
		
		while(i > 0 && j > 0) {
			double scoreH = mat[i - 1][j] + GAP;
			double scoreG = mat[i][j - 1] + GAP;
			double scoreNO = mat[i - 1][j - 1];
			
			double max = Math.max(scoreNO, Math.max(scoreG, scoreH));
			
			if(max == scoreNO) {
				tmpListAln1 = miseAJourListAlignementSeq(a1, countA1, j, tmpListAln1);
				tmpListAln2 = miseAJourListAlignementSeq(a2, countA2, i, tmpListAln2);
				i--;
				j--;
			}else {
				if(max == scoreG){
					tmpListAln1 = miseAJourListAlignementSeq(a1, countA1, j, tmpListAln1);
					tmpListAln2 = miseAJourListAlignementSeq(a2, countA2, -1, tmpListAln2);
					j--;
				}else{                                                    //(max == scoreH)
					tmpListAln1 = miseAJourListAlignementSeq(a1, countA1, -1, tmpListAln1);
					tmpListAln2 = miseAJourListAlignementSeq(a2, countA2, i, tmpListAln2);
					i--;
				}
			}
		}
		while(j > 0) {
			tmpListAln1 = miseAJourListAlignementSeq(a1, countA1, j, tmpListAln1);
			tmpListAln2 = miseAJourListAlignementSeq(a2, countA2, -1, tmpListAln2);
			j--;
		}
		
		while(i > 0) {
			tmpListAln1 = miseAJourListAlignementSeq(a1, countA1, -1, tmpListAln1);
			tmpListAln2 = miseAJourListAlignementSeq(a2, countA2, i, tmpListAln2);
			i--;
		}
		
		tmpListAln1.addAll(tmpListAln2);
		this.setListAlignementSeq(tmpListAln1);
	}
	
	/**
	 * calcule le score de l'alignement de deux séquences
	 * @return score de l'alignement
	 */
	public double scoreAlignement() {
		double score = 0.0;
		int i;
		int lon = this.listAlignementSeq.get(0).length();
		for (i = 0; i < lon; i++) {
			if(this.listAlignementSeq.get(0).charAt(i) == this.listAlignementSeq.get(1).charAt(i)) {
				score = score + 1;
			}
		}
		score = ((score/lon) * 10000)/10000;
		return score;
	}
	
	/**
	 * affiche le résultat de l'alignement
	 */
	public void afficher() {
		for(String str : this.listAlignementSeq) {
			System.out.println(str);
		}
	}
	
	/**
	 * fontion pour mettre à jour les alignements des séquences dans la liste des alignements de séquences pour un alignement donné.
	 * @param al
	 * @param count
	 * @param index
	 * @param tmpListAln
	 * @return
	 */
	private List<String> miseAJourListAlignementSeq(Alignement al, int count,  int index, List<String> tmpListAln) {
		for (int l = 0; l < count; l++) {
			String strtmp = tmpListAln.get(l);
			if(index < 0) {
				strtmp = "-" + strtmp;
			} else {
				strtmp = al.getListAlignementSeq().get(l).charAt(index - 1) + strtmp;
			}
			tmpListAln.remove(l);
			tmpListAln.add(l, strtmp);
		}
		
		return tmpListAln;
	}
	
	/**
	 * retourne la longueur de l'alignement
	 * @return
	 */
	public int longueur() {
		return this.listAlignementSeq.get(0).length();
	}
	
	/**
	 * renvoi la colonne d'indice "index" d'un alignement
	 * @param index
	 * @return
	 */
	public String colonne(int index) {
		String col = "";
		for (String str : this.getListAlignementSeq()) {
			col = col + str.charAt(index);
		}
		return col;
	}
	
	/**
	 * retourne la matrice des scores mat
	 * @return
	 */
	public double[][] getMat() {
		return mat;
	}
	
	/**
	 * retourne la séquence s1
	 * @return
	 */
	public Sequence getS1() {
		return s1;
	}
	
	/**
	 * retourne la séquence s2
	 * @return
	 */
	public Sequence getS2() {
		return s2;
	}
	
	/**
	 * retourne l'alignement aln1
	 * @return
	 */
	public Alignement getAln1() {
		return aln1;
	}
	
	/**
	 * retourne l'alignement aln2
	 * @return
	 */
	public Alignement getAln2() {
		return aln2;
	}
	
	/**
	 * retourne la liste contenant les différentes lignes d'un alignements
	 * @return
	 */
	public List<String> getListAlignementSeq() {
		return listAlignementSeq;
	}
	
	/**
	 * assigne la liste contenant les différentes lignes d'un alignements
	 * @param listAlignementSeq
	 */
	public void setListAlignementSeq(List<String> listAlignementSeq) {
		this.listAlignementSeq = listAlignementSeq;
	}
	
	/**
	 * retourne la valeur du match
	 * @return
	 */
	public int getMATCH() {
		return MATCH;
	}
	
	/**
	 * retourne la valeur du mismatch
	 * @return
	 */
	public int getMISMATCH() {
		return MISMATCH;
	}
	
	/**
	 * retourne la valeur du Gap
	 * @return
	 */
	public int getGAP() {
		return GAP;
	}
}
