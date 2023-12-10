// HomePage.java


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Homehospitalise extends JFrame {

    private static utilisateur utilisateur;

    private static final String DB_URL = "jdbc:mysql://srv-bdens.insa-toulouse.fr:3306/projet_gei_016";
    private static final String USER = "projet_gei_016";
    private static final String PASSWORD = "Fahd7ohb";

    private JPanel profilPanel;
    private JPanel demandePanel;

    public Homehospitalise(utilisateur utilisateur) {
        this.utilisateur = utilisateur;

        setTitle("Accueil");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        profilPanel = new JPanel(new GridLayout(3, 2));
        profilPanel.setBorder(BorderFactory.createTitledBorder("Informations du Profil"));
        profilPanel.setBackground(new Color(100, 100, 0));

        profilPanel.add(new JLabel("Nom: " + utilisateur.getNom()));
        profilPanel.add(new JLabel("Email: " + utilisateur.getEmail()));
        profilPanel.add(new JLabel("Rôle: " + utilisateur.getRole()));

        demandePanel = new JPanel(new GridLayout(0, 1));
        demandePanel.setBorder(BorderFactory.createTitledBorder("Mes Demandes"));
        demandePanel.setBackground(Color.white);

        add(profilPanel);
        add(demandePanel);

        JButton ajouterDemandeButton = new JButton("Ajouter une Demande");
        ajouterDemandeButton.setBackground(new Color(255, 165, 0));
        ajouterDemandeButton.setForeground(Color.white);
        ajouterDemandeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String typeAide = JOptionPane.showInputDialog("Type de l'aide:");
                String description = JOptionPane.showInputDialog("Description de la demande:");

                ajouterDemande(utilisateur.getNom(), typeAide, description);
                updateDemandePanel();

                JOptionPane.showMessageDialog(Homehospitalise.this, "Demande ajoutée avec succès!");
            }
        });

        add(ajouterDemandeButton);

        updateDemandePanel();

        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        setVisible(true);
    }

    private void ajouterDemande(String demandeur, String typeAide, String description) {
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
            Class.forName("com.mysql.cj.jdbc.Driver");

            String query = "INSERT INTO demande (typeaide, description, demandeur) VALUES (?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, typeAide);
                preparedStatement.setString(2, description);
                preparedStatement.setString(3, demandeur);

                preparedStatement.executeUpdate();
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void updateDemandePanel() {
        demandePanel.removeAll();

        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
            Class.forName("com.mysql.cj.jdbc.Driver");

            String query = "SELECT typeaide, statut, motif FROM demande WHERE demandeur = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, utilisateur.getNom());
                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    String typeAide = resultSet.getString("typeaide");
                    String statut = resultSet.getString("statut");
                    String motif = resultSet.getString("motif");

                    JPanel demandeInfoPanel = new JPanel(new GridLayout(1, 3));
                    demandeInfoPanel.setBorder(BorderFactory.createLineBorder(Color.black));
                    demandeInfoPanel.setBackground(Color.white);

                    JLabel typeAideLabel = new JLabel("Type d'aide: " + typeAide);
                    demandeInfoPanel.add(typeAideLabel);

                    JLabel statutLabel = new JLabel("Statut: " + (statut != null ? statut : "En attente"));
                    demandeInfoPanel.add(statutLabel);

                    if ("refusee".equals(statut)) {
                        JLabel motifLabel = new JLabel("Motif du refus: " + motif);
                        demandeInfoPanel.add(motifLabel);
                    }

                    demandePanel.add(demandeInfoPanel);
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        validate();
        repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    new Homehospitalise(utilisateur);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}