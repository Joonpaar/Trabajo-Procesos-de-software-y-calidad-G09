package es.deusto.spq.server.jdo;

import java.util.HashSet;
import java.util.Set;

import javax.jdo.annotations.Join;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable
public class Producto {
	@PrimaryKey
	String nombre=null;
	int precio;
	int stock;
	TipoProducto tipo;
	
	@Persistent(mappedBy="user", dependentElement="true")
	@Join
	Set<Message> messages = new HashSet<>();
	
	public Producto() {
		
	}
	
	public Producto(String nombre, int precio, int stock, TipoProducto tipo) {
		super();
		this.nombre = nombre;
		this.precio = precio;
		this.stock = stock;
		this.tipo = tipo;
	}
	
	public void addMessage(Message message) {
		messages.add(message);
	}

	public void removeMessage(Message message) {
		messages.remove(message);
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
	
	public Set<Message> getMessages() {return this.messages;}
	
	 public String toString() {
			StringBuilder messagesStr = new StringBuilder();
			for (Message message: this.messages) {
				messagesStr.append(message.toString() + " - ");
			}
	        return "Producto: nombre --> " + this.nombre + ", precio -->  " + this.precio + ", stock -->  " + this.stock + ", messages --> [" + messagesStr + "]";
	    }
	 
}
