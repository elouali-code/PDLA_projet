package code;

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
    public static boolean authenticate(String username, String password) {
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
                return resultSet.next();
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        // En cas d'erreur, retourner false
        return false;
    }
}
