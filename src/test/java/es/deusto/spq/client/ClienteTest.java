package es.deusto.spq.client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
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
import es.deusto.spq.server.jdo.Carro;
import es.deusto.spq.server.jdo.Producto;
import es.deusto.spq.server.jdo.TipoProducto;
import es.deusto.spq.server.jdo.User;


public class ClienteTest {

    @Mock
    private Client client;

    @Mock(answer=Answers.RETURNS_DEEP_STUBS)
    private WebTarget webTarget;

    @Captor
    private ArgumentCaptor<Entity<UserData>> userDataEntityCaptor;

   

    private Cliente exampleClient;
    private Producto exampleProducto;
    private List<Producto> listaProductos;
    private Carro exampleCarro;
    private User exampleUsuario;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // prepare static mock of ClientBuilder
        try (MockedStatic<ClientBuilder> clientBuilder = Mockito.mockStatic(ClientBuilder.class)) {
            clientBuilder.when(ClientBuilder::newClient).thenReturn(client);
            when(client.target("http://localhost:8080/rest/resource")).thenReturn(webTarget);

            exampleClient = new Cliente("localhost", "8080");
            exampleProducto=new Producto("prodcuto", 1, 1, TipoProducto.Jardineria);
            listaProductos=new ArrayList<>();
            listaProductos.add(exampleProducto);
            List<String> prods=new ArrayList<>();
            List<Integer> cants=new ArrayList<>();
            exampleCarro=new Carro("test-login", prods, cants);
            exampleUsuario=new User("test-login", "passwd", 0, 0);
        }
    }

    @Test
    public void testRegisterUser() {
        when(webTarget.path("register")).thenReturn(webTarget);

        Response response = Response.ok().build();
        when(webTarget.request(MediaType.APPLICATION_JSON).post(any(Entity.class))).thenReturn(response);
        assertTrue(Cliente.registerUser("test-login", "passwd"));

        verify(webTarget.request(MediaType.APPLICATION_JSON)).post(userDataEntityCaptor.capture());
        assertEquals("test-login", userDataEntityCaptor.getValue().getEntity().getLogin());
        assertEquals("passwd", userDataEntityCaptor.getValue().getEntity().getPassword());
    }

    @Test
    public void testRegisterUserWithError() {
        when(webTarget.path("register")).thenReturn(webTarget);

        Response response = Response.serverError().build();
        when(webTarget.request(MediaType.APPLICATION_JSON).post(any(Entity.class))).thenReturn(response);
        assertFalse(Cliente.registerUser("test-login", "passwd"));

        verify(webTarget.request(MediaType.APPLICATION_JSON)).post(userDataEntityCaptor.capture());
        assertEquals("test-login", userDataEntityCaptor.getValue().getEntity().getLogin());
        assertEquals("passwd", userDataEntityCaptor.getValue().getEntity().getPassword());
    }
    
    @Test
    public void testLoginUser() {
    	when(webTarget.path("login")).thenReturn(webTarget);
    	Response response = mock(Response.class);
        when(webTarget.request(MediaType.APPLICATION_JSON).post(any(Entity.class))).thenReturn(response);
        when(response.getStatus()).thenReturn(Response.Status.OK.getStatusCode());
        when(response.readEntity(Integer.class)).thenReturn(3);
        assertTrue(Cliente.loginUser("test-login", "passwd"));
        
        verify(webTarget.request(MediaType.APPLICATION_JSON)).post(userDataEntityCaptor.capture());
        assertEquals("test-login", userDataEntityCaptor.getValue().getEntity().getLogin());
        assertEquals("passwd", userDataEntityCaptor.getValue().getEntity().getPassword());
    }
    
    @Test
    public void testInsertarProducto() {
    	when(webTarget.path("insertarProducto")).thenReturn(webTarget);
    	Response response = Response.ok().build();
        when(webTarget.request(MediaType.APPLICATION_JSON).post(any(Entity.class))).thenReturn(response);
        Cliente.insertarProducto("producto", 1, 1, TipoProducto.Jardineria);

    }
    @Test
    public void testGetProductos() {
    	when(webTarget.path("getProductos")).thenReturn(webTarget);
    	Response response = mock(Response.class);
    	GenericType<ArrayList<Producto>> listType = new GenericType<ArrayList<Producto>>() {};
        when(webTarget.request(MediaType.APPLICATION_JSON).post(any(Entity.class))).thenReturn(response);
        when(response.getStatus()).thenReturn(Response.Status.OK.getStatusCode());
        when(response.readEntity(listType)).thenReturn((ArrayList<Producto>) listaProductos);
        Cliente.getProductos();

    }
    @Test
    public void testGetProductosPorTipo() {
    	when(webTarget.path("getProductosPorTipo")).thenReturn(webTarget);
    	Response response = mock(Response.class);
    	GenericType<ArrayList<Producto>> listType = new GenericType<ArrayList<Producto>>() {};
        when(webTarget.request(MediaType.APPLICATION_JSON).post(any(Entity.class))).thenReturn(response);
        when(response.getStatus()).thenReturn(Response.Status.OK.getStatusCode());
        when(response.readEntity(listType)).thenReturn((ArrayList<Producto>) listaProductos);
        Cliente.getProductosPorTipo(TipoProducto.Jardineria);
    }
    @Test
    public void testBorrarProducto() {
    	when(webTarget.path("borrarProducto")).thenReturn(webTarget);
    	Response response = Response.ok().build();
        when(webTarget.request(MediaType.APPLICATION_JSON).post(any(Entity.class))).thenReturn(response);
        Cliente.borrarProducto("producto");
    }
    @Test
    public void testGetComprasDelUsuario() {
    	when(webTarget.path("getComprasDelUsuario")).thenReturn(webTarget);
    	Response response = mock(Response.class);
    	GenericType<ArrayList<Producto>> listType = new GenericType<ArrayList<Producto>>() {};
        when(webTarget.request(MediaType.APPLICATION_JSON).post(any(Entity.class))).thenReturn(response);
        when(response.getStatus()).thenReturn(Response.Status.OK.getStatusCode());
        when(response.readEntity(listType)).thenReturn((ArrayList<Producto>) listaProductos);
        Cliente.getComprasDelUsuario("test-login");
    }
    @Test
    public void testEditarProducto() {
    	when(webTarget.path("editarProducto")).thenReturn(webTarget);
    	Response response = Response.ok().build();
        when(webTarget.request(MediaType.APPLICATION_JSON).post(any(Entity.class))).thenReturn(response);
        Cliente.editarProducto("producto", TipoProducto.Jardineria.name(),2, 2);
    }
    @Test
    public void testComprarProducto() {
    	when(webTarget.path("comprarProducto")).thenReturn(webTarget);
    	Response response = Response.ok().build();
        when(webTarget.request(MediaType.APPLICATION_JSON).post(any(Entity.class))).thenReturn(response);
        List<String> prods=new ArrayList<>();
        List<Integer> cants=new ArrayList<>();
        Cliente.comprarProducto(prods, cants);
    }
    @Test
    public void testGetProducto() {
    	when(webTarget.path("getComprasDelUsuario")).thenReturn(webTarget);
    	Response response = mock(Response.class);
        when(webTarget.request(MediaType.APPLICATION_JSON).post(any(Entity.class))).thenReturn(response);
        when(response.getStatus()).thenReturn(Response.Status.OK.getStatusCode());
        when(response.readEntity(Producto.class)).thenReturn(exampleProducto);
        Cliente.getProducto("producto");
    }
    @Test
    public void testGetCarro() {
    	when(webTarget.path("getCarro")).thenReturn(webTarget);
    	Response response = mock(Response.class);
        when(webTarget.request(MediaType.APPLICATION_JSON).post(any(Entity.class))).thenReturn(response);
        when(response.getStatus()).thenReturn(Response.Status.OK.getStatusCode());
        when(response.readEntity(Carro.class)).thenReturn(exampleCarro);
        Cliente.getCarro("test-login");
    }
    @Test
    public void testActualizarCarro() {
    	when(webTarget.path("actualizarCarro")).thenReturn(webTarget);
    	Response response = Response.ok().build();
        when(webTarget.request(MediaType.APPLICATION_JSON).post(any(Entity.class))).thenReturn(response);
        List<String> prods=new ArrayList<>();
        List<Integer> cants=new ArrayList<>();
        Cliente.actualizarCarro(prods, cants);
    }
    @Test
    public void testGetUsuarioPorNombre() {
    	when(webTarget.path("getUsuarioPorNombre")).thenReturn(webTarget);
    	Response response = mock(Response.class);
        when(webTarget.request(MediaType.APPLICATION_JSON).post(any(Entity.class))).thenReturn(response);
        when(response.readEntity(User.class)).thenReturn(exampleUsuario);
        Cliente.getUsuarioPorNombre("test-login");
    }
    
    @Test
    public void testEditarUser() {
    	when(webTarget.path("editarUser")).thenReturn(webTarget);
    	Response response = Response.ok().build();
        when(webTarget.request(MediaType.APPLICATION_JSON).post(any(Entity.class))).thenReturn(response);
        Cliente.editarUser("test-login", 1);
    }
}
