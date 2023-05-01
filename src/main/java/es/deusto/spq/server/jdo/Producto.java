package es.deusto.spq.server.jdo;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable
public class Producto {
	@PrimaryKey
	String nombre=null;
	int precio;
	int stock;
	TipoProducto tipo;
		
	public Producto() {
		
	}
	
	public Producto(String nombre, int precio, int stock, TipoProducto tipo) {
		super();
		this.nombre = nombre;
		this.precio = precio;
		this.stock = stock;
		this.tipo = tipo;
	}
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getPrecio() {
		return precio;
	}

	public void setPrecio(int precio) {
		this.precio = precio;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public TipoProducto getTipo() {
		return tipo;
	}

	public void setTipo(TipoProducto tipo) {
		this.tipo = tipo;
	}
	
	 
}
