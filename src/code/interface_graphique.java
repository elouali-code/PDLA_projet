package code;



import javax.swing.*;
import java.awt.*;

public class interface_graphique {

    public static void main(String[] args) 
    {
        // Définissez le frame
        JFrame frame = new JFrame("Hello World");

        // Set data in the drop-down list
        String[] liste = {"contact", "abde", "fu", "oussama", "boyu"};

        // Créer une liste déroulante
        JComboBox cb = new JComboBox(liste);
    
        frame.setLayout(new GridLayout(6, 1));
        
        frame.add(cb);

        frame.pack();
        frame.setSize(250, 250);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}