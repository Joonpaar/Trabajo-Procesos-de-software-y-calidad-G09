package es.deusto.spq.server.jdo;

import java.util.List;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable
public class Compra {
	@PrimaryKey
    String userLogin;
	@PrimaryKey
    Long fecha;
    List<String> productos;
    List<Integer> cantidades;
    
    public Compra() {
		
		}
    
    public Compra(String user,Long fecha, List<String> productos, List<Integer> cantidades) {
        this.userLogin = user;
        this.fecha = fecha;
        this.productos = productos;
        this.cantidades = cantidades;
    }

    public String getUser() {
        return userLogin;
    }
    
    public void setUser(String user) {
        this.userLogin = user;
    }
    
    public Long getFecha() {
        return fecha;
    }
    
    public void setFecha(Long fecha) {
        this.fecha = fecha;
    }
    
    public List<String> getProductos() {
        return productos;
    }
    
    public void setProductos(List<String> productos) {
        this.productos = productos;
    }
    
    public List<Integer> getCantidades() {
        return cantidades;
    }
    
    public void setCantidades(List<Integer> cantidades) {
        this.cantidades = cantidades;
    }
}

