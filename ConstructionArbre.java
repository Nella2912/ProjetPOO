/**
 * modelise un arbre avec la méthode UPGMA
 * @author Hornella Fosso-Kahina Lounaci
 *
 */
public class ConstructionArbre {
	double [][] mD;               // une matrice de distances prise en paramètre
	
	public ConstructionArbre(/*double matDeDist [][]*/) {
		//this.mD = matDeDist;
	}
	
	// prend en argument la matrice  de distance et retourne le couple de sequence
	
	public String DistMin(double matDeDist[][] ) throws Exception {
		
		
		MatriceDistance D1= new MatriceDistance("ADN16S.fasta");
		D1.construireListeSeq();
		D1.distance();
		
		matDeDist=D1.getDistMat();
		int ligmin=0;
		int colmin = 1;
		double  min = matDeDist[ligmin][colmin];
		int longueur=D1.ListeDesSequences.size();
		
		Sequence seqmin1;
		Sequence seqmin2;
		
		
		for( int lig=0;lig < longueur;lig++){
			for( int col=lig+1;col < longueur;col++){ // ici faut comprendre comment la matice de score est construite
				if (min >matDeDist[lig][col]){
					min = matDeDist[lig][col];
					ligmin=lig;
					colmin=col;
					
				}
					
			}
			
		}
		seqmin1=D1.ListeDesSequences.get(ligmin);
		seqmin2=D1.ListeDesSequences.get(colmin);
		
		
		return seqmin1.getID()+seqmin2.getID();
		//return min;
	}	
	
	// mettre a jour la matrice de distance 
	
	public void Matrice Ajour(ligmin,colmin,matdist,A){
		
	}
	
}
