package code;

import javax.print.DocFlavor.URL;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BenevolatApp {
    public static void main(String[] args) {
    	  JFrame frame = new JFrame("Inscription Utilisateur");
          frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
          frame.setSize(400, 300);

          java.net.URL imageUrl = BenevolatApp.class.getResource("6660-1024x640.jpg");
          if (imageUrl != null) {
              ImageIcon backgroundImage = new ImageIcon(imageUrl);

              JLabel backgroundLabel = new JLabel(backgroundImage);
              backgroundLabel.setBounds(0, 0, 2000, 00); 
              frame.getContentPane().add(backgroundLabel);
          } else {
              System.err.println("L'image n'a pas pu être chargée.");
          }

          frame.setVisible(true);

        JPanel panel = new JPanel();
        frame.add(panel);
        placeComponents(panel);

        frame.setVisible(true);
    }

    private static void placeComponents(JPanel panel) {
        panel.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(5, 5, 5, 5);

        JLabel nameLabel = new JLabel("Nom :");
        constraints.gridx = 0;
        constraints.gridy = 0;
        panel.add(nameLabel, constraints);

        JTextField nameText = new JTextField(20);
        constraints.gridx = 1;
        panel.add(nameText, constraints);

        JLabel emailLabel = new JLabel("E-mail :");
        constraints.gridx = 0;
        constraints.gridy = 1;
        panel.add(emailLabel, constraints);

        JTextField emailText = new JTextField(20);
        constraints.gridx = 1;
        panel.add(emailText, constraints);

        JLabel passwordLabel = new JLabel("Mot de passe :");
        constraints.gridx = 0;
        constraints.gridy = 2;
        panel.add(passwordLabel, constraints);

        JPasswordField passwordField = new JPasswordField(20);
        constraints.gridx = 1;
        panel.add(passwordField, constraints);

        JLabel confirmPasswordLabel = new JLabel("Confirmer le mot de passe :");
        constraints.gridx = 0;
        constraints.gridy = 3;
        panel.add(confirmPasswordLabel, constraints);

        JPasswordField confirmPasswordField = new JPasswordField(20);
        constraints.gridx = 1;
        panel.add(confirmPasswordField, constraints);

        JLabel roleLabel = new JLabel("Rôle :");
        constraints.gridx = 0;
        constraints.gridy = 4;
        panel.add(roleLabel, constraints);

        JRadioButton hospitaliseRadioButton = new JRadioButton("Hospitalisé");
        constraints.gridx = 1;
        constraints.gridy = 4;
        panel.add(hospitaliseRadioButton, constraints);

        JRadioButton benevoleRadioButton = new JRadioButton("Bénévole");
        constraints.gridy = 5;
        panel.add(benevoleRadioButton, constraints);

        ButtonGroup roleGroup = new ButtonGroup();
        roleGroup.add(hospitaliseRadioButton);
        roleGroup.add(benevoleRadioButton);

        JButton submitButton = new JButton("S'inscrire");
        constraints.gridx = 0;
        constraints.gridy = 6;
        constraints.gridwidth = 2;
        panel.add(submitButton, constraints);

        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String name = nameText.getText();
                String email = emailText.getText();
                String password = new String(passwordField.getPassword());
                String confirmPassword = new String(confirmPasswordField.getPassword());
                String role = hospitaliseRadioButton.isSelected() ? "Hospitalisé" : "Bénévole";

                if (password.equals(confirmPassword)) {
                    JOptionPane.showMessageDialog(null, "Utilisateur inscrit :\nNom : " + name + "\nE-mail : " + email + "\nRôle : " + role);
                } else {
                    JOptionPane.showMessageDialog(null, "Les mots de passe ne correspondent pas. Veuillez réessayer.");
                }
            }
        });
    }
}
