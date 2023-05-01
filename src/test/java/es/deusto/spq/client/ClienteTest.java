package es.deusto.spq.client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Answers;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import es.deusto.spq.pojo.UserData;


public class ClienteTest {

    @Mock
    private Client client;

    @Mock(answer=Answers.RETURNS_DEEP_STUBS)
    private WebTarget webTarget;

    @Captor
    private ArgumentCaptor<Entity<UserData>> userDataEntityCaptor;

   

    private Cliente exampleClient;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // prepare static mock of ClientBuilder
        try (MockedStatic<ClientBuilder> clientBuilder = Mockito.mockStatic(ClientBuilder.class)) {
            clientBuilder.when(ClientBuilder::newClient).thenReturn(client);
            when(client.target("http://localhost:8080/rest/resource")).thenReturn(webTarget);

            exampleClient = new Cliente("localhost", "8080");
        }
    }

//    @Test
//    public void testRegisterUser() {
//        when(webTarget.path("register")).thenReturn(webTarget);
//
//        Response response = Response.ok().build();
//        when(webTarget.request(MediaType.APPLICATION_JSON).post(any(Entity.class))).thenReturn(response);
////        assertTrue(exampleClient.registerUser("test-login", "passwd"));
//
//        verify(webTarget.request(MediaType.APPLICATION_JSON)).post(userDataEntityCaptor.capture());
//        assertEquals("test-login", userDataEntityCaptor.getValue().getEntity().getLogin());
//        assertEquals("passwd", userDataEntityCaptor.getValue().getEntity().getPassword());
//    }
//
//    @Test
//    public void testRegisterUserWithError() {
//        when(webTarget.path("register")).thenReturn(webTarget);
//
//        Response response = Response.serverError().build();
//        when(webTarget.request(MediaType.APPLICATION_JSON).post(any(Entity.class))).thenReturn(response);
//        assertFalse(exampleClient.registerUser("test-login", "passwd"));
//
//        verify(webTarget.request(MediaType.APPLICATION_JSON)).post(userDataEntityCaptor.capture());
//        assertEquals("test-login", userDataEntityCaptor.getValue().getEntity().getLogin());
//        assertEquals("passwd", userDataEntityCaptor.getValue().getEntity().getPassword());
//    }
}
