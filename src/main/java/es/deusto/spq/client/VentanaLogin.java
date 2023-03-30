package es.deusto.spq.client;

import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.font.TextAttribute;
import java.util.Map;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import es.deusto.spq.pojo.UserData;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class VentanaLogin {

	JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaLogin window = new VentanaLogin();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public VentanaLogin() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		
		//Panel principal
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		//Boton
		JButton btnLogin = new JButton("Iniciar Sesión");
		btnLogin.setBounds(165, 170, 117, 21);
		Utilidades.modifyButton(btnLogin);
		panel.add(btnLogin);
		
		//Componentes de texto y demas
		JTextField textFieldUsuario = new JTextField();
		textFieldUsuario.setBounds(200, 56, 115, 19);
		panel.add(textFieldUsuario);
		Utilidades.modifyTextField(textFieldUsuario);
		textFieldUsuario.setColumns(10);
		
		JPasswordField passwordFieldContraseña = new JPasswordField();
		passwordFieldContraseña.setBounds(200, 101, 115, 19);
		panel.add(passwordFieldContraseña);
		Utilidades.modifyPasswordField(passwordFieldContraseña);
		passwordFieldContraseña.setColumns(10);
		
		JLabel lblUsuario = new JLabel("Usuario:");
		lblUsuario.setHorizontalAlignment(SwingConstants.CENTER);
		lblUsuario.setBounds(100, 60, 67, 13);
		lblUsuario.setFont(new Font("Segoe UI", Font.BOLD, 13));
		panel.add(lblUsuario);
		
		JLabel lblContraseña = new JLabel("Contraseña:");
		lblContraseña.setHorizontalAlignment(SwingConstants.CENTER);
		lblContraseña.setBounds(100, 105, 81, 13);
		lblContraseña.setFont(new Font("Segoe UI", Font.BOLD, 13));
		panel.add(lblContraseña);
		
		JLabel loginlabel = new JLabel("¿No tienes cuenta? Regístrate");
		loginlabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
		loginlabel.setBounds(135, 210, 180, 23);
		panel.add(loginlabel);
		
		//Acciones de los componentes
		btnLogin.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ExampleClient.loginUser(textFieldUsuario.getText(), passwordFieldContraseña.getText());
			}
		});
		// MouseListener loginlabel
		loginlabel.addMouseListener(new MouseAdapter() {
			@SuppressWarnings({ "unchecked", "rawtypes" })
			public void mouseEntered(MouseEvent evt) {
				Font font = loginlabel.getFont();
				Map attributes = font.getAttributes();
				attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
				loginlabel.setFont(font.deriveFont(attributes));
				loginlabel.setForeground(new Color(20,115,191));
				loginlabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}

			public void mouseExited(MouseEvent evt) {
				loginlabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
				loginlabel.setForeground(Color.BLACK);
			}
		});
				
		loginlabel.addMouseListener(new MouseAdapter() {	
			@Override
			public void mouseClicked(MouseEvent e) {
//				ventanaActual.dispose();
//				VentanaLogin v1 = new VentanaLogin(MainProgram.loginController);
//				v1.setVisible(true);	
			}
		});
	}
}
