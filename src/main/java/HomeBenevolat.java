// HomeBenevolat.java


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HomeBenevolat extends JFrame {

    private static utilisateur utilisateur;

    private static final String DB_URL = "jdbc:mysql://srv-bdens.insa-toulouse.fr:3306/projet_gei_016";
    private static final String USER = "projet_gei_016";
    private static final String PASSWORD = "Fahd7ohb";

    public HomeBenevolat(utilisateur utilisateur) {
        this.utilisateur = utilisateur;

        setTitle("Accueil Benevolat");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Panel to display the list of requests for help
        JPanel demandePanel = new JPanel(new GridLayout(0, 1));
        demandePanel.setBorder(BorderFactory.createTitledBorder("Liste des Demandes d'Aide"));
        demandePanel.setBackground(new Color(200, 100, 0));  // Set background color to orange

        // Retrieve and display the list of requests from the database
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
            Class.forName("com.mysql.cj.jdbc.Driver");

            String query = "SELECT iddemande, typeAide, description, statut FROM demande";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    int demandeId = resultSet.getInt("iddemande");
                    String typeAide = resultSet.getString("typeAide");
                    String description = resultSet.getString("description");
                    String statut = resultSet.getString("statut");

                    JPanel requestPanel = new JPanel(new GridLayout(1, 4));
                    requestPanel.setBorder(BorderFactory.createLineBorder(Color.black));
                    requestPanel.setBackground(Color.white);  // Set background color to white

                    JLabel label = new JLabel("Type de la demande : " + typeAide + " Description : " + description + "\tStatut : " + statut);
                    requestPanel.add(label);

                    JButton acceptButton = new JButton("Accepter");
                    acceptButton.setBackground(new Color(255, 165, 0));  // Set background color to orange
                    acceptButton.setForeground(Color.white);  // Set text color to white
                    acceptButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            acceptRequest(demandeId);
                            updateStatutDemande(demandeId, "Acceptée");
                        }
                    });
                    requestPanel.add(acceptButton);

                    JButton rejectButton = new JButton("Rejeter");
                    rejectButton.setBackground(new Color(255, 165, 0));  // Set background color to orange
                    rejectButton.setForeground(Color.white);  // Set text color to white
                    rejectButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            rejectRequest(demandeId);
                            updateStatutDemande(demandeId, "Rejetée");
                        }
                    });
                    requestPanel.add(rejectButton);

                    demandePanel.add(requestPanel);
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        add(demandePanel);

        setVisible(true);
    }

    private void acceptRequest(int demandeId) {
        JOptionPane.showMessageDialog(this, "Demande de " + demandeId + " acceptée avec succès!");
    }

    private void rejectRequest(int demandeId) {
        JOptionPane.showMessageDialog(this, "Demande de " + demandeId + " rejetée avec succès!");
    }

    private void updateStatutDemande(int demandeId, String statut) {
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Requête SQL pour mettre à jour le statut de la demande
            String updateQuery = "UPDATE demande SET statut = ? WHERE iddemande = ?";
            try (PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
                updateStatement.setString(1, statut);
                updateStatement.setInt(2, demandeId);

                updateStatement.executeUpdate();
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    new HomeBenevolat(utilisateur);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
