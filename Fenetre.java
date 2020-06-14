/**
 * Initialise la fenêtre du résultat final
 * @author Hornella Fosso - Kahina Lounaci
 *
 */

import javax.swing.*;
import java.awt.*;

@SuppressWarnings("serial")
public class Fenetre extends JFrame {
    
	/**
	 * construction de la fenêtre du résultat
	 * @param al
	 */
	public Fenetre(Alignement al){
    	Dimension size = new Dimension();
        int width = al.getListAlignementSeq().get(0).length() * 16;                 //On identifie la taille de la fenêtre à l'affichage
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
        this.setResizable(true);                            //On peut redimensionner la fenêtre
        if (width < 300) {
	        size.setSize(width, height);
	        
        } else {
        	size.setSize(300, height);
        }
        this.setMinimumSize(size);                          //On identifie la taille minimale de la fenêtre
        
        JPanel panel = new JPanel();                           //Instanciation d'un objet JPanel
        JScrollPane scroll = new JScrollPane(panel);             //Instanciation d'un objet JScrollPanel pour qu'il y ait des scrolls dans la fenêtre
        scroll.setViewportView(panel);                           //Ajout du JPanel au JScrollPanel
        this.setContentPane(scroll);
        
        int index = 1;
        String lblText;
        JLabel label;
        for (String elt : al.getListAlignementSeq()) {                   //Pour chaque caractère du résultat du dernier alignement
            for (int i = 0; i < elt.length(); i++){                      // - pour chaque caractère, on affiche dans la couleur correspondante
                lblText = elt.charAt(i) + "";
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
                panel.add(label);
            }
            panel.setLayout(new GridLayout(index, 0));
            index++;
        }

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    public void afficher() {
    	this.setVisible(true);
    }
}
