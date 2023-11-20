package code;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class HomePage extends JFrame {

    private static utilisateur utilisateur;

    private static final String DB_URL = "jdbc:mysql://srv-bdens.insa-toulouse.fr:3306/projet_gei_016";
    private static final String USER = "projet_gei_016";
    private static final String PASSWORD = "Fahd7ohb";

    @SuppressWarnings("static-access")
    public HomePage(utilisateur utilisateur) {
        this.utilisateur = utilisateur;

        setTitle("Accueil");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Panel pour afficher les informations du profil
        JPanel profilPanel = new JPanel(new GridLayout(3, 2));
        profilPanel.setBorder(BorderFactory.createTitledBorder("Informations du Profil"));
        profilPanel.setBackground(new Color(255, 165, 0)); // Couleur orange

        profilPanel.add(new JLabel("Nom: "));
        profilPanel.add(new JLabel(utilisateur.getNom()));

        profilPanel.add(new JLabel("Email: "));
        profilPanel.add(new JLabel(utilisateur.getEmail()));

        profilPanel.add(new JLabel("Rôle: "));
        profilPanel.add(new JLabel(utilisateur.getRole()));

        // Panel pour ajouter une demande d'aide
        JPanel aidePanel = new JPanel(new GridLayout(3, 2));
        aidePanel.setBorder(BorderFactory.createTitledBorder("Ajouter une Demande d'Aide"));
        aidePanel.setBackground(Color.white);

        aidePanel.add(new JLabel("Type d'aide: "));
        JTextField typeAideField = new JTextField();
        aidePanel.add(typeAideField);

        aidePanel.add(new JLabel("Description: "));
        JTextField descriptionField = new JTextField();
        aidePanel.add(descriptionField);

        JButton ajouterAideButton = new JButton("Ajouter l'Aide");
        ajouterAideButton.setBackground(new Color(255, 165, 0)); // Couleur orange
        ajouterAideButton.setForeground(Color.white);
        ajouterAideButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String typeAide = typeAideField.getText();
                String description = descriptionField.getText();
                String demandeur = utilisateur.getNom();

                JOptionPane.showMessageDialog(HomePage.this, "Aide ajoutée:\nType: " + typeAide + "\nDescription: " + description);

                try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
                    Class.forName("com.mysql.cj.jdbc.Driver");

                    String query = "INSERT INTO demande (typeaide , description, demandeur) VALUES (?, ?, ?)";
                    try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                        preparedStatement.setString(1, typeAide);
                        preparedStatement.setString(2, description);
                        preparedStatement.setString(3, demandeur);

                        preparedStatement.executeUpdate();
                    }
                } catch (SQLException | ClassNotFoundException e1) {
                    e1.printStackTrace();
                }
            }
        });
        aidePanel.add(ajouterAideButton);

        setLayout(new GridLayout(2, 1));
        add(profilPanel);
        add(aidePanel);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    new HomePage(utilisateur);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
