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

public class CompraTest {
    
    private Compra compra;

    @Before
    public void setUp() {
    	List<Integer> cants=new ArrayList<>();
    	cants.add(1);
    	List<String> prods=new ArrayList<>();
    	prods.add("Semillas");
    	Long l=(long) 1;
        compra = new Compra("usu", l,prods, cants);
    }

    @Test
    public void testGetUsuario() {
        assertEquals("usu", compra.getUser());
    }
    
    @Test
    public void testGetFecha() {
        assertEquals(1, Long.parseLong(String.valueOf(compra.getFecha())));
    }

    @Test
    public void testGetProductos() {
    	List<String>prods=compra.getProductos();
        assertEquals("Semillas", prods.get(0));
    }
    
    @Test
    public void testGetCantidades() {
    	List<Integer>cants=compra.getCantidades();
        assertEquals(1, Integer.parseInt(String.valueOf(cants.get(0))));    
        }

    @Test
    public void testSetUsuario() {
    	compra.setUser("usu");
        assertEquals("usu", compra.getUser());
    }
    
    @Test
    public void testSetFecha() {
    	Long l=(long) 1;
    	compra.setFecha(l);
        assertEquals(1, Long.parseLong(String.valueOf(compra.getFecha())));
    }

    @Test
    public void testSetProductos() {
    	List<String> prods=new ArrayList<>();
    	prods.add("Semillas");
    	compra.setProductos(prods);
        assertEquals("Semillas", compra.getProductos().get(0));    
    }
    
    @Test
    public void testSetCantidades() {
    	List<Integer> cants=new ArrayList<>();
    	cants.add(1);
    	compra.setCantidades(cants);
        assertEquals(1, Integer.parseInt(String.valueOf(compra.getCantidades().get(0))));    
        }
}
