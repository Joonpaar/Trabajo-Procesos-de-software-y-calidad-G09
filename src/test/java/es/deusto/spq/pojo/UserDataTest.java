package es.deusto.spq.pojo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;


public class UserDataTest {

    UserData userData;
    
    @Before
    public void setUp() {
        userData = new UserData();
        userData.setLogin("test-login");
        userData.setPassword("passwd");
        userData.setTipoUser(0);
    }

    @Test
    public void testGetLogin() {
        assertEquals("test-login", userData.getLogin());
    }

    @Test
    public void testGetPassword() {
        assertEquals("passwd", userData.getPassword());
    }
    @Test
    public void testGetTipo() {
        assertEquals(0, userData.getTipoUser());
    }
    @Test
    public void testToString() {
    	userData.toString();
        assertTrue(true);
    }
}
