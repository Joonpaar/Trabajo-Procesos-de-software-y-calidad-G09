package es.deusto.spq.pojo;

public class UserData {

    private String login;
    private String password;
    private int tipoUser;
    private int valoracion;

   

	public UserData() {
        // required by serialization
    }

    public String getLogin() {
        return this.login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public int getTipoUser() {
		return tipoUser;
	}

	public void setTipoUser(int tipoUser) {
		this.tipoUser = tipoUser;
	}

    public int getValoracion() {
		return valoracion;
	}

	public void setValoracion(int valoracion) {
		this.valoracion = valoracion;
	}

	public String toString() {
        return "[login=" + login + ", password=" + password + ", tipoUser=" + tipoUser + ", valoracion=" + valoracion + "]";
    }
}