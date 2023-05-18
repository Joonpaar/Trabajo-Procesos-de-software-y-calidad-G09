package es.deusto.spq.client;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.QueryParam;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import es.deusto.spq.pojo.UserData;
import es.deusto.spq.server.jdo.Carro;
import es.deusto.spq.server.jdo.Compra;
import es.deusto.spq.server.jdo.Producto;
import es.deusto.spq.server.jdo.TipoProducto;
import es.deusto.spq.server.jdo.User;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Cliente {

	protected static final Logger logger = LogManager.getLogger();

	protected static boolean admin;
	private Client client;
	private static WebTarget webTarget;

	public Cliente(String hostname, String port) {
		client = ClientBuilder.newClient();
		webTarget = client.target(String.format("http://%s:%s/rest/resource", hostname, port));
	}

	public static boolean registerUser(String login, String password) {
		WebTarget registerUserWebTarget = webTarget.path("register");
		Invocation.Builder invocationBuilder = registerUserWebTarget.request(MediaType.APPLICATION_JSON);

		UserData userData = new UserData();
		userData.setLogin(login);
		userData.setPassword(password);
		Response response = invocationBuilder.post(Entity.entity(userData, MediaType.APPLICATION_JSON));
		if (response.getStatus() != Status.OK.getStatusCode()) {
			logger.error("Error connecting with the server. Code: {}", response.getStatus());
			return false;
		} else {
			logger.info("User correctly registered");
			return true;
		}
	}

	// FALTA POR HACER
	public static boolean loginUser(String login, String password) {
		WebTarget loginUserWebTarget = webTarget.path("login");
		Invocation.Builder invocationBuilder = loginUserWebTarget.request(MediaType.APPLICATION_JSON);

		UserData userData = new UserData();
		userData.setLogin(login);
		userData.setPassword(password);
		Response response = invocationBuilder.post(Entity.entity(userData, MediaType.APPLICATION_JSON));
		if (response.getStatus() != Status.OK.getStatusCode()) {
			logger.error("Error connecting with the server. Code: {}", response.getStatus());
		} else {
			int valor = response.readEntity(Integer.class);
			logger.info("El valor es: " + valor);
			if (valor == 0) {
				logger.info("El nombre de usuario no es correcto");
				return false;
			} else if (valor == 1) {
				logger.info("La constrasenia es incorrecta!");
				return false;
			} else if (valor == 2) {
				admin = false;
				logger.info("Usuario logeado correctamente!");
				return true;
			} else if (valor == 3) {
				admin = true;
				logger.info("Usuario administrador logeado correctamente!");
				return true;
			}

			logger.info("Error de login correctamente!");
			return false;
		}
		return false;
	}

	public static void insertarProducto(String nombre, int precio, int stock, TipoProducto tipo) {
		WebTarget insertarProductoWebTarget = webTarget.path("insertarProducto");
		Invocation.Builder invocationBuilder = insertarProductoWebTarget.request(MediaType.APPLICATION_JSON);

		Producto productoData = new Producto();
		productoData.setNombre(nombre);
		productoData.setPrecio(precio);
		productoData.setStock(stock);
		productoData.setTipo(tipo);
		Response response = invocationBuilder.post(Entity.entity(productoData, MediaType.APPLICATION_JSON));
		if (response.getStatus() != Status.OK.getStatusCode()) {
			logger.error("Error connecting with the server. Code: {}", response.getStatus());
		} else {
			logger.info("Producto insertado correctamente");
		}
	}

	public static ArrayList<Producto> getProductos() {
		WebTarget getProductosUserWebTarget = webTarget.path("getProductos");
		Invocation.Builder invocationBuilder = getProductosUserWebTarget.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.post(null);
		if (response.getStatus() != Status.OK.getStatusCode()) {
			logger.error("Error connecting with the server. Code: {}", response.getStatus());
			return null;
		} else {
			GenericType<ArrayList<Producto>> listType = new GenericType<ArrayList<Producto>>() {
			};
			ArrayList<Producto> productos = response.readEntity(listType);
			return productos;
		}
	}
	
	//Falta por verificar
	public static ArrayList<Producto> getProductosPorTipo(@QueryParam("tipo") TipoProducto tipo) {
	    WebTarget getProductosUserWebTarget = webTarget.path("getProductosPorTipo").queryParam("tipo", tipo);
	    Invocation.Builder invocationBuilder = getProductosUserWebTarget.request(MediaType.APPLICATION_JSON);
	    Response response = invocationBuilder.post(null);
	    if (response.getStatus() != Status.OK.getStatusCode()) {
	        logger.error("Error connecting with the server. Code: {}", response.getStatus());
	        return null;
	    } else {
	        GenericType<ArrayList<Producto>> listType = new GenericType<ArrayList<Producto>>() {};
	        ArrayList<Producto> productos = response.readEntity(listType);
	        return productos;
	    }
	}

	public static void borrarProducto(String nombre) {
		WebTarget borrarProductoWebTarget = webTarget.path("borrarProducto");
		Invocation.Builder invocationBuilder = borrarProductoWebTarget.request(MediaType.APPLICATION_JSON);

		Producto productoData = new Producto();
		productoData.setNombre(nombre);

		Response response = invocationBuilder.post(Entity.entity(productoData, MediaType.APPLICATION_JSON));
		if (response.getStatus() != Status.OK.getStatusCode()) {
			logger.error("Error connecting with the server. Code: {}", response.getStatus());
		} else {
			logger.info("Producto borrado");
		}
	}
	
	public static ArrayList<Compra> getComprasDelUsuario(String nombre) {
		WebTarget borrarProductoWebTarget = webTarget.path("getComprasDelUsuario");
		Invocation.Builder invocationBuilder = borrarProductoWebTarget.request(MediaType.APPLICATION_JSON);

		UserData userData = new UserData();
		userData.setLogin(nombre);

		Response response = invocationBuilder.post(Entity.entity(userData, MediaType.APPLICATION_JSON));
		if (response.getStatus() != Status.OK.getStatusCode()) {
			logger.error("Error connecting with the server. Code: {}", response.getStatus());
			return null;
		} else {
			GenericType<ArrayList<Compra>> listType = new GenericType<ArrayList<Compra>>() {};
	        ArrayList<Compra> comprasDelUsu = response.readEntity(listType);
	        return comprasDelUsu;
		}
	}

	public static void editarProducto(String nombre, String tipo, int stock, int precio) {
		WebTarget editarProductoWebTarget = webTarget.path("editarProducto");
		Invocation.Builder invocationBuilder = editarProductoWebTarget.request(MediaType.APPLICATION_JSON);

		Producto productoData = new Producto();
		productoData.setNombre(nombre);
		productoData.setTipo(TipoProducto.valueOf(tipo));
		productoData.setStock(stock);
		productoData.setPrecio(precio);

		Response response = invocationBuilder.post(Entity.entity(productoData, MediaType.APPLICATION_JSON));
		if (response.getStatus() != Status.OK.getStatusCode()) {
			logger.error("Error connecting with the server. Code: {}", response.getStatus());
		} else {
			logger.info("Producto editado");
		}
	}
	
	public static void comprarProducto(List<String> productos, List<Integer> cantidades) {
		WebTarget editarProductoWebTarget = webTarget.path("comprarProducto");
		Invocation.Builder invocationBuilder = editarProductoWebTarget.request(MediaType.APPLICATION_JSON);

		Compra compraData = new Compra();
		compraData.setCantidades(cantidades);
		compraData.setFecha(System.currentTimeMillis());
		compraData.setProductos(productos);
		compraData.setUser(VentanaCatalogo.cli);

		Response response = invocationBuilder.post(Entity.entity(compraData, MediaType.APPLICATION_JSON));
		if (response.getStatus() != Status.OK.getStatusCode()) {
			logger.error("Error connecting with the server. Code: {}", response.getStatus());
		} else {
			logger.info("Compra insertada correctamente");
		}
	}
	
	public static Producto getProducto(String nombre) {
		WebTarget editarProductoWebTarget = webTarget.path("getProducto");
		Invocation.Builder invocationBuilder = editarProductoWebTarget.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.post(Entity.entity(nombre, MediaType.APPLICATION_JSON));
		if (response.getStatus() != Status.OK.getStatusCode()) {
			logger.error("Error connecting with the server. Code: {}", response.getStatus());
			return null;
		} else {
			logger.info("Producto obtenido");
			Producto producto=response.readEntity(Producto.class);
			return producto;
		}
	}
	
	public static Carro getCarro(String nombre) {
		WebTarget editarProductoWebTarget = webTarget.path("getCarro");
		Invocation.Builder invocationBuilder = editarProductoWebTarget.request(MediaType.APPLICATION_JSON);

		Response response = invocationBuilder.post(Entity.entity(nombre, MediaType.APPLICATION_JSON));
		if (response.getStatus() != Status.OK.getStatusCode()) {
			logger.error("Error connecting with the server. Code: {}", response.getStatus());
			return null;
		} else {
			logger.info("Carro obtenido");
			Carro carro=response.readEntity(Carro.class);
			return carro;
		}
	}
	
	public static void actualizarCarro(List<String> prods, List<Integer> cant) {
		WebTarget editarProductoWebTarget = webTarget.path("actualizarCarro");
		Invocation.Builder invocationBuilder = editarProductoWebTarget.request(MediaType.APPLICATION_JSON);

		Carro carroData = new Carro();
		carroData.setUser(VentanaCatalogo.cli);
		carroData.setProductos(prods);
		carroData.setCantidades(cant);

		Response response = invocationBuilder.post(Entity.entity(carroData, MediaType.APPLICATION_JSON));
		if (response.getStatus() != Status.OK.getStatusCode()) {
			logger.error("Error connecting with the server. Code: {}", response.getStatus());
		} else {
			logger.info("Carro actualizado");
		}
	}
	
	public static User getUsuarioPorNombre(String nombre) {
		WebTarget getUsuarioPorNombreWebTarget = webTarget.path("getUsuarioPorNombre");
		Invocation.Builder invocationBuilder = getUsuarioPorNombreWebTarget.request(MediaType.APPLICATION_JSON);

		Response response = invocationBuilder.post(Entity.entity(nombre, MediaType.APPLICATION_JSON));
		if (response.getStatus() != Status.OK.getStatusCode()) {
			logger.error("Error connecting with the server. Code: {}", response.getStatus());
			return null;
		} else {
			User usuario = response.readEntity(User.class);
			return usuario;
		}
	}
	
	public static void editarUser(String nombre,int valoracion) {
		WebTarget editarUserWebTarget = webTarget.path("editarUser");
		Invocation.Builder invocationBuilder = editarUserWebTarget.request(MediaType.APPLICATION_JSON);

		UserData userData = new UserData();
		userData.setLogin(nombre);
		userData.setValoracion(valoracion);

		Response response = invocationBuilder.post(Entity.entity(userData, MediaType.APPLICATION_JSON));
		if (response.getStatus() != Status.OK.getStatusCode()) {
			logger.error("Error connecting with the server. Code: {}", response.getStatus());
		} else {
			logger.info("User editado");
		}
	}

	public static void main(String[] args) {
		if (args.length != 2) {
			logger.info("Use: java Client.Client [host] [port]");
			System.exit(0);
		}

		String hostname = args[0];
		String port = args[1];

		Cliente exampleClient = new Cliente(hostname, port);
		VentanaLogin v1 = new VentanaLogin();
		v1.setVisible(true);
	}
}