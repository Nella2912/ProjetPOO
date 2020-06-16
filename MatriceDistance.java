/**
 * lit les séquences dans un fichier teste, effectue l'alignement deux par deux  et construit une matrice des distances à partir des scores d'alignements.
 * @author Hornella Fosso-Kahina Lounaci
 *
 */

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MatriceDistance {
	/**
	 * nom du fichier fasta sur lequel se fera le traitement
	 */
	private String nomfichier;
	
	/**
	 * Liste contenant la liste des séquences
	 */
	private List<Sequence> listeDesSequences;
	
	/**
	 * matrice des distances
	 */
	private double [][] distMat;
	
	/**
	 * Listes d'entiers permettant d'avoir les index de la précédentes matrices des distances (utile lors du calcul de la nouvelle matrice des distances)
	 */
	private List<Integer> listAncienIndexMatDist;
	
	/**
	 * instancie un objet Matrice Distance et initialise les attrinuts
	 * @param filename  fichier fasta à traiter
	 */
	public MatriceDistance(String filename) {
		this.nomfichier = filename;
		this.listeDesSequences = new ArrayList<Sequence>();
		this.listAncienIndexMatDist = new ArrayList<Integer>();
	}
	
	/**
	 * fonction pour l'initialisation de la matrice des distances
	 * @throws Exception
	 */
	public void initMatriceDistance() throws Exception{
		try {
			this.construireListeSeq();
			this.distance();
		}
		catch(Exception e) {
			throw e;
		}
	}
	
	/**
	 * remplie la liste des sequences à partir des éléments lus dans le fichier entré
	 * @throws Exception
	 */
	private void construireListeSeq() throws Exception {
	    BufferedReader lecteurAvecBuffer=null;
	    String ligne;
	    String idTmp = "";
	    int index = 0;
	    try{
	    	lecteurAvecBuffer = new BufferedReader(new FileReader(nomfichier));
	    	while ((ligne = lecteurAvecBuffer.readLine())!=null) {
	    		if(ligne.charAt(0) == '>') {
	    			idTmp = ligne.substring(1);
	    		}else {
	    			if(idTmp != "") {
		    			Sequence seq = new Sequence(idTmp, ligne);
		    			listeDesSequences.add(seq);
		    			listAncienIndexMatDist.add(index);
		    			index++;
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
	
	/**
	 * initialisation de la matrice des distances
	 */
	private void distance() {
		int dim = listeDesSequences.size();
		distMat = new double [dim][dim];
		int i;                                   // indice de ligne
		int j;                                   // indice de colonne
		double dis;
		for(i = 0; i < dim; i++) {
			Sequence seq1 = listeDesSequences.get(i);          
			for(j = i; j < dim; j++) {
				Sequence seq2 = listeDesSequences.get(j);				
				Alignement align = new Alignement(seq1, seq2);
				align.aligner();
				dis = 1 - align.scoreAlignement();
				
				// on attribue à la ligne i et à la colone j le score. Et on attribut le même score à la ligne j et la colonne i
				distMat[i][j] = dis; 
				distMat[j][i] = dis;
			}
		}
	}
	
	/**
	 * restitue la matrice de distances
	 * @return matrice de distances
	 */
	public double[][] getDistMat() {
		return distMat;
	}
	
	/**
	 * écrase la valeur de la matrice des distances
	 * @param distMat
	 */
	public void setDistMat(double[][] distMat) {
		this.distMat = distMat;
	}
	
	/**
	 * restitue la liste des sequences
	 * @return
	 */
	public List<Sequence> getListeDesSequences() {
		return listeDesSequences;
	}
	
	/**
	 * assigne la liste contenant les indexs de la précédente matrice des distances
	 * @param ancienIndexMatDist
	 */
	public void setListAncienIndexMatDist(List<Integer> listAncienIndexMatDist) {
		this.listAncienIndexMatDist = listAncienIndexMatDist;
	}
	
	/**
	 * restitue la liste contenant les indexs de la précédente matrice des distances
	 * @return
	 */
	public List<Integer> getListAncienIndexMatDist() {
		return listAncienIndexMatDist;
	}
}
