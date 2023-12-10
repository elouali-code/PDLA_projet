
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class HomeValidateur extends JFrame {

    private static utilisateur utilisateur;

    private static final String DB_URL = "jdbc:mysql://srv-bdens.insa-toulouse.fr:3306/projet_gei_016";
    private static final String USER = "projet_gei_016";
    private static final String PASSWORD = "Fahd7ohb";

    public HomeValidateur(utilisateur utilisateur) {
        this.utilisateur = utilisateur;

        setTitle("Accueil Validateur");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Panel pour afficher la liste des demandes
        JPanel demandePanel = new JPanel(new GridLayout(0, 1));
        demandePanel.setBorder(BorderFactory.createTitledBorder("Liste des Demandes d'Aide"));
       // demandePanel.setBackground(new Color(500, 500, 0));  // Fond orange

        // Récupérer et afficher la liste des demandes depuis la base de données
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
            Class.forName("com.mysql.cj.jdbc.Driver");

            String query = "SELECT iddemande, typeAide, description, demandeur, statut, motif FROM demande";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    int demandeId = resultSet.getInt("iddemande");
                    String typeAide = resultSet.getString("typeaide");
                    String description = resultSet.getString("description");
                    String demandeur = resultSet.getString("demandeur");
                    String statut = resultSet.getString("statut");
                    String motif = resultSet.getString("motif");

                    JPanel requestPanel = new JPanel(new GridLayout(1, 4));
                    requestPanel.setBorder(BorderFactory.createLineBorder(Color.black));
                    requestPanel.setBackground(Color.white);

                    JLabel label = new JLabel("Type: " + typeAide + ", Description: " + description + " Demandeur: " + demandeur );
                    requestPanel.add(label);

                    JButton traiterButton = new JButton("Traiter");
                    traiterButton.setBackground(new Color(255, 165, 0));  // Fond orange
                    traiterButton.setForeground(Color.white);  // Texte blanc
                    traiterButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            handleTraitement(demandeId, statut, motif);
                            validate();
                            repaint();
                        }
                    });
                    requestPanel.add(traiterButton);

                    demandePanel.add(requestPanel);
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        add(demandePanel);

        setVisible(true);
    }

    private void handleTraitement(int demandeId, String statut, String motif) {
        if (statutDejaTraite(statut)) {
            JOptionPane.showMessageDialog(this, "Cette demande a déjà été traitée.");
            return;
        }

        // Créer les boutons "Valider" et "Refuser"
        JButton validerButton = new JButton("Valider");
        validerButton.setBackground(new Color(255, 165, 0));  // Fond orange
        validerButton.setForeground(Color.white);  // Texte blanc

        JButton refuserButton = new JButton("Refuser");
        refuserButton.setBackground(new Color(255, 165, 0));  // Fond orange
        refuserButton.setForeground(Color.white);  // Texte blanc

        final String finalStatut = statut;
        final String finalMotif = motif;

        // Ajouter des actions aux boutons
        validerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String statut = "validé";
                String motif = null;  // Réinitialiser le motif
                updateRequestStatus(demandeId, statut, motif);
                JOptionPane.showMessageDialog(HomeValidateur.this, "Demande validée avec succès!");
            }
        });

        refuserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String statut = "refusé";
                String motif = JOptionPane.showInputDialog(HomeValidateur.this, "Veuillez fournir un motif (facultatif) :");
                updateRequestStatus(demandeId, statut, motif);
                JOptionPane.showMessageDialog(HomeValidateur.this, "Demande refusée avec succès!");
            }
        });

        // Créer une boîte de dialogue pour afficher les boutons
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.add(validerButton);
        buttonsPanel.add(refuserButton);

        JOptionPane.showOptionDialog(this, buttonsPanel, "Traitement de la demande", JOptionPane.NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, new Object[]{}, null);
    }

    private void updateRequestStatus(int demandeId, String statut, String motif) {
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
            Class.forName("com.mysql.cj.jdbc.Driver");

            String query = "UPDATE demande SET statut = ?, motif = ? WHERE iddemande = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, statut);
                preparedStatement.setString(2, motif);
                preparedStatement.setInt(3, demandeId);

                preparedStatement.executeUpdate();
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private boolean statutDejaTraite(String statut) {
        return statut != null && (statut.equals("validé") || statut.equals("refusé"));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                new HomeValidateur(utilisateur);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
