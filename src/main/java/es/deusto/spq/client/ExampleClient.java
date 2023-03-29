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
import es.deusto.spq.server.jdo.User;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ExampleClient {

	protected static final Logger logger = LogManager.getLogger();

	private static final String USER = "dipina";
	private static final String PASSWORD = "dipina";


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
	
	//FALTA POR HACER
	public static void loginUser(String login, String password) {
		WebTarget loginUserWebTarget = webTarget.path("login");
		Invocation.Builder invocationBuilder = loginUserWebTarget.request(MediaType.APPLICATION_JSON);
		
		UserData userData = new UserData();
		userData.setLogin(login);
		userData.setPassword(password);
		Response response = invocationBuilder.post(Entity.entity(userData, MediaType.APPLICATION_JSON));
//		Response response = builder.post(null);
//		Response response = invocationBuilder.pos
		if (response.getStatus() != Status.OK.getStatusCode()) {
			logger.error("Error connecting with the server. Code: {}", response.getStatus());
//			List<User> users = response.readEntity(listType);

		} else {
			User usuario = response.readEntity(User.class);
			logger.info("User logeado correctamente: " + usuario.getTipoUser());
			logger.info("Usuario logeado correctamente!");
		}
	}
	
	public static void getProductos() {
		WebTarget getProductosUserWebTarget = webTarget.path("getProductos");
		Invocation.Builder invocationBuilder = getProductosUserWebTarget.request(MediaType.APPLICATION_JSON);
		logger.info("Usuario logeado correctamente1!");
		Response response = invocationBuilder.post(null);
		logger.info("Usuario logeado correctamente!");
//		Response response = invocationBuilder.pos
		if (response.getStatus() != Status.OK.getStatusCode()) {
			logger.error("Error connecting with the server. Code: {}", response.getStatus());

//		Response response = invocationBuilder.post(Entity.entity(, MediaType.APPLICATION_JSON));
//		if (response.getStatus() != Status.OK.getStatusCode()) {
//			logger.error("Error connecting with the server. Code: {}", response.getStatus());
		} else {
//			GenericType<List<User>> listType = new GenericType<List<User>>(){};
//			List<Producto> productos = response.readEntity(listType);
			logger.info("Usuario logeado correctamente3!");
			GenericType<ArrayList<Producto>> listType = new GenericType<ArrayList<Producto>>(){};
			ArrayList<Producto> productos = response.readEntity(listType);
			logger.info("Usuario logeado correctamente!4");
			int i=0;
			logger.info("Usuario logeado correctamente!5" + productos.size());
			while (i<productos.size()) {
				logger.info("User logeado correctamente: " + i);
			}
//			logger.info("User logeado correctamente: " + usuario.getTipoUser());
//			logger.info("Usuario logeado correctamente!");
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
			logger.error("Error connecting with the server. Code: {}",response.getStatus());
		} else {
			String responseMessage = response.readEntity(String.class);
			logger.info("* Message coming from the server: '{}'", responseMessage);
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
		exampleClient.getProductos();
//		exampleClient.registerUser(USER, PASSWORD);
//		exampleClient.sayMessage(USER, PASSWORD, "This is a test!...");
//		VentanaLogin window = new VentanaLogin();
//		window.frame.setVisible(true);
	}
}