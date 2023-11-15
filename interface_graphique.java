package code;



import javax.swing.*;
import java.awt.*;

public class interface_graphique {

    public static void main(String[] args) 
    {
        JFrame frame = new JFrame("Hello World");

        String[] liste = {"contact", "abde", "fu", "oussama", "boyu"};

        JComboBox cb = new JComboBox(liste);
    
        frame.setLayout(new GridLayout(6, 1));
        
        frame.add(cb);

        frame.pack();
        frame.setSize(250, 250);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
