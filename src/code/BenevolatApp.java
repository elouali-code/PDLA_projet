package code;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BenevolatApp {

    public static void main(String[] args) {
        JFrame frame = new JFrame("Authentification Utilisateur");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);

        JPanel panel = new JPanel();
        panel.setBackground(new Color(255, 165, 0)); // Fond orange
        frame.add(panel);
        placeComponents(panel, frame);

        frame.setVisible(true);
    }

    private static void placeComponents(JPanel panel, JFrame frame) {
        panel.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(10, 10, 10, 10);

        JLabel nameLabel = new JLabel("Nom d'utilisateur:");
        constraints.gridx = 0;
        constraints.gridy = 0;
        nameLabel.setForeground(Color.white);
        panel.add(nameLabel, constraints);

        JTextField nameText = new JTextField(20);
        constraints.gridx = 1;
        panel.add(nameText, constraints);

        JLabel passwordLabel = new JLabel("Mot de passe:");
        constraints.gridx = 0;
        constraints.gridy = 1;
        passwordLabel.setForeground(Color.white);
        panel.add(passwordLabel, constraints);

        JPasswordField passwordField = new JPasswordField(20);
        constraints.gridx = 1;
        panel.add(passwordField, constraints);

        JButton loginButton = new JButton("Se connecter");
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 2;
        loginButton.setBackground(Color.white);
        loginButton.setForeground(new Color(255, 165, 0)); // Texte orange
        panel.add(loginButton, constraints);

        // Style du bouton
        loginButton.setBorderPainted(false);
        loginButton.setFocusPainted(false);
        loginButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = nameText.getText();
                String password = new String(passwordField.getPassword());

                // Vérifier l'authentification avec la base de données
                if (DatabaseConnection.authenticate(username, password)) {
                    JOptionPane.showMessageDialog(null, "Authentification réussie !");
                    // Masquer la fenêtre actuelle
                    frame.setVisible(false);
                    // Afficher la nouvelle page d'accueil
                    utilisateur usertest = new utilisateur(username, "", "", "", 0);
                    HomePage homePage = new HomePage(usertest);
                    homePage.show();
                } else {
                    JOptionPane.showMessageDialog(null, "Authentification échouée. Veuillez réessayer.");
                }
            }
        });
    }
}
