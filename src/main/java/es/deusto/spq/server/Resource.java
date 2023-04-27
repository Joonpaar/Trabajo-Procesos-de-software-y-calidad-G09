package es.deusto.spq.server;

import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.Extent;
import javax.jdo.JDOHelper;
import javax.jdo.Transaction;

import es.deusto.spq.server.jdo.User;
import es.deusto.spq.server.jdo.Producto;
import es.deusto.spq.server.jdo.TipoProducto;
import es.deusto.spq.pojo.UserData;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

@Path("/resource")
@Produces(MediaType.APPLICATION_JSON)
public class Resource {

	protected static final Logger logger = LogManager.getLogger();

	private int cont = 0;
	private PersistenceManager pm=null;
	private Transaction tx=null;

	public Resource() {
		PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory("datanucleus.properties");
		this.pm = pmf.getPersistenceManager();
		this.tx = pm.currentTransaction();
	}
	
	@POST
	@Path("/register")
	public Response registerUser(UserData userData) {
		try
        {	
            tx.begin();
            logger.info("Checking whether the user already exits or not: '{}'", userData.getLogin());
			User user = null;
			try {
				user = pm.getObjectById(User.class, userData.getLogin());
			} catch (javax.jdo.JDOObjectNotFoundException jonfe) {
				logger.info("Exception launched: {}", jonfe.getMessage());
			}
			logger.info("User: {}", user);
			if (user != null) {
				logger.info("Setting password user: {}", user);
				user.setPassword(userData.getPassword());
				logger.info("Password set user: {}", user);
			} else {
				logger.info("Creating user: {}", user);
				user = new User(userData.getLogin(), userData.getPassword());
				pm.makePersistent(user);					 
				logger.info("User created: {}", user);
			}
			tx.commit();
			return Response.ok().build();
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
      
		}
	}
	
	@POST
	@Path("/insertarProducto")
	public Response insertarProducto(Producto productoData) {
		try
        {	
            tx.begin();
            logger.info("Checking whether the product already exits or not: '{}'", productoData.getNombre());
			Producto producto = null;
			try {
				producto = pm.getObjectById(Producto.class, productoData.getNombre());
			} catch (javax.jdo.JDOObjectNotFoundException jonfe) {
				logger.info("Exception launched: {}", jonfe.getMessage());
			}
			logger.info("Product: {}", producto);
			if (producto != null) {
				logger.info("The product already exits: {}", producto);
			} else {
				logger.info("Creating producto: {}", producto);
				producto = new Producto(productoData.getNombre(), productoData.getPrecio(), productoData.getStock(), productoData.getTipo());
				pm.makePersistent(producto);					 
				logger.info("Product created: {}", producto);
			}
			tx.commit();
			return Response.ok().build();
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
      
		}
	}
	
	
	@POST
	@Path("/login")
	public int loginUser(UserData userData) {
		try
        {	
            tx.begin();
            logger.info("Checking whether the user already exits or not: '{}'", userData.getLogin());
			User user = null;
			try {
				user = pm.getObjectById(User.class, userData.getLogin());
			} catch (javax.jdo.JDOObjectNotFoundException jonfe) {
				logger.info("Exception launched: {}", jonfe.getMessage());
			}
			logger.info("User: {}", user);
			if (user != null) {
				if (user.getPassword().equals(userData.getPassword())) {
					logger.info("Setting password user: {}", user);
					UserData userDat = new UserData();
					userDat.setLogin(user.getLogin());
					userDat.setPassword(user.getPassword());
					userDat.setTipoUser(user.getTipoUser());
					if(user.getTipoUser()==1) {
						return 3;
					}
					return 2;
				}else
					return 1;
			} else {
				logger.info("No existe el usuario {}", user);			 
			}
			tx.commit();

			return 0;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
      
		}
	}
	
	
	@POST
	@Path("/getProductos")
	public List<Producto> getAll() {
		
		List<Producto> productos = new ArrayList<>();

		try {
			tx.begin();
			
			Extent<Producto> prodExtent = pm.getExtent(Producto.class, true);
			
			for (Producto p : prodExtent) {
				productos.add(p);
			}
						
			tx.commit();
		} catch (Exception ex) {
			System.out.println("  $ Error querying all users: " + ex.getMessage());
		} finally {
			if (tx != null && tx.isActive()) {
				tx.rollback();
			}

			pm.close();
		}
		
		return productos;
	}
	
	@POST
	@Path("/getProductosPorNombre")
	public List<Producto> getProductosPorNombre(String nombre) {
	    List<Producto> productos = new ArrayList<>();

	    try {
	        tx.begin();

	        Extent<Producto> prodExtent = pm.getExtent(Producto.class, true);

	        for (Producto p : prodExtent) {
	            if (p.getNombre().contains(nombre)) {
	                productos.add(p);
	            }
	        }

	        tx.commit();
	    } catch (Exception ex) {
	        System.out.println("  $ Error querying products by name: " + ex.getMessage());
	    } finally {
	        if (tx != null && tx.isActive()) {
	            tx.rollback();
	        }

	        pm.close();
	    }

	    return productos;
	}
	
	@POST
	@Path("/getProductosPorTipo")
	public List<Producto> getProductosPorNombre(TipoProducto tipo) {
	    List<Producto> productos = new ArrayList<>();

	    try {
	        tx.begin();

	        Extent<Producto> prodExtent = pm.getExtent(Producto.class, true);

	        for (Producto p : prodExtent) {
	            if (p.getTipo().equals(tipo)) {
	                productos.add(p);
	            }
	        }

	        tx.commit();
	    } catch (Exception ex) {
	        System.out.println("  $ Error querying products by type: " + ex.getMessage());
	    } finally {
	        if (tx != null && tx.isActive()) {
	            tx.rollback();
	        }

	        pm.close();
	    }

	    return productos;
	}

	@GET
	@Path("/hello")
	@Produces(MediaType.TEXT_PLAIN)
	public Response sayHello() {
		return Response.ok("Hello world!").build();
	}

	@POST
	@Path("/editarProducto")
	public Response editarProducto(Producto productoData) {
		try
	    {	
	        tx.begin();
	        logger.info("Checking whether the product exits or not: '{}'", productoData.getNombre());
			Producto producto = null;
			try {
				producto = pm.getObjectById(Producto.class, productoData.getNombre());
			} catch (javax.jdo.JDOObjectNotFoundException jonfe) {
				logger.info("Exception launched: {}", jonfe.getMessage());
			}
			logger.info("Product: {}", producto);
			if (producto == null) {
				logger.info("Error editando el producto: {}", producto);
			} else {
				logger.info("Editando producto: {}", producto);
				producto.setPrecio(productoData.getPrecio());
				producto.setStock(productoData.getStock());
				producto.setTipo(productoData.getTipo());
				pm.flush();
				logger.info("Producto editado: {}", producto);
			}
			tx.commit();
			return Response.ok().build();
	    }
	    finally
	    {
	        if (tx.isActive())
	        {
	            tx.rollback();
	        }
	  
		}
	}
	@POST
	@Path("/borrarProducto")
	public Response borrarProducto(Producto productoData) {
		try
	    {	
	        tx.begin();
	        logger.info("Checking whether the product exits or not: '{}'", productoData.getNombre());
			Producto producto = null;
			try {
				producto = pm.getObjectById(Producto.class, productoData.getNombre());
			} catch (javax.jdo.JDOObjectNotFoundException jonfe) {
				logger.info("Exception launched: {}", jonfe.getMessage());
			}
			logger.info("Product: {}", producto);
			if (producto == null) {
				logger.info("Error borrando el producto: {}", producto);
			} else {
				logger.info("Borrando producto: {}", producto);
				pm.deletePersistent(producto);
				logger.info("Producto borrado: {}", producto);
			}
			tx.commit();
			return Response.ok().build();
	    }
	    finally
	    {
	        if (tx.isActive())
	        {
	            tx.rollback();
	        }
	  
		}
	}
}
