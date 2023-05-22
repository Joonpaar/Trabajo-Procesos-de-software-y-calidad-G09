package es.deusto.spq.server;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.Extent;
import javax.jdo.JDOHelper;
import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;
import javax.jdo.Transaction;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import es.deusto.spq.client.Cliente;
import es.deusto.spq.pojo.UserData;
import es.deusto.spq.server.jdo.Carro;
import es.deusto.spq.server.jdo.Compra;
import es.deusto.spq.server.jdo.Producto;
import es.deusto.spq.server.jdo.TipoProducto;
import es.deusto.spq.server.jdo.User;


public class ResourceTest {

    private Resource resource;

    @Mock
    private PersistenceManager persistenceManager;

    @Mock
    private Transaction transaction;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // initializing static mock object PersistenceManagerFactory
        try (MockedStatic<JDOHelper> jdoHelper = Mockito.mockStatic(JDOHelper.class)) {
            PersistenceManagerFactory pmf = mock(PersistenceManagerFactory.class);
            jdoHelper.when(() -> JDOHelper.getPersistenceManagerFactory("datanucleus.properties")).thenReturn(pmf);
            
            when(pmf.getPersistenceManager()).thenReturn(persistenceManager);
            when(persistenceManager.currentTransaction()).thenReturn(transaction);

            // instantiate tested object with mock dependencies
            resource = new Resource();
        }
    }
    

    @Test
    public void testRegisterUser() {
        // prepare mock Persistence Manager to return User
        UserData userData = new UserData();
        userData.setLogin("test-login");
        userData.setPassword("passwd");

        // simulate that 
        User user = spy(User.class);
        when(persistenceManager.getObjectById(User.class, userData.getLogin())).thenReturn(user);

        // call tested method
        Response response = resource.registerUser(userData);

        // check that the user is set by the code with the password
        ArgumentCaptor<String> passwordCaptor = ArgumentCaptor.forClass(String.class);
        verify(user).setPassword(passwordCaptor.capture());
        assertEquals("passwd", passwordCaptor.getValue());

        // check expected response
        assertEquals(Response.Status.OK, response.getStatusInfo());
    }
    
    @Test
    public void testLoginUser() {
    	UserData userData = new UserData();
        userData.setLogin("test-login");
        userData.setPassword("passwd");
        
        User user = spy(User.class);
        when(persistenceManager.getObjectById(User.class, userData.getLogin())).thenReturn(user);
        when(user.getPassword()).thenReturn("passwd");
        int response = resource.loginUser(userData);

//        // check that the user is set by the code with the password
//        ArgumentCaptor<String> passwordCaptor = ArgumentCaptor.forClass(String.class);
//        verify(user).setPassword(passwordCaptor.capture());
//        assertEquals("passwd", passwordCaptor.getValue());
//
//        // check expected response
        assertEquals(response, 2);
        
    }
    
    @Test
    public void testLoginUser2() {
    	UserData userData = new UserData();
        userData.setLogin("test-login");
        userData.setPassword("admin");
        
        User user = spy(User.class);
        when(persistenceManager.getObjectById(User.class, userData.getLogin())).thenReturn(user);
        when(user.getPassword()).thenReturn("passwd");
        int response = resource.loginUser(userData);

       // check expected response
        assertEquals(response, 1);
    }
    
    @Test
    public void testLoginUser3() {
    	UserData userData = new UserData();
        userData.setLogin("admin");
        userData.setPassword("admin");
        userData.setTipoUser(1);
        
        User user = spy(User.class);
        when(persistenceManager.getObjectById(User.class, userData.getLogin())).thenReturn(user);
        when(user.getPassword()).thenReturn("admin");
        when(user.getTipoUser()).thenReturn(1);
        int response = resource.loginUser(userData);

       // check expected response
        assertEquals(response, 3);
    }
    
    @Test
    public void testNoLogin() {
        // prepare mock Persistence Manager to return User
        UserData userData = new UserData();
        userData.setLogin("test-login");
        userData.setPassword("passwd");
        
        // simulate that the object is not found in the database
        when(persistenceManager.getObjectById(any(), anyString())).thenThrow(new JDOObjectNotFoundException());

        // prepare mock transaction behaviour
        when(transaction.isActive()).thenReturn(true);

        // call tested method
        int response = resource.loginUser(userData);
    }
    
    
    
    @Test
    public void testRegisterUserNotFound() {
        // prepare mock Persistence Manager to return User
        UserData userData = new UserData();
        userData.setLogin("test-login");
        userData.setPassword("passwd");
        
        // simulate that the object is not found in the database
        when(persistenceManager.getObjectById(any(), anyString())).thenThrow(new JDOObjectNotFoundException());

        // prepare mock transaction behaviour
        when(transaction.isActive()).thenReturn(true);

        // call tested method
        Response response = resource.registerUser(userData);

        // check that the new user is stored in the database with the correct values
//        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
//        verify(persistenceManager).makePersistent(userCaptor.capture());
//        assertEquals("test-login", userCaptor.getValue().getLogin());
//        assertEquals("passwd", userCaptor.getValue().getPassword());
//
//        // check expected response
//        assertEquals(Response.Status.OK, response.getStatusInfo());
    }
    
    @Test
    public void testInsertarProducto() {
    	Producto producto = new Producto("producto", 1, 1, TipoProducto.Jardineria);
        when(persistenceManager.getObjectById(Producto.class, producto.getNombre())).thenReturn(null);
        Response response = resource.insertarProducto(producto);
        assertEquals(Response.Status.OK, response.getStatusInfo());
    }
    
    @Test
    public void testNoInsertarProducto() {
    	Producto producto = new Producto("producto", 2, 2, TipoProducto.Jardineria);
        when(persistenceManager.getObjectById(Producto.class, producto.getNombre())).thenThrow(new JDOObjectNotFoundException());
        when(transaction.isActive()).thenReturn(true);
        Response response = resource.insertarProducto(producto);
    }
    
    @Test
    public void testGetAll() {
    	Extent<Producto> prodExtent=mock(Extent.class);
    	when(persistenceManager.getExtent(Producto.class, true)).thenReturn(prodExtent);
        List<Producto> response = resource.getAll();
    }
    
    @Test
    public void testNoGetAll() {
    	Extent<Producto> prodExtent=mock(Extent.class);
    	when(persistenceManager.getExtent(Producto.class, true)).thenThrow(new JDOObjectNotFoundException());
        List<Producto> response = resource.getAll();
    }
    
    @Test
    public void testGetProductosPorTipo() {
    	Extent<Producto> prodExtent=mock(Extent.class);
    	when(persistenceManager.getExtent(Producto.class, true)).thenReturn(prodExtent);
        List<Producto> response = resource.getProductosPorTIpo(TipoProducto.Jardineria);
    }
    
    @Test
    public void testEditarProducto() {
    	Producto producto=new Producto("producto", 2, 2, TipoProducto.Jardineria);
    	when(persistenceManager.getObjectById(Producto.class, producto.getNombre())).thenReturn(producto);
        Response response = resource.editarProducto(producto);
        assertEquals(Response.Status.OK, response.getStatusInfo());
    }
    
    @Test
    public void testNoEditarProducto() {
    	Producto producto = new Producto("producto", 2, 2, TipoProducto.Jardineria);
        when(persistenceManager.getObjectById(Producto.class, producto.getNombre())).thenThrow(new JDOObjectNotFoundException());
        when(transaction.isActive()).thenReturn(true);
        Response response = resource.editarProducto(producto);
    }
    
    @Test
    public void testGetComprasDelUsu() {
    	String nombre="test-login";
    	Query<?> q=mock(Query.class);
    	List<Compra> compras=new ArrayList<>();
    	Compra compra=new Compra();
    	compras.add(compra);
    	when(persistenceManager.newQuery("SELECT FROM " + Compra.class.getName() + " WHERE userLogin == '" + nombre + "'")).thenReturn(q);
    	when((List<Compra>) q.execute()).thenReturn(compras);
    	List<Compra> response = resource.getComprasDelUsu(nombre);
        assertEquals(compras, response);
    }
    @Test
    public void testBorrarProducto() {
    	Producto producto=new Producto("producto", 2, 2, TipoProducto.Jardineria);
    	when(persistenceManager.getObjectById(Producto.class, producto.getNombre())).thenReturn(producto);
        Response response = resource.borrarProducto(producto);
        assertEquals(Response.Status.OK, response.getStatusInfo());
    }
    
    @Test
    public void testNoBorrarProducto() {
    	Producto producto = new Producto("producto", 2, 2, TipoProducto.Jardineria);
        when(persistenceManager.getObjectById(Producto.class, producto.getNombre())).thenThrow(new JDOObjectNotFoundException());
        when(transaction.isActive()).thenReturn(true);
        Response response = resource.borrarProducto(producto);
    }
    
    
    @Test
    public void testComprarProducto() {
    	Compra compra=new Compra();
        Response response = resource.comprarProducto(compra);
        assertEquals(Response.Status.OK, response.getStatusInfo());
    }
    @Test
    public void testGetProducto() {
    	Producto producto=new Producto();
    	String nombre="producto";
    	when(persistenceManager.getObjectById(Producto.class, producto.getNombre())).thenReturn(producto);
        Producto response = resource.getProducto(nombre);
    }
    @Test
    public void testGetUsuarioPorNombre() {
    	User usuario=new User();
    	String nombre="test-login";
    	when(persistenceManager.getObjectById(User.class, nombre)).thenReturn(usuario);
        User response = resource.getUsuarioPorNombre(nombre);
    }
    @Test
    public void testGetCarro() {
    	Carro carro=new Carro();
    	String nombre="test-login";
    	when(persistenceManager.getObjectById(Carro.class, nombre)).thenReturn(carro);
        Carro response = resource.getCarro(nombre);
    }
    @Test
    public void testEditarUser() {
    	User usuario=new User("test-login", "passwd", 0, 0);
    	UserData userData=mock(UserData.class);
    	when(persistenceManager.getObjectById(User.class, userData.getLogin())).thenReturn(usuario);
        Response response = resource.editarUser(userData);
        assertEquals(Response.Status.OK, response.getStatusInfo());
    }
    
    @Test
    public void testNoEditaruser() {
        // prepare mock Persistence Manager to return User
        UserData userData = new UserData();
        userData.setLogin("test-login");
        userData.setPassword("passwd");

        when(persistenceManager.getObjectById(any(), anyString())).thenThrow(new JDOObjectNotFoundException());

        when(transaction.isActive()).thenReturn(true);

        Response response = resource.editarUser(userData);
    }
    
    
    @Test
    public void testActualizarCarro() {
    	List<String> prods=new ArrayList<>();
    	List<Integer> cants=new ArrayList<>();
    	Carro carro=new Carro("test-login", prods, cants);
    	
    	Carro carroData=mock(Carro.class);
    	String nombre="test-login";
    	when(persistenceManager.getObjectById(Carro.class, carroData.getUser())).thenReturn(carro);
        Response response = resource.actualizarCarro(carroData);
    }
    
    @Test
    public void testNoActualizarCarro() {
    	List<String> prods=new ArrayList<>();
    	List<Integer> cants=new ArrayList<>();
    	Carro carro=new Carro("test-login", prods, cants);
        when(persistenceManager.getObjectById(any(), anyString())).thenThrow(new JDOObjectNotFoundException());
        when(transaction.isActive()).thenReturn(true);
        Response response = resource.actualizarCarro(carro);
    }
}
