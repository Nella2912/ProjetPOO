/**
 * Initialise la fenêtre du résultat final
 * @author Hornella Fosso - Kahina Lounaci
 *
 */

import javax.swing.*;
import java.awt.*;
import java.util.List;

@SuppressWarnings("serial")
public class Fenetre extends JFrame {
    
	/**
	 * construction de la fenêtre du résultat
	 * @param al
	 */
	public Fenetre(Alignement al){
    	Dimension size = new Dimension();
        int width = al.getListAlignementSeq().get(0).length() * 16;                 //Calcul de la taille de la fenêtre à l'affichage
        int height = al.getListAlignementSeq().size() * 23;
        if (width > 1500) {
        	width = 1500;
        }
        if (height > 500) {
        	height = 500;
        }
    	size.setSize(width, height);
    	this.setSize(size);
    	
        this.setTitle("Résultat des alignements");                                 //Titre de la fenêtre
        this.setLocationRelativeTo(null);                                          
        this.setResizable(true);                            //Paramètre permettant de laisser la fenêtre redimensionnable
        if (width < 300) {
	        size.setSize(width, height);
	        
        } else {
        	size.setSize(300, height);
        }
        this.setMinimumSize(size);                          //Assignation de la taille minimale de la fenêtre
        
        JPanel panel = new JPanel();                           //Instanciation d'un objet JPanel
        JPanel panel1 = new JPanel();
        JPanel panel2 = new JPanel();
        JPanel panel3 = new JPanel();
        
        JScrollPane scroll = new JScrollPane(panel);             //Instanciation d'un objet JScrollPane pour qu'il y ait des scrolls dans la fenêtre
        
        panel.add(panel1);
        panel.add(panel2);
        panel.add(panel3);
        
        panel1.setAlignmentX(LEFT_ALIGNMENT);
        panel2.setAlignmentX(LEFT_ALIGNMENT);
        panel3.setAlignmentX(LEFT_ALIGNMENT);
        
        scroll.setViewportView(panel);                           //Ajout du JPanel au JScrollPane
        this.setContentPane(scroll);
        
        
        int index = 1;
        String lblText;
        JLabel label;
        
        List<String> lSeq = al.getListAlignementSeq();
        List<String> lIdEtLongSeq = al.getListIdEtLongueurSeq();
        
        for (int i = 0; i < lSeq.size(); i++ ) {                   //Pour chaque caractère du résultat du dernier alignement
        	String elt = lSeq.get(i);
        	String[] tabIdLong = lIdEtLongSeq.get(i).split("##");
        	label = new JLabel(tabIdLong[0]);
        	panel1.add(label);
            for (int j = 0; j < elt.length(); j++){                      // - on affiche dans la couleur correspondante en utilisant un JLabel
                lblText = elt.charAt(j) + "";
                label = new JLabel(lblText);
                label.setOpaque(true);
                switch (lblText.toUpperCase()) {
                    case "A":
                        label.setBackground(Color.yellow);
                        break;
                    case "C":
                        label.setBackground(Color.green);
                        break;
                    case "G":
                        label.setBackground(Color.magenta);
                        break;
                    case "T":
                        label.setBackground(Color.orange);
                        break;
                    default:
                        label.setBackground(Color.white);
                }
                panel2.add(label);
            }
            
            label = new JLabel(tabIdLong[1]);
        	panel3.add(label);
        	
        	panel1.setLayout(new GridLayout(index, 0));
            panel2.setLayout(new GridLayout(index, 0));
            panel3.setLayout(new GridLayout(index, 0));
            
            index++;
        }

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
	/**
	 * permet de rendre visible la fênetre
	 */
    public void afficher() {
    	this.setVisible(true);
    }
}
