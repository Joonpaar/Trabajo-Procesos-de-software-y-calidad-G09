package es.deusto.spq.server.jdo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.mockito.Mockito;
import org.mockito.MockedStatic;

import org.junit.Before;
import org.junit.Test;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

public class CarroTest {
    
    private Carro carro;

    @Before
    public void setUp() {
    	List<Integer> cants=new ArrayList<>();
    	cants.add(1);
    	List<String> prods=new ArrayList<>();
    	prods.add("Semillas");
        carro = new Carro("usu", prods, cants);
    }

    @Test
    public void testGetUsuario() {
        assertEquals("usu", carro.getUser());
    }

    @Test
    public void testGetProductos() {
    	List<String>prods=carro.getProductos();
        assertEquals("Semillas", prods.get(0));
    }
    
    @Test
    public void testGetCantidades() {
    	List<Integer>cants=carro.getCantidades();
        assertEquals(1, Integer.parseInt(String.valueOf(cants.get(0))));    
        }

    @Test
    public void testSetUsuario() {
    	carro.setUser("usu");
        assertEquals("usu", carro.getUser());
    }

    @Test
    public void testSetProductos() {
    	List<String> prods=new ArrayList<>();
    	prods.add("Semillas");
    	carro.setProductos(prods);
        assertEquals("Semillas", carro.getProductos().get(0));    
    }
    
    @Test
    public void testSetCantidades() {
    	List<Integer> cants=new ArrayList<>();
    	cants.add(1);
    	carro.setCantidades(cants);
        assertEquals(1, Integer.parseInt(String.valueOf(carro.getCantidades().get(0))));    
        }
}
