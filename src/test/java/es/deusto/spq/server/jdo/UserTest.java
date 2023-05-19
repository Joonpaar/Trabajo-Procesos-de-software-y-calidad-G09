package es.deusto.spq.server.jdo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.mockito.Mockito;
import org.mockito.MockedStatic;

import org.junit.Before;
import org.junit.Test;

import java.time.ZoneId;
import java.time.ZonedDateTime;

public class UserTest {
    
    private User user;
    private User user2;


    @Before
    public void setUp() {
        user = new User("test-login", "passwd");
        user2 = new User("usuario", "123", 0);
    }

    @Test
    public void testGetLogin() {
        assertEquals("test-login", user.getLogin());
    }

    @Test
    public void testGetPassword() {
        assertEquals("passwd", user.getPassword());
    }

    @Test
    public void testSetPassword() {
        user.setPassword("newpasswd");
        assertEquals("newpasswd", user.getPassword());
    }
    @Test
    public void testGetTipoUser() {
        assertEquals(0, user2.getTipoUser());
    }

    @Test
    public void testSetTipoUser() {
        user2.setTipoUser(1);
        assertEquals(1, user2.getTipoUser());
    }
    @Test
    public void testGetValoracion() {
        user2.setValoracion(1);
        assertEquals(1, user2.getValoracion());
    }
}
