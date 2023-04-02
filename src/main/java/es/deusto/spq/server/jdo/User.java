package es.deusto.spq.server.jdo;

import java.util.Set;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.PrimaryKey;
import javax.jdo.annotations.Join;
import javax.jdo.annotations.Persistent;
import java.util.HashSet;

@PersistenceCapable
public class User {
	@PrimaryKey
	String login=null;
	String password=null;
	int tipoUser;	
	
	
	public User(String login, String password) {
		this.login = login;
		this.password = password;
	}
	
	
	
	public User(String login, String password, int tipoUser) {
		super();
		this.login = login;
		this.password = password;
		this.tipoUser = tipoUser;
	}

	public int getTipoUser() {
		return tipoUser;
	}

	public void setTipoUser(int tipoUser) {
		this.tipoUser = tipoUser;
	}

	public String getLogin() {
		return this.login;
	}
	
	public String getPassword() {
		return this.password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
}
