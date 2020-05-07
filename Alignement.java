/**Modéliser  l'alignement globale de sequences ADN 
 * avec l'algorithme NEEDLMAN-WUNSH 
 * @author Hornella-Kahina
 *
 */

public class Alignement2seq implements Aligneur{
	/**
	 * Attributs
	 */
	
	private Sequence seq1;// premiere sequence
	private Sequence seq2;// deuxieme sequence
	
	private int[][] Mat; // matrice de programation dynamique!
	
	/**
	 * 	Pénalités fixées pour determiner le score de l'alignement
	 */
	public final static int match= 5;
	public final static int mismatch= -4;
	public final static int gap = -4;
	
	public Alignement2seq (Sequence seq1,Sequence seq2){
		this.seq1=seq1;
		this.seq2=seq2;
		this.Mat= new int [seq1.longueur()+1][seq2.longueur()+1];
		
	} 
	
	
	public void remplirMat(){
		int col;// index pour colonne
		int row;//index pour ligne
		int score1,score2,score3;
		int best;
		for(col=0;col<=seq1.longueur();col++) {
			Mat[0][col]=gap*col;
		}
		for(row=1;row<=seq2.longueur();row++){
			Mat[row][0]=gap*row;	
		}
		
		for(col=1;col<=seq1.longueur();col++) {}
			for(row=1;row<=seq2.longueur();row++){
				if (seq1.character(col)== seq2.character(row)){
					score1= Mat[col-1][row-1]+match;
				}
				else score1= Mat[col-1][row-1]+match;
				
				score2 =Mat[col][row-1]+gap;
				score3=Mat[row][col-1]+gap;
				best=score1;
				if(score2>score1) best=score2;
				if(score3>score1) best=score3;
				Mat[col][row]=best;
				
				
			}
	}
	
