package es.deusto.spq.server.jdo;

import java.util.List;

import javax.jdo.annotations.Element;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable
public class Carro {
	@PrimaryKey
    String userLogin;
	@Element
    List<String> productos;
	@Element
    List<Integer> cantidades;
    
    public Carro() {
		
		}
    
    public Carro(String user, List<String> productos, List<Integer> cantidades) {
        this.userLogin = user;
        this.productos = productos;
        this.cantidades = cantidades;
    }

    public String getUser() {
        return userLogin;
    }
    
    public void setUser(String user) {
        this.userLogin = user;
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

