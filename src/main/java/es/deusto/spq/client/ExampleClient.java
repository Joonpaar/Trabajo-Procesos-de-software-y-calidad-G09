package es.deusto.spq.client;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import es.deusto.spq.pojo.DirectMessage;
import es.deusto.spq.pojo.MessageData;
import es.deusto.spq.pojo.UserData;
import es.deusto.spq.server.jdo.Producto;
import es.deusto.spq.server.jdo.TipoProducto;
import es.deusto.spq.server.jdo.User;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ExampleClient {

	protected static final Logger logger = LogManager.getLogger();

	private static final String USER = "dipina";
	private static final String PASSWORD = "dipina";
	
	protected static boolean admin;
	private Client client;
	private static WebTarget webTarget;


	public ExampleClient(String hostname, String port) {
		client = ClientBuilder.newClient();
		webTarget = client.target(String.format("http://%s:%s/rest/resource", hostname, port));
	}

	public static void registerUser(String login, String password) {
		WebTarget registerUserWebTarget = webTarget.path("register");
		Invocation.Builder invocationBuilder = registerUserWebTarget.request(MediaType.APPLICATION_JSON);

		UserData userData = new UserData();
		userData.setLogin(login);
		userData.setPassword(password);
		Response response = invocationBuilder.post(Entity.entity(userData, MediaType.APPLICATION_JSON));
		if (response.getStatus() != Status.OK.getStatusCode()) {
			logger.error("Error connecting with the server. Code: {}", response.getStatus());
		} else {
			logger.info("User correctly registered");
		}
	}

	// FALTA POR HACER
	public static void loginUser(String login, String password) {
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
			} else if (valor == 1) {
				logger.info("La constrasenia es incorrecta!");
			} else if (valor == 2) {
				admin=false;
				logger.info("Usuario logeado correctamente!");
			} else if (valor == 3) {
				admin=true;
				logger.info("Usuario administrador logeado correctamente!");
			}

			logger.info("Usuario logeado correctamente!");
		}

	}

	// Falta por verificar
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

	public void sayMessage(String login, String password, String message) {
		WebTarget sayHelloWebTarget = webTarget.path("sayMessage");
		Invocation.Builder invocationBuilder = sayHelloWebTarget.request(MediaType.APPLICATION_JSON);

		DirectMessage directMessage = new DirectMessage();
		UserData userData = new UserData();
		userData.setLogin(login);
		userData.setPassword(password);

		directMessage.setUserData(userData);

		MessageData messageData = new MessageData();
		messageData.setMessage(message);
		directMessage.setMessageData(messageData);

		Response response = invocationBuilder.post(Entity.entity(directMessage, MediaType.APPLICATION_JSON));
		if (response.getStatus() != Status.OK.getStatusCode()) {
			logger.error("Error connecting with the server. Code: {}", response.getStatus());
		} else {
			String responseMessage = response.readEntity(String.class);
			logger.info("* Message coming from the server: '{}'", responseMessage);
		}
	}

	public static void borrarProducto(String nombre) {
		WebTarget registerUserWebTarget = webTarget.path("register");
		Invocation.Builder invocationBuilder = registerUserWebTarget.request(MediaType.APPLICATION_JSON);

		Producto productoData = new Producto();
		productoData.setNombre(nombre);

		Response response = invocationBuilder.post(Entity.entity(productoData, MediaType.APPLICATION_JSON));
		if (response.getStatus() != Status.OK.getStatusCode()) {
			logger.error("Error connecting with the server. Code: {}", response.getStatus());
		} else {
			logger.info("User correctly registered");
		}
	}

	public static void editarProducto(String tipo, int stock, int precio) {
		WebTarget registerUserWebTarget = webTarget.path("register");
		Invocation.Builder invocationBuilder = registerUserWebTarget.request(MediaType.APPLICATION_JSON);

		Producto productoData = new Producto();
		productoData.setTipo(TipoProducto.valueOf(tipo));
		productoData.setStock(stock);
		productoData.setPrecio(precio);

		Response response = invocationBuilder.post(Entity.entity(productoData, MediaType.APPLICATION_JSON));
		if (response.getStatus() != Status.OK.getStatusCode()) {
			logger.error("Error connecting with the server. Code: {}", response.getStatus());
		} else {
			logger.info("User correctly registered");
		}
	}

	public static void main(String[] args) {
		if (args.length != 2) {
			logger.info("Use: java Client.Client [host] [port]");
			System.exit(0);
		}

		String hostname = args[0];
		String port = args[1];

		ExampleClient exampleClient = new ExampleClient(hostname, port);

		exampleClient.registerUser(USER, PASSWORD);
		exampleClient.sayMessage(USER, PASSWORD, "This is a test!...");
		VentanaLogin v1 = new VentanaLogin();
		v1.setVisible(true);
	}
}