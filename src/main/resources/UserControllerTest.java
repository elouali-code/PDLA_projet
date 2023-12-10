import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class UserControllerTest {

    @Test
    public void testGetAllUsers() {
        List<utilisateur> users = UserController.getAllUsers();
        Assertions.assertNotNull(users);
        Assertions.assertEquals(10, users.size());
    }

    @Test
    public void testGetUserById() {
        utilisateur user = UserController.getUserById(1);
        Assertions.assertNotNull(user);
        Assertions.assertEquals("user1", user.getUsername());
    }

    @Test
    public void testCreateUser() {
        utilisateur user = new utilisateur();
        user.setUsername("newUser");
        user.setEmail("newUser@email.com");
        user.setRole("benevolat");

        UserController.createUser(user);

        List<utilisateur> users = UserController.getAllUsers();
        Assertions.assertEquals(11, users.size());
        Assertions.assertTrue(users.contains(user));
    }

    @Test
    public void testUpdateUser() {
        utilisateur user = UserController.getUserById(1);
        user.setUsername("updatedUser");

        UserController.updateUser(user);

        utilisateur updatedUser = UserController.getUserById(1);
        Assertions.assertEquals("updatedUser", updatedUser.getUsername());
    }

    @Test
    public void testDeleteUser() {
        UserController.deleteUser(1);

        List<utilisateur> users = UserController.getAllUsers();
        Assertions.assertEquals(9, users.size());
        Assertions.assertFalse(users.contains(UserController.getUserById(1)));
    }
}
