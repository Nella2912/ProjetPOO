import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * lit les séquences dans un fichier teste, effectue l'alignement deux par deux  et construit une matrice des distances à partir des scores d'alignements.
 * @author Hornella Fosso-Kahina Lounaci
 *
 */
public class MatriceDistance {
	
	String N0mfichier;
	
	List<Sequence> ListeDesSequences;
	
	private int [][] distMat;
	
	
	/**
	 * 
	 * @param filename
	 */
	public MatriceDistance(String filename) {
		this.N0mfichier = filename;
		this.ListeDesSequences = new ArrayList<Sequence>();
	}
	
	
	
	/**
	 * remplie la liste des sequences à partir des éléments lus dans le fichier entré
	 * @throws Exception
	 */
	public void construireListeSeq() throws Exception {
	    BufferedReader lecteurAvecBuffer=null;
	    String ligne;
	    String idTmp = "";
	    try{
	    	lecteurAvecBuffer = new BufferedReader(new FileReader(N0mfichier));
	    	while ((ligne = lecteurAvecBuffer.readLine())!=null) {
	    		if(ligne.charAt(0) == '>') {
	    			idTmp = ligne.substring(1);
	    		}else {
	    			if(idTmp != "") {
		    			Sequence seq = new Sequence(idTmp, ligne);
		    			ListeDesSequences.add(seq);
		    			idTmp = "";
	    			}else {
	    				lecteurAvecBuffer.close();
	    				throw new Exception("identifiant introuvable dans le fichier");
	    			}
	    		}
	    	}
	  	    lecteurAvecBuffer.close();
	     }
	    catch(FileNotFoundException exc){
	    	throw exc;
	    }
	    catch(IOException exc){
	    	throw exc;
	    }
	    catch(Exception exc){
	    	throw exc;
	    }
	}
	
	
	public void distance() {
		int dim = ListeDesSequences.size();
		distMat = new int [dim][dim];
		int i;                                   //indice de ligne
		int j;                                   // indice de colonne
		int score;
		for(i=0; i<dim; i++) {
			Sequence seq1 = ListeDesSequences.get(i);          
			for(j=i; j<dim; j++) {
				//System.out.println(i + "et" + j);
				Sequence seq2 = ListeDesSequences.get(j);
				Alignement2seq alignement = new Alignement2seq(seq1, seq2);
				alignement.matriceDeScore();
				//System.out.println("mat de score calculée");
				alignement.aligner();
				//System.out.println("alignement fait");
				score = alignement.scoreAlignement();
				// on attribue à la ligne i et à la colone j le score. Et on attribut le même score à la ligne j et la colonne i
				distMat[i][j] = score; 
				distMat[j][i] = score;
			}
		}
	}
	
	/**
	 * restitue la matrice de distances
	 * @return matrice de distances
	 */
	public int[][] getDistMat() {
		return distMat;
	}
	
	public void afficheMat() {
		int dim = ListeDesSequences.size();
		//System.out.println(dim);
		int i;
		int j;
		for (i= 0; i<dim; i++) {
			for(j = 0; j<dim; j++) {
				System.out.print(distMat[i][j]);
			}
			System.out.println("");
		}
	}
	
}
