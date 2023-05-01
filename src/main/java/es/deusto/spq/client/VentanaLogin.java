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
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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

public class VentanaLogin extends JFrame{
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaLogin window = new VentanaLogin();
					window.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public VentanaLogin() {
		setBounds(100, 100, 450, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		setTitle("Login");
		
		//Panel principal
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.CENTER);
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
		Utilidades.modifyTextField(textFieldUsuario, true);
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
		
		JLabel registroLabel = new JLabel("¿No tienes cuenta? Regístrate");
		registroLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
		registroLabel.setBounds(135, 210, 200, 23);
		panel.add(registroLabel);
		
		//Acciones de los componentes
		btnLogin.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				boolean login=Cliente.loginUser(textFieldUsuario.getText(), passwordFieldContraseña.getText());
				if(login) {
					VentanaCatalogo.cli = textFieldUsuario.getText();
					VentanaCatalogo frame = new VentanaCatalogo();
					frame.setVisible(true);
					dispose();
				}
			}
		});
		
		passwordFieldContraseña.addKeyListener(new KeyAdapter() {	
			
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
		            // Hacer clic en el botón de inicio de sesión
		            btnLogin.doClick();
		        }
				
			}
			
		});
		
		// MouseListeners registroLabel
		registroLabel.addMouseListener(new MouseAdapter() {
			@SuppressWarnings({ "unchecked", "rawtypes" })
			public void mouseEntered(MouseEvent evt) {
				Font font = registroLabel.getFont();
				Map attributes = font.getAttributes();
				attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
				registroLabel.setFont(font.deriveFont(attributes));
				registroLabel.setForeground(new Color(20,115,191));
				registroLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}

			public void mouseExited(MouseEvent evt) {
				registroLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
				registroLabel.setForeground(Color.BLACK);
			}
		});
				
		registroLabel.addMouseListener(new MouseAdapter() {	
			@Override
			public void mouseClicked(MouseEvent e) {
				dispose();
				VentanaRegistro v1 = new VentanaRegistro();
				v1.setVisible(true);	
			}
		});
	}
}
