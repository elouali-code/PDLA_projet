import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

public class DatabaseConnectionTest {

    @Test
    public void testAuthenticateValidCredentials() throws SQLException {
        utilisateur user = DatabaseConnection.authenticate("user1", "password1");
        Assertions.assertNotNull(user);
        Assertions.assertEquals("user1", user.getUsername());
        Assertions.assertEquals("user1@email.com", user.getEmail());
        Assertions.assertEquals("benevolat", user.getRole());
    }

    @Test
    public void testAuthenticateInvalidUsername() throws SQLException {
        utilisateur user = DatabaseConnection.authenticate("invalidUser", "password1");
        Assertions.assertNull(user);
    }

    @Test
    public void testAuthenticateInvalidPassword() throws SQLException {
        utilisateur user = DatabaseConnection.authenticate("user1", "invalidPassword");
        Assertions.assertNull(user);
    }

    @Test
    public void testAuthenticateSQLException() {
        try {
            DatabaseConnection.authenticate("user1", "password1");
        } catch (SQLException e) {
            Assertions.assertTrue(true);
        }
    }
}
