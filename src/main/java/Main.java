// Benevolat.java

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    createAndShowGUI();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Authentification Utilisateur");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);

        JPanel panel = new JPanel();
        panel.setBackground(new Color(200, 200, 0)); // Fond orange
        frame.add(panel);

        placeComponents(panel, frame);

        frame.setVisible(true);
    }

    private static void placeComponents(JPanel panel, final JFrame frame) {
        panel.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(10, 10, 10, 10);

        JLabel nameLabel = new JLabel("Nom d'utilisateur:");
        constraints.gridx = 0;
        constraints.gridy = 0;
        nameLabel.setForeground(Color.white);
        panel.add(nameLabel, constraints);

        final JTextField nameText = new JTextField(20);
        constraints.gridx = 1;
        panel.add(nameText, constraints);

        JLabel passwordLabel = new JLabel("Mot de passe:");
        constraints.gridx = 0;
        constraints.gridy = 1;
        passwordLabel.setForeground(Color.white);
        panel.add(passwordLabel, constraints);

        final JPasswordField passwordField = new JPasswordField(20);
        constraints.gridx = 1;
        panel.add(passwordField, constraints);

        JButton loginButton = new JButton("Se connecter");
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 2;
        loginButton.setBackground(Color.white);
        loginButton.setForeground(new Color(255, 165, 0)); // Texte orange
        panel.add(loginButton, constraints);

        JButton inscribeButton = new JButton("S'inscrire"); // inscription
        constraints.gridy = 3;
        panel.add(inscribeButton, constraints);

        // Style du bouton
        loginButton.setBorderPainted(false);
        loginButton.setFocusPainted(false);
        loginButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = nameText.getText();
                String password = new String(passwordField.getPassword());

                // Vérifier l'authentification avec la base de données
                utilisateur usertest = DatabaseConnection.authenticate(username, password);

                if (usertest != null) {
                    JOptionPane.showMessageDialog(null, "Authentification réussie!");

                    // Masquer la fenêtre actuelle
                    frame.setVisible(false);

                    // Vérifier le rôle de l'utilisateur
                    if (usertest.getRole().equals("benevolat")) {
                        // Afficher la page HomePage2 pour les bénévoles
                        HomeBenevolat homeBenevolat = new HomeBenevolat(usertest);
                        homeBenevolat.show();
                    } else if (usertest.getRole().equals("validateur")) {
                        // Afficher la page HomeValidateur pour les validateurs
                        HomeValidateur homeValidateur = new HomeValidateur(usertest);
                    } else if (usertest.getRole().equals("hospitalise")) {
                        // Afficher la page HomePage pour les hospitaliers
                        Homehospitalise homehospitalise = new Homehospitalise(usertest);
                    } else {
                        JOptionPane.showMessageDialog(null, "Rôle non reconnu. Veuillez réessayer.");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Authentification échouée. Veuillez réessayer.");
                }
            }
        });
        
    inscribeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //inscription
                testSignInButton(frame);
            }
        });
    }

    private static void testSignInButton(JFrame frame) {
    	int id = Integer.parseInt(JOptionPane.showInputDialog("Enter ID(3 numbers):"));
        String username = JOptionPane.showInputDialog("Enter username:");
        String password = JOptionPane.showInputDialog("Enter password:");
        String email = JOptionPane.showInputDialog("Enter email:");

        String[] roles = {"benevolat", "validateur", "hospitalise"};
        String role = (String) JOptionPane.showInputDialog(null, "Choose a role:", "Select Role",
                JOptionPane.QUESTION_MESSAGE, null, roles, roles[0]);

        String age = JOptionPane.showInputDialog("Enter age:");

        utilisateur newUser = new utilisateur(username, password, email, role, age, id);

        DatabaseConnection.addUtilisateur(newUser);
    }
}
