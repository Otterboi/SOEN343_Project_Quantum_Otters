package Test;

import Backend.Users.Role;
import Backend.Users.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void constructorShouldSetDefaultRoleToStranger() {
        User user = new User();
        assertEquals(Role.STRANGER, user.getRole(), "Default role should be STRANGER");
    }

    @Test
    void constructorShouldSetRoleBasedOnStringInput() {
        User user = new User("John", "pass123", "admin");
        assertEquals(Role.ADMIN, user.getRole(), "Role should be set to ADMIN based on the constructor input");

        User userGuest = new User("Kareem", "pass456", "guest");
        assertEquals(Role.GUEST, userGuest.getRole(), "Role should be set to GUEST based on the constructor input");
    }

    @Test
    void setRoleShouldCorrectlyChangeUserRole() {
        User user = new User();
        user.setRole("child");
        assertEquals(Role.CHILD, user.getRole(), "Role should be set to CHILD");

        user.setRole("parent");
        assertEquals(Role.PARENT, user.getRole(), "Role should be set to PARENT");

        user.setRole("invalidRole");
        assertEquals(Role.STRANGER, user.getRole(), "Invalid role should default to STRANGER");
    }

    @Test
    void getRoleStringShouldReturnRoleInLowerCase() {
        User user = new User("John", "pass123", "admin");
        assertEquals("admin", user.getRoleString(), "getRoleString should return the role in lower case");
    }

    @Test
    void setNameAndPasswordShouldChangeUserCredentials() {
        User user = new User();
        user.setName("Ali");
        user.setPassword("password");

        assertEquals("Ali", user.getName(), "Name should be set to Ali");
        assertEquals("password", user.getPassword(), "Password should be set to password");
    }
}
