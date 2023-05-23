package es.deusto.spq.server;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Transaction;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status.Family;

import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import com.github.noconnor.junitperf.JUnitPerfRule;
import com.github.noconnor.junitperf.JUnitPerfTest;
import com.github.noconnor.junitperf.reporting.providers.HtmlReportGenerator;

import categories.IntegrationTest;
import categories.PerformanceTest;
import es.deusto.spq.pojo.UserData;
import es.deusto.spq.server.jdo.Carro;
import es.deusto.spq.server.jdo.Compra;
import es.deusto.spq.server.jdo.Producto;
import es.deusto.spq.server.jdo.TipoProducto;
import es.deusto.spq.server.jdo.User;

@Category({ IntegrationTest.class, PerformanceTest.class })
public class ResourcePerfTest {

    private static final PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory("datanucleus.properties");
    
    private static HttpServer server;
    private WebTarget target;

    @Rule
    public JUnitPerfRule perfTestRule = new JUnitPerfRule(new HtmlReportGenerator("target/junitperf/report.html"));

    @BeforeClass
    public static void prepareTests() throws Exception {
        // start the server
        server = Main.startServer();

        PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx = pm.currentTransaction();
        try {
            tx.begin();
            pm.makePersistent(new User("john", "1234"));
            tx.commit();
        } finally {
            if (tx.isActive()) {
                tx.rollback();
            }
            pm.close();
        }
    }

    @Before
    public void setUp() {
        // create the client
        Client c = ClientBuilder.newClient();
        target = c.target(Main.BASE_URI).path("resource");
    }

    @AfterClass
    public static void tearDownServer() throws Exception {
        server.shutdown();

        PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx = pm.currentTransaction();
        try {
            tx.begin();
            pm.newQuery(User.class).deletePersistentAll();
            pm.newQuery(Carro.class).deletePersistentAll();
            pm.newQuery(Producto.class).deletePersistentAll();
            pm.newQuery(Compra.class).deletePersistentAll();
            tx.commit();
        } finally {
            if (tx.isActive()) {
                tx.rollback();
            }
            pm.close();
        }
    }

    @Test
    @JUnitPerfTest(threads = 10, durationMs = 2000)
    public void testRegisterUser() {
        UserData user = new UserData();
        user.setLogin(UUID.randomUUID().toString());
        user.setPassword("1234");

        Response response = target.path("register")
            .request(MediaType.APPLICATION_JSON)
            .post(Entity.entity(user, MediaType.APPLICATION_JSON));

        assertEquals(Family.SUCCESSFUL, response.getStatusInfo().getFamily());
    }
    @Test
    @JUnitPerfTest(threads = 10, durationMs = 2000)
    public void testInsertarProducto() {
        Producto prod = new Producto();
        prod.setNombre(UUID.randomUUID().toString());
        prod.setPrecio(1);
        prod.setStock(1);
        prod.setTipo(TipoProducto.Jardineria);

        Response response = target.path("insertarProducto")
            .request(MediaType.APPLICATION_JSON)
            .post(Entity.entity(prod, MediaType.APPLICATION_JSON));

        assertEquals(Family.SUCCESSFUL, response.getStatusInfo().getFamily());
    }
    
    @Test
    @JUnitPerfTest(threads = 10, durationMs = 2000)
    public void testComprarProducto() {
    	List<String> prods=new ArrayList<>();
    	prods.add("producto");
        List<Integer> cants=new ArrayList<>();
        cants.add(1);
        Compra compra = new Compra();
        compra.setUser(UUID.randomUUID().toString());
        compra.setFecha(System.currentTimeMillis());
        compra.setCantidades(cants);
        compra.setProductos(prods);

        Response response = target.path("comprarProducto")
            .request(MediaType.APPLICATION_JSON)
            .post(Entity.entity(compra, MediaType.APPLICATION_JSON));

        assertEquals(Family.SUCCESSFUL, response.getStatusInfo().getFamily());
    }
    
    
    
}