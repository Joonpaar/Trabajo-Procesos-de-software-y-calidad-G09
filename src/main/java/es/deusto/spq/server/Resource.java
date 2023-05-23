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
import es.deusto.spq.server.jdo.Carro;
import es.deusto.spq.server.jdo.Compra;
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
//				user = new User(userData.getLogin(), userData.getPassword());
				user = new User(userData.getLogin(), userData.getPassword(), 0, -1);
				pm.makePersistent(user);
				List<String> prods= new ArrayList<>();
				List<Integer> cants= new ArrayList<>();
				Carro carro=new Carro(user.getLogin(), prods, cants);
				pm.makePersistent(carro);
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
			System.out.println("  $ Error: " + ex.getMessage());
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
	public List<Producto> getProductosPorTIpo(TipoProducto tipo) {
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
	@Path("/getComprasDelUsuario")
	public List<Compra> getComprasDelUsu(String nombre) {
		List<Compra> comprasDelUsu = null;
		try{	
	        tx.begin();
	        
			
			Query<?> query = pm.newQuery("SELECT FROM " + Compra.class.getName() + " WHERE userLogin == '" + nombre + "'");
			query.setUnique(true);
			comprasDelUsu = (List<Compra>) query.execute();
			
			tx.commit();
	    }catch (Exception ex) {
			System.out.println("  $ Error querying a Reto: " + ex.getMessage());
	    }finally{
	        if (tx.isActive())
	        {
	            tx.rollback();
	        }
		}
		return comprasDelUsu;
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
	@POST
	@Path("/comprarProducto")
	public Response comprarProducto(Compra compraData) {
		try
	    {	
	        tx.begin();
			Compra compra=new Compra(compraData.getUser(), compraData.getFecha(),compraData.getProductos(), compraData.getCantidades());
			pm.makePersistent(compra);
			logger.info("Compra añadida: {}", compra);
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
	@Path("/getProducto")
	public Producto getProducto(String nombre) {
		Producto producto=null;
		try {
			producto = pm.getObjectById(Producto.class, nombre);
		
		} catch (Exception ex) {
			System.out.println("  $ Error: " + ex.getMessage());
			pm.close();
		}

		return producto;
	}
	
	@POST
	@Path("/getUsuarios")
	public List<User> getUsuarios() {
		
		List<User> usuarios = new ArrayList<>();

		try {
			tx.begin();
			
			Extent<User> userExtent = pm.getExtent(User.class, true);
			
			for (User u : userExtent) {
				usuarios.add(u);
			}
						
			tx.commit();
		} catch (Exception ex) {
			System.out.println("  $ Error: " + ex.getMessage());
		} finally {
			if (tx != null && tx.isActive()) {
				tx.rollback();
			}

			pm.close();
		}
		
		return usuarios;
	}
	
	@POST
	@Path("/getUsuarioPorNombre")
	public User getUsuarioPorNombre(String nombre) {
		User usuario = null;
		try {
			usuario = pm.getObjectById(User.class, nombre);
		
		} catch (Exception ex) {
			System.out.println("  $ Error: " + ex.getMessage());
			pm.close();
		}

		return usuario;
	}
	
	@POST
	@Path("/getCarro")
	public Carro getCarro(String nombre) {
		Carro carro=null;
		try {
			
			carro = pm.getObjectById(Carro.class, nombre);
		} catch (Exception ex) {
			System.out.println("  $ Error: " + ex.getMessage());
			pm.close();
		}
		if(carro.getCantidades()==null) {
			carro.setCantidades(new ArrayList<>());
			carro.setProductos(new ArrayList<>());
		}
		return carro;
	}
	
	@POST
	@Path("/editarUser")
	public Response editarUser(UserData userData) {
		try
	    {	
	        tx.begin();
	        logger.info("Checking whether the product exits or not: '{}'", userData.getLogin());
			User usuario = null;
			try {
				usuario = pm.getObjectById(User.class, userData.getLogin());
			} catch (javax.jdo.JDOObjectNotFoundException jonfe) {
				logger.info("Exception launched: {}", jonfe.getMessage());
			}
			logger.info("Usuario: {}", usuario);
			if (usuario == null) {
				logger.info("Error editando el usuario: {}", usuario);
			} else {
				logger.info("Editando producto: {}", usuario);
				usuario.setValoracion((userData.getValoracion()));
				pm.flush();
				logger.info("Producto editado: {}", usuario);
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
	@Path("/actualizarCarro")
	public Response actualizarCarro(Carro carroData) {
		try
	    {	
	        tx.begin();
			Carro carro = null;
			try {
				carro = pm.getObjectById(Carro.class, carroData.getUser());
			} catch (javax.jdo.JDOObjectNotFoundException jonfe) {
				logger.info("Exception launched: {}", jonfe.getMessage());
			}
			if (carro == null) {
				logger.info("Error actualizando el carro: {}", carro);
			} else {
				logger.info("Actualizando carro: {}", carro);
				carro.setUser(carroData.getUser());
				carro.setCantidades(carroData.getCantidades());
				carro.setProductos(carroData.getProductos());
				pm.flush();
				logger.info("Carro actualizado: {}", carro);
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
