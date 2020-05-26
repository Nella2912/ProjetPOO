
public class Alignement2align {
	
	Alignement aln1;
	
	Alignement aln2;
	
	/**
	 * score pour un match extrait de la matrice de substitution NUC4.4
	 */
	private final double  match = 5;
	
	/**
	 * score pour un mismatch extrait de la matrice de substitution NUC4.4
	 */
	private final double mismatch = -4;
	
	/**
	 * score pour un gap extrait de la matrice de substitution NUC4.4
	 */
	private final double gap = -4;
	
	
	private double mat [][];
	
	private String alignement1Seq1 = "";
	
	private String alignement1Seq2 = "";
	
	private String alignement2Seq1 = "";
	
	private String alignement2Seq2 = "";
	
	
	public double[][] getMat() {
		return mat;
	}
	
	public Alignement2align(Alignement aln1, Alignement aln2) {
		this.aln1 = aln1;
		this.aln2 = aln2;
		this.mat = new double [this.aln2.longueur() + 1][this.aln1.longueur() + 1];
	}
	
	public void matriceDeScore() {
		int lig, col;                                                                    // index de la matrice
		double score;
		int ylen = aln1.longueur();
		int xlen = aln2.longueur();
		
		for(col = 0; col < ylen + 1; col++) {
			mat[0][col] = gap * col;
		}
		
		for(lig = 0; lig < xlen + 1; lig++) {
			mat[lig][0] = gap * lig;
		}
		
		for(lig = 1; lig < xlen + 1; lig++) {
			String a2 = aln2.colonne(lig - 1);
			for(col = 1; col < ylen + 1; col++) {
				score = 0;
				String a1 = aln1.colonne(col - 1);
				for(int i = 0; i < a1.length(); i++) {
					for(int j = 0; j < a2.length(); j++) {
						if(a1.charAt(i) == '-' || a2.charAt(j) == '-') {
							score = score + gap;
						}else {
							if(a1.charAt(i) == a2.charAt(j)) {
								score = score + match;
							}else {
								score = score + mismatch;
							}
						}
					}
				}
				score = score /(aln1.longueur() * aln2.longueur());
				mat[lig][col] = score;
			}
		}
	}

	/*
	public void aligner() {
		int i = aln2.longueur();
		int j = aln1.longueur();
		
		while(i > 0 && j > 0) {
			System.out.println(aln2.colonne(i - 1) + " ====> " + aln1.colonne(j - 1));
			
			double score = mat[i][j];
			System.out.println(score);
			double scoreH = mat[i - 1][j] + gap;
			System.out.println(scoreH);
			double scoreG = mat[i][j - 1] + gap;
			System.out.println(scoreG);
			double scoreNO = mat[i - 1][j - 1];
			
			
			if (aln1.colonne(j - 1).equals(aln2.colonne(i - 1))) {
				scoreNO = mat[i - 1][j - 1] + match;
			} else {
	        	scoreNO = mat[i - 1][j - 1] + mismatch;
			}
			
			
			double max = Math.max(scoreNO, Math.max(scoreG, scoreH));
			
			System.out.println(scoreNO);
			
			if(max == scoreNO) {
				alignement1Seq1 = aln1.getAlignementSeq1().charAt(j - 1) + alignement1Seq1;
				alignement1Seq2 = aln1.getAlignementSeq2().charAt(j - 1) + alignement1Seq2;
				
				alignement2Seq1 = aln2.getAlignementSeq1().charAt(i - 1) + alignement2Seq1;
				alignement2Seq2 = aln2.getAlignementSeq2().charAt(i - 1) + alignement2Seq2;
				
				i--;
				j--;
				System.out.println("je decremente i et j");
			}else {
				if(max == scoreG){
					alignement1Seq1 = aln1.getAlignementSeq1().charAt(j - 1) + alignement1Seq1;
					alignement1Seq2 = aln1.getAlignementSeq2().charAt(j - 1) + alignement1Seq2;
					
					alignement2Seq1 = '-' + alignement2Seq1;
					alignement2Seq2 = '-' + alignement2Seq2;
					
					j--;
					System.out.println("je decremente j");
				}else{                                                    //(max == scoreH)
					alignement1Seq1 = '-' + alignement1Seq1;
					alignement1Seq2 = '-' + alignement1Seq2;
					
					alignement2Seq1 = aln2.getAlignementSeq1().charAt(i - 1) + alignement2Seq1;
					alignement2Seq2 = aln2.getAlignementSeq2().charAt(i - 1) + alignement2Seq2;
					
					System.out.println("je decremente i");
					i--;
				}
			}
			//i = j = 0;
		}
		while(j > 0) {
			alignement1Seq1 = aln1.getAlignementSeq1().charAt(j - 1) + alignement1Seq1;
			alignement1Seq2 = aln1.getAlignementSeq2().charAt(j - 1) + alignement1Seq2;
			alignement2Seq1 = '-' + alignement2Seq1;
			alignement2Seq2 = '-' + alignement2Seq2;
			j--;
		}
		
		while(i > 0) {
			alignement1Seq1 = '-' + alignement1Seq1;
			alignement1Seq2 = '-' + alignement1Seq2;
			alignement2Seq1 = aln2.getAlignementSeq1().charAt(i - 1) + alignement2Seq1;
			alignement2Seq2 = aln2.getAlignementSeq2().charAt(i - 1) + alignement2Seq2;
			i--;
		}
	}
	*/
	
	public void afficher() {
		System.out.println(alignement1Seq1);
		System.out.println(alignement1Seq2);
		System.out.println(alignement2Seq1);
		System.out.println(alignement2Seq2);
	}
	
}
