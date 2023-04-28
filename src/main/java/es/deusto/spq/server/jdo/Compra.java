package es.deusto.spq.server.jdo;

import java.util.ArrayList;
import java.util.Date;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable
public class Compra {
	@PrimaryKey
    int id;
    User user;
    Date fecha;
    ArrayList<Producto> productos;
    ArrayList<Integer> cantidades;
    
    public Compra() {
		
		}
    
    public Compra(int id, User user, Date fecha, ArrayList<Producto> productos, ArrayList<Integer> cantidades) {
        this.id = id;
        this.user = user;
        this.fecha = fecha;
        this.productos = productos;
        this.cantidades = cantidades;
    }
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public User getUser() {
        return user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }
    
    public Date getFecha() {
        return fecha;
    }
    
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
    
    public ArrayList<Producto> getProductos() {
        return productos;
    }
    
    public void setProductos(ArrayList<Producto> productos) {
        this.productos = productos;
    }
    
    public ArrayList<Integer> getCantidades() {
        return cantidades;
    }
    
    public void setCantidades(ArrayList<Integer> cantidades) {
        this.cantidades = cantidades;
    }
}

