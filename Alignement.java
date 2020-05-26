import java.util.ArrayList;
import java.util.List;

/**
 * modelise les alignements (Sequence => Sequence, Alignement<=> Sequence, Alignement <=> Alignement)
 * @author Hornella Fosso-Kahina Lounaci
 *
 */
public class Alignement implements IAligneur{
	/**
	 * première Sequence qui doit être alignée
	 */
	private Sequence s1;
	
	/**
	 * deuxième Sequence qui doit être alignée
	 */
	private Sequence s2;
	
	/**
	 * 
	 */
	private Alignement aln1;
	
	/**
	 * 
	 */
	private Alignement aln2;
	
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
		this.mat = new double [seq1.longueur() + 1][seq2.longueur() + 1];
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
		int xlen = seq1.longueur();
		int ylen = seq2.longueur();
		
		for(col = 0; col <= ylen; col++) {                                          // on remplit la première ligne
			mat[0][col] = gap * col;
		}
		for(lig = 0; lig <= xlen; lig++) { 										 // on remplit la première colonne
			mat[lig][0] = gap * lig;
		}
		
		for (lig = 1; lig <= xlen; lig++) { 
			for (col = 1; col <= ylen; col++) {
				if (seq1.character(lig - 1) == seq2.character(col - 1)) {
					//if(s1.character(lig-1) == 'N') { // on a N en face de N et donc le score d'alignement est -1
					//	NO = mat[lig-1][col-1] - 1;
					//}else { // y'a pas de N
						NO = mat[lig - 1][col - 1] + match;
					//}
		        }else {
		        	//if(s1.character(lig-1) == 'N' || s2.character(col-1) == 'N') { // on a N en face d'un aa quelconque et donc le score d'alignement est -2
		        	//	NO = mat[lig-1][col-1] - 2;           
		        	//}
		        	NO = mat[lig - 1][col - 1] + mismatch;
		        }
		        g = mat[lig][col - 1] + gap;
		        h = mat[lig - 1][col] + gap;
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
			mat[0][col] = gap * col;
		}
		
		for(lig = 0; lig < xlen + 1; lig++) {
			mat[lig][0] = gap * lig;
		}
		
		for(lig = 1; lig < xlen + 1; lig++) {
			String c2 = seq1.character(lig - 1) + "";
			for(col = 1; col < ylen + 1; col++) {
				score = 0;
				String c1 = a1.colonne(col - 1);
				for(int i = 0; i < c1.length(); i++) {
					for(int j = 0; j < c2.length(); j++) {
						if(c1.charAt(i) == '-' || c2.charAt(j) == '-') {
							score = score + gap;
						}else {
							if(c1.charAt(i) == c2.charAt(j)) {
								score = score + match;
							}else {
								score = score + mismatch;
							}
						}
					}
				}
				score = score /(a1.longueur() * seq1.longueur());
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
			mat[0][col] = gap * col;
		}
		
		for(lig = 0; lig < xlen + 1; lig++) {
			mat[lig][0] = gap * lig;
		}
		
		for(lig = 1; lig < xlen + 1; lig++) {
			String c2 = a2.colonne(lig - 1);
			for(col = 1; col < ylen + 1; col++) {
				score = 0;
				String c1 = a1.colonne(col - 1);
				for(int i = 0; i < c1.length(); i++) {
					for(int j = 0; j < c2.length(); j++) {
						if(c1.charAt(i) == '-' || c2.charAt(j) == '-') {
							score = score + gap;
						}else {
							if(c1.charAt(i) == c2.charAt(j)) {
								score = score + match;
							}else {
								score = score + mismatch;
							}
						}
					}
				}
				score = score /(a1.longueur() * a2.longueur());
				mat[lig][col] = score;
			}
		}
	}
	
	
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
		int i = seq1.longueur();
		int j = seq2.longueur();
		while(i > 0 && j > 0) {
			//System.out.println(i + "et" +j);
			double score = mat[i][j];
			//double scoreH = mat[i][j - 1];
			double scoreG = mat[i - 1][j];
			double scoreNO;
			if (seq1.character(i - 1) == seq2.character(j - 1)) {
				/*if(s1.character(i-1) == 'N') {
					System.out.println("je rencontre N 2 fois");
					scoreNO = mat[i-1][j-1] - 1;
				}else {*/
					scoreNO = mat[i - 1][j - 1] + match;
				//}
			}else {
	        	/*if(s1.character(i-1) == 'N' || s2.character(j-1) == 'N') {
	        		System.out.println("je rencontre N 1 fois");
	        		scoreNO = mat[i-1][j-1] - 2;
	        	}else {*/
	        		scoreNO = mat[i - 1][j - 1] + mismatch;
	        	//}
			}
			if(score == scoreNO) {
				alignementSeq1 = seq1.character(i - 1) + alignementSeq1;
				alignementSeq2 = seq2.character(j - 1) + alignementSeq2;
				i--;
				j--;
				//System.out.println("je decremente i et j");
			}else {
				if(score == scoreG + gap) {
					alignementSeq1 = seq1.character(i - 1) + alignementSeq1;
					alignementSeq2 = '-' + alignementSeq2;
					//System.out.println("je decremente i");
					i--;
				}else{                                                         //(score == scoreH + gap)
					alignementSeq1 = '-' + alignementSeq1;
					alignementSeq2 = seq2.character(j - 1) + alignementSeq2;
					j--;
					//System.out.println("je decremente j");
				}
			}
		}
		while(i > 0) {
			alignementSeq1 = seq1.character(i - 1) + alignementSeq1;
			alignementSeq2 = '-' + alignementSeq2;
			i--;
		}
		
		while(j > 0) {
			alignementSeq1 = '-' + alignementSeq1;
			alignementSeq2 = seq2.character(j - 1) + alignementSeq2;
			j--;
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
			//System.out.println(i + " ====> " + j);
			//System.out.println(seq1.character(i - 1) + " ====> " + a1.colonne(j - 1));
			//double score = mat[i][j];
			//System.out.println(score);
			double scoreH = mat[i - 1][j] + gap;
			//System.out.println(scoreH);
			double scoreG = mat[i][j - 1] + gap;
			//System.out.println(scoreG);
			double scoreNO = mat[i - 1][j - 1];
			
			/*
			if (a1.colonne(j - 1).equals(a2.colonne(i - 1))) {
				scoreNO = scoreNO + match;
			} else {
	        	scoreNO = scoreNO + mismatch;
			}
			*/
		
			double max = Math.max(scoreNO, Math.max(scoreG, scoreH));
			
			//System.out.println(scoreNO);
			
			if(max == scoreNO) {
				count = a1.getListAlignementSeq().size();
				for (int l = 0; l < count; l++) {
					String strtmp = tmpListAln1.get(l);
					strtmp = a1.getListAlignementSeq().get(l).charAt(j - 1) + strtmp;
					tmpListAln1.remove(l);
					tmpListAln1.add(l, strtmp);
				}
				
				alignementSeq1 = seq1.character(i - 1) + alignementSeq1;
				
				i--;
				j--;
				//System.out.println("je decremente i et j");
			}else {
				if(max == scoreG){
					count = a1.getListAlignementSeq().size();
					for (int l = 0; l < count; l++) {
						String strtmp = tmpListAln1.get(l);
						strtmp = a1.getListAlignementSeq().get(l).charAt(j - 1) + strtmp;
						tmpListAln1.remove(l);
						tmpListAln1.add(l, strtmp);
					}
					
					alignementSeq1 = "-" + alignementSeq1;
					
					j--;
					//System.out.println("je decremente j");
				}else{                                                    //(max == scoreH)
					count = a1.getListAlignementSeq().size();
					for (int l = 0; l < count; l++) {
						String strtmp = tmpListAln1.get(l);
						strtmp = "-" + strtmp;
						tmpListAln1.remove(l);
						tmpListAln1.add(l, strtmp);
					}
					
					alignementSeq1 = seq1.character(i - 1) + alignementSeq1;
					
					//System.out.println("je decremente i");
					i--;
				}
			}
		}
		while(j > 0) {
			count = a1.getListAlignementSeq().size();
			for (int l = 0; l < count; l++) {
				String strtmp = tmpListAln1.get(l);
				strtmp = a1.getListAlignementSeq().get(l).charAt(j - 1) + strtmp;
				tmpListAln1.remove(l);
				tmpListAln1.add(l, strtmp);
			}
			
			alignementSeq1 = "-" + alignementSeq1;
			
			j--;
		}
		
		while(i > 0) {
			count = a1.getListAlignementSeq().size();
			for (int l = 0; l < count; l++) {
				String strtmp = tmpListAln1.get(l);
				strtmp = "-" + strtmp;
				tmpListAln1.remove(l);
				tmpListAln1.add(l, strtmp);
			}
			
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
		int count = a1.getListAlignementSeq().size();
		for (int l = 0; l < count; l++) {
			tmpListAln1.add("");
		}
		
		List<String> tmpListAln2 = new ArrayList<String>();
		count = a2.getListAlignementSeq().size();
		for (int l = 0; l < count; l++) {
			tmpListAln2.add("");
		}
		
		while(i > 0 && j > 0) {
			//System.out.println(i + " ====> " + j);
			//System.out.println(a2.colonne(i - 1) + " ====> " + a1.colonne(j - 1));
			//double score = mat[i][j];
			//System.out.println(score);
			double scoreH = mat[i - 1][j] + gap;
			//System.out.println(scoreH);
			double scoreG = mat[i][j - 1] + gap;
			//System.out.println(scoreG);
			double scoreNO = mat[i - 1][j - 1];
			
			/*
			if (a1.colonne(j - 1).equals(a2.colonne(i - 1))) {
				scoreNO = scoreNO + match;
			} else {
	        	scoreNO = scoreNO + mismatch;
			}
			*/
			
			double max = Math.max(scoreNO, Math.max(scoreG, scoreH));
			
			//System.out.println(scoreNO);
			
			if(max == scoreNO) {
				count = a1.getListAlignementSeq().size();
				for (int l = 0; l < count; l++) {
					String strtmp = tmpListAln1.get(l);
					strtmp = a1.getListAlignementSeq().get(l).charAt(j - 1) + strtmp;
					tmpListAln1.remove(l);
					tmpListAln1.add(l, strtmp);
				}
				
				count = a2.getListAlignementSeq().size();
				for (int l = 0; l < count; l++) {
					String strtmp = tmpListAln2.get(l);
					strtmp = a2.getListAlignementSeq().get(l).charAt(i - 1) + strtmp;
					tmpListAln2.remove(l);
					tmpListAln2.add(l, strtmp);
				}
				
				i--;
				j--;
				//System.out.println("je decremente i et j");
			}else {
				if(max == scoreG){
					count = a1.getListAlignementSeq().size();
					for (int l = 0; l < count; l++) {
						String strtmp = tmpListAln1.get(l);
						strtmp = a1.getListAlignementSeq().get(l).charAt(j - 1) + strtmp;
						tmpListAln1.remove(l);
						tmpListAln1.add(l, strtmp);
					}
					
					count = a2.getListAlignementSeq().size();
					for (int l = 0; l < count; l++) {
						String strtmp = tmpListAln2.get(l);
						strtmp = "-" + strtmp;
						tmpListAln2.remove(l);
						tmpListAln2.add(l, strtmp);
					}
					
					j--;
					//System.out.println("je decremente j");
				}else{                                                    //(max == scoreH)
					count = a1.getListAlignementSeq().size();
					for (int l = 0; l < count; l++) {
						String strtmp = tmpListAln1.get(l);
						strtmp = "-" + strtmp;
						tmpListAln1.remove(l);
						tmpListAln1.add(l, strtmp);
					}
					
					count = a2.getListAlignementSeq().size();
					for (int l = 0; l < count; l++) {
						String strtmp = tmpListAln2.get(l);
						strtmp = a2.getListAlignementSeq().get(l).charAt(i - 1) + strtmp;
						tmpListAln2.remove(l);
						tmpListAln2.add(l, strtmp);
					}
					
					//System.out.println("je decremente i");
					i--;
				}
			}
			//i = j = 0;
		}
		while(j > 0) {
			count = a1.getListAlignementSeq().size();
			for (int l = 0; l < count; l++) {
				String strtmp = tmpListAln1.get(l);
				strtmp = a1.getListAlignementSeq().get(l).charAt(j - 1) + strtmp;
				tmpListAln1.remove(l);
				tmpListAln1.add(l, strtmp);
			}
			
			count = a2.getListAlignementSeq().size();
			for (int l = 0; l < count; l++) {
				String strtmp = tmpListAln2.get(l);
				strtmp = "-" + strtmp;
				tmpListAln2.remove(l);
				tmpListAln2.add(l, strtmp);
			}
			
			j--;
		}
		
		while(i > 0) {
			count = a1.getListAlignementSeq().size();
			for (int l = 0; l < count; l++) {
				String strtmp = tmpListAln1.get(l);
				strtmp = "-" + strtmp;
				tmpListAln1.remove(l);
				tmpListAln1.add(l, strtmp);
			}
			
			count = a2.getListAlignementSeq().size();
			for (int l = 0; l < count; l++) {
				String strtmp = tmpListAln2.get(l);
				strtmp = a2.getListAlignementSeq().get(l).charAt(i - 1) + strtmp;
				tmpListAln2.remove(l);
				tmpListAln2.add(l, strtmp);
			}
			
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
		for (i = 0; i<lon; i++) {
			if(this.listAlignementSeq.get(0).charAt(i) == this.listAlignementSeq.get(1).charAt(i)) {
				/*if(alignementSeq1.charAt(i) == 'N') {
					score = score - 1;
				}else {*/
					score = score + 1; //match;
				//}
			}/*else {
				if(alignementSeq1.charAt(i) == '-' || alignementSeq2.charAt(i) == '-') {
					score = score + gap;
				}else {
					if(alignementSeq1.charAt(i) == 'N' || alignementSeq2.charAt(i) == 'N') {
						score = score - 2;
					}else {
						score = score + mismatch;
					}
				}
			}*/
		}
		score = ((score/lon) * 10000)/10000;
		return score;///alignementSeq1.length();
	}
	
	/**
	 * affiche le résultat de l'alignement
	 */
	public void afficher() {
		/*
		if (this.aln1 == null && this.aln2 == null) {
			System.out.println(this.s1.getID() + " " + this.listAlignementSeq.get(0) + " " + this.s1.longueur());
			System.out.println(this.s2.getID() + " " + this.listAlignementSeq.get(1) + " " + this.s2.longueur());
		} else {
			if (this.aln2 == null) {
				this.aln1.afficher();
				System.out.println(this.s1.getID() + " " + this.listAlignementSeq.get(listAlignementSeq.size() - 1) + " " + this.s1.longueur());
			} else {
				this.aln1.afficher();
				this.aln2.afficher();
			}
		}
		*/
		
		for(String str : this.listAlignementSeq) {
			System.out.println(str);
		}
	}
	
	/**
	 * 
	 * @return
	 */
	public int longueur() {
		return this.listAlignementSeq.get(0).length();
	}
	
	/**
	 * 
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
	 * 
	 * @return
	 */
	public double[][] getMat() {
		return mat;
	}
	
	/**
	 * 
	 * @return
	 */
	public Sequence getS1() {
		return s1;
	}
	
	/**
	 * 
	 * @return
	 */
	public Sequence getS2() {
		return s2;
	}
	
	/**
	 * 
	 * @return
	 */
	public Alignement getAln1() {
		return aln1;
	}
	
	/**
	 * 
	 * @return
	 */
	public Alignement getAln2() {
		return aln2;
	}
	
	/**
	 * 
	 * @return
	 */
	public List<String> getListAlignementSeq() {
		return listAlignementSeq;
	}
	
	/**
	 * 
	 * @param listAlignementSeq
	 */
	public void setListAlignementSeq(List<String> listAlignementSeq) {
		this.listAlignementSeq = listAlignementSeq;
	}
	
	/**
	 * 
	 * @return
	 */
	public int getMatch() {
		return match;
	}
	
	/**
	 * 
	 * @return
	 */
	public int getMismatch() {
		return mismatch;
	}
	
	/**
	 * 
	 * @return
	 */
	public int getGap() {
		return gap;
	}
}
