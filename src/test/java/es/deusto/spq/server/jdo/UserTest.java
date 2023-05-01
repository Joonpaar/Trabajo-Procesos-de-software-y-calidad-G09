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
    
    private static ZonedDateTime timestamp = ZonedDateTime.of(2023, 03, 23, 19, 15, 22, 0, ZoneId.of("Europe/Madrid"));
    private User user;

    @Before
    public void setUp() {
        user = new User("test-login", "passwd");
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
}
