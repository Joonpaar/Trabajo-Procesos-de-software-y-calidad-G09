package es.deusto.spq.server.jdo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.mockito.Mockito;
import org.mockito.MockedStatic;

import org.junit.Before;
import org.junit.Test;

import java.time.ZoneId;
import java.time.ZonedDateTime;

public class ProductoTest {
    
    private Producto prod;

    @Before
    public void setUp() {
        prod = new Producto("Semillas", 1, 1, TipoProducto.Jardineria);
    }

    @Test
    public void testGetNombre() {
        assertEquals("Semillas", prod.getNombre());
    }

    @Test
    public void testGetPrecio() {
        assertEquals(1, prod.getPrecio());
    }

    @Test
    public void testGetStock() {
        assertEquals(1, prod.getStock());
    }
    @Test
    public void testGetTipo() {
        assertEquals(TipoProducto.Jardineria, prod.getTipo());
    }
    @Test
    public void testSetNombre() {
    	prod.setNombre("Semillas");
        assertEquals("Semillas", prod.getNombre());
    }

    @Test
    public void testSetPrecio() {
    	prod.setPrecio(1);
        assertEquals(1, prod.getPrecio());
    }

    @Test
    public void testSetStock() {
    	prod.setStock(1);
        assertEquals(1, prod.getStock());
    }
    @Test
    public void testSetTipo() {
    	prod.setTipo(TipoProducto.Jardineria);
        assertEquals(TipoProducto.Jardineria, prod.getTipo());
    }
}
