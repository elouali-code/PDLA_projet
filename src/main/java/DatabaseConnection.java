

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String DB_URL = "jdbc:mysql://srv-bdens.insa-toulouse.fr:3306/projet_gei_016";
    private static final String USER = "projet_gei_016";
    private static final String PASSWORD = "Fahd7ohb";

    // Méthode pour vérifier les identifiants dans la base de données
    public static utilisateur authenticate(String username, String password) {
        try {
            // Charger le pilote JDBC
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Établir la connexion à la base de données
            Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);

            // Requête SQL pour récupérer l'utilisateur avec le nom d'utilisateur et le mot de passe fournis
            String sql = "SELECT * FROM users2 WHERE username = ? AND password = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, username);
                statement.setString(2, password);

                // Exécuter la requête
                ResultSet resultSet = statement.executeQuery();

                // Si l'utilisateur est trouvé, l'authentification réussit
                if (resultSet.next()) {
                    // Récupérer les valeurs de la base de données
                    String email = resultSet.getString("email");
                    String role = resultSet.getString("role");
                    String age = resultSet.getString("age");


                    // Créer une instance de la classe utilisateur avec les valeurs récupérées
                    utilisateur usertest = new utilisateur(username, email, role,age);

                    // Fermer la connexion à la base de données
                    connection.close();

                    // Retourner l'instance de l'utilisateur
                    return usertest;
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        // En cas d'erreur, retourner null
        return null;
    }
    
    public static void addUtilisateur(utilisateur user) {
        try {
            // Charger le pilote JDBC
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Établir la connexion à la base de données
            Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);

            String sql = "INSERT INTO users2 (username, password, email, role, age) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, user.getNom());
                statement.setString(2, user.getPassword());
                statement.setString(3, user.getEmail());
                statement.setString(4, user.getRole());
                statement.setString(5, user.getage());

                statement.executeUpdate();
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } 
}
}
