import org.junit.Test;

import java.util.Scanner;

import static org.junit.Assert.assertNotNull;

public class TestAdd {

    @Test
    public void testAddUtilisateur() {        
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter username: ");
        String username = scanner.nextLine();

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        System.out.print("Enter email: ");
        String email = scanner.nextLine();

        System.out.print("Enter role (benevolat/validateur/hospitalise): ");
        String role = scanner.nextLine();

        System.out.print("Enter age: ");
        String age = scanner.nextLine();
       
        utilisateur newUser = new utilisateur(username, password, email, role, age);

        DatabaseConnection.addUtilisateur(newUser);

        utilisateur retrievedUser = DatabaseConnection.authenticate(username, password);

        assertNotNull("User should be retrieved from the database", retrievedUser);
    }
}
