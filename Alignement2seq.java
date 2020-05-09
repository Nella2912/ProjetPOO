/**
 * modelise l'alignement de deux sequences avec l'algorithme de Wunsch et Needlemann
 * @author Hornella Fosso-Kahina Lounaci
 *
 */
public class Alignement2seq implements Aligneur {
	/**
	 * première Sequence qui doit être alignée
	 */
	private Sequence s1;
	
	/**
	 * deuxième Sequence qui doit être alignée
	 */
	private Sequence s2;
	
	/**
	 * score pour un match extrait de la matrice de substitution NUC4.4
	 */
	private final int  match = 5;
	
	/**
	 * score pour un mismatch extrait de la matrice de substitution NUC4.4
	 */
	private final int mismatch = -4;
	
	/**
	 * score pour un gap extrait de la matrice de substitution NUC4.4
	 */
	private final int gap = -4;
	
	/**
	 * Matrice des scores à remplir en fonction des deux sequences à aligner 
	 */
	private int [][] mat;
	
	/**
	 * Alignement des deux sequences 
	 */
	private String alignementSeq1 = "";
	
	private String alignementSeq2 = "";
	
	
	/**
	 * construction d'un alignement en donnant deux sequences
	 * @param seq1 première sequence
	 * @param seq2 deuxième sequence
	 */
	public Alignement2seq(Sequence seq1, Sequence seq2) {
		this.s1 = seq1;
		this.s2 = seq2;
		this.mat=new int [seq1.longueur()+1][seq2.longueur()+1];
	}
	
	/**
	 * remplit la matrice de scores
	 */
	public void matriceDeScore() {
		int lig, col;                                                                    // index de la matrice
		int NO, h, g;                                                                    // éléments parmis lesquels on cherche le max
		int max;
		int xlen = s1.longueur();
		int ylen = s2.longueur();
		
		for(col=0; col<= s2.longueur(); col++) {                                          // on remplit la première ligne
			mat[0][col] = gap*col;
		}
		for(lig = 0; lig <= s1.longueur(); lig++) { 										 // on remplit la première colonne
			mat[lig][0] = gap*lig;
		}
		
		
		for (lig=1; lig <= xlen; lig++) { 
			for (col=1; col <= ylen; col++) {
					if (s1.character(lig-1) == s2.character(col-1)) {
						/*if(s1.character(lig-1) == 'N') { // on a N en face de N et donc le score d'alignement est -1
							NO = mat[lig-1][col-1] - 1;
						}else { // y'a pas de N*/
							NO = mat[lig-1][col-1] + match;
						//}
			        }else {
			        	/*if(s1.character(lig-1) == 'N' || s2.character(col-1) == 'N') { // on a N en face d'un aa quelconque et donc le score d'alignement est -2
			        		NO = mat[lig-1][col-1] - 2;           
			        	}*/
			        	NO = mat[lig-1][col-1] + mismatch;
			        }
			        g = mat[lig][col-1] + gap;
			        h = mat[lig-1][col] + gap;
			        max = NO;
			        if (h>max) max = h;
			        if (g>max) max = g;
			        mat[lig][col] = max;		        
	      	}
		 }
	}
	
	
	/**
	 * effectue l'alignement de deux séquences à partir de la matrice des scores
	 */
	public void aligner() {
		int i = s1.longueur();
		int j = s2.longueur();
		while(i > 0 && j > 0) {
			System.out.println(i + "et" +j);
			int score = mat[i][j];
			int scoreH = mat[i][j-1];
			int scoreG = mat[i-1][j];
			int scoreNO;
			if (s1.character(i-1) == s2.character(j-1)) {
				/*if(s1.character(i-1) == 'N') {
					System.out.println("je rencontre N 2 fois");
					scoreNO = mat[i-1][j-1] - 1;
				}else {*/
					scoreNO = mat[i-1][j-1] + match;
				//}
			}else {
	        	/*if(s1.character(i-1) == 'N' || s2.character(j-1) == 'N') {
	        		System.out.println("je rencontre N 1 fois");
	        		scoreNO = mat[i-1][j-1] - 2;
	        	}else {*/
	        		scoreNO = mat[i-1][j-1] + mismatch;
	        	//}
			}
			if(score == scoreNO) {
				alignementSeq1 = s1.character(i-1)+alignementSeq1;
				alignementSeq2 = s2.character(j-1)+alignementSeq2;
				i--;
				j--;
				System.out.println("je decremente i et j");
			}else {
				if(score == scoreG + gap) {
					alignementSeq1 = s1.character(i-1)+alignementSeq1;
					alignementSeq2 = '-'+alignementSeq2;
					System.out.println("je decremente i");
					i--;
				}
				if(score == scoreH + gap){
					alignementSeq1 = '-'+alignementSeq1;
					alignementSeq2 = s2.character(j-1)+alignementSeq2;
					j--;
					System.out.println("je decremente j");
				}
			}
		}
		while(i>0) {
			alignementSeq1 = s1.character(i-1)+alignementSeq1;
			alignementSeq2 = '-'+alignementSeq2;
			i--;
		}
		
		while(j>0) {
			alignementSeq1 = '-'+alignementSeq1;
			alignementSeq2 = s2.character(j-1)+alignementSeq2;
			j--;
		}
	}
	
	
	/**
	 * calcule le score de l'alignement
	 * @return score de l'alignement
	 */
	public int scoreAlignement() {
		int score = 0;
		int i;
		for (i = 0; i<alignementSeq1.length(); i++) {
			if(alignementSeq1.charAt(i) == alignementSeq2.charAt(i)) {
				/*if(alignementSeq1.charAt(i) == 'N') {
					score = score - 1;
				}else {*/
					score = score + match;
				//}
			}else {
				if(alignementSeq1.charAt(i) == '-' || alignementSeq2.charAt(i) == '-') {
					score = score + gap;
				}else {
					/*if(alignementSeq1.charAt(i) == 'N' || alignementSeq2.charAt(i) == 'N') {
						score = score - 2;
					}else {*/
						score = score + mismatch;
					//}
				}
			}
		}
		return score;
	}
	
	
	/**
	 * affiche le résultat de l'alignement
	 */
	public void afficher() {
		System.out.println(s1.getID()+" "+alignementSeq1+ " "+s1.longueur());
		System.out.println(s2.getID()+" "+alignementSeq2+ " "+s2.longueur());
	}
	public Sequence getS1() {
		return s1;
	}
	public Sequence getS2() {
		return s2;
	}
	public int getMatch() {
		return match;
	}
	public int getMismatch() {
		return mismatch;
	}
	public int getGap() {
		return gap;
	}
	public String getAlignementSeq1() {
		return alignementSeq1;
	}
	public String getAlignementSeq2() {
		return alignementSeq2;
	}
}
