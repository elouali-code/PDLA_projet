import org.junit.Test;

import javax.swing.*;

public class TestAdd {

    @Test
    public void testSignInButton() {
        SwingUtilities.invokeLater(() -> createAndShowGUI());
    }

    private static void createAndShowGUI() {
        String username = JOptionPane.showInputDialog("Enter username:");
        String password = JOptionPane.showInputDialog("Enter password:");
        String email = JOptionPane.showInputDialog("Enter email:");

        String[] roles = {"benevolat", "validateur", "hospitalise"};
        String role = (String) JOptionPane.showInputDialog(null, "Choose a role:", "Select Role",
                JOptionPane.QUESTION_MESSAGE, null, roles, roles[0]);

        String age = JOptionPane.showInputDialog("Enter age:");

        utilisateur newUser = new utilisateur(username, password, email, role, age);

        DatabaseConnection.addUtilisateur(newUser);

        JOptionPane.showMessageDialog(null, "User added to the database!");
    }
}
