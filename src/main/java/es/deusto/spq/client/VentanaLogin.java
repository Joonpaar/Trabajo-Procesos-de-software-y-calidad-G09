package es.deusto.spq.client;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;

import javax.swing.ImageIcon;
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
		setBounds(100, 100, 450, 400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		setTitle("Login");
		ImageIcon icono = new ImageIcon("src\\main\\java\\Imagenes\\B.png");
		Image imagenIcono = icono.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH);
		ImageIcon iconoRedimensionado = new ImageIcon(imagenIcono);
		setIconImage(iconoRedimensionado.getImage());
		
		//Panel principal
		JPanel panel = new JPanel();
		panel.setBackground(Color.DARK_GRAY);
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		//Boton
		JButton btnLogin = new JButton("Iniciar Sesión");
		btnLogin.setBounds(165, 280, 117, 21);
		Utilidades.modifyButton(btnLogin);
		panel.add(btnLogin);
		
		//Componentes de texto y demas
		JTextField textFieldUsuario = new JTextField();
		textFieldUsuario.setBounds(200, 186, 115, 19);
		panel.add(textFieldUsuario);
		Utilidades.modifyTextField(textFieldUsuario, true);
		textFieldUsuario.setColumns(10);
		
		JPasswordField passwordFieldContraseña = new JPasswordField();
		passwordFieldContraseña.setBounds(200, 231, 115, 19);
		panel.add(passwordFieldContraseña);
		Utilidades.modifyPasswordField(passwordFieldContraseña);
		passwordFieldContraseña.setColumns(10);
		
		JLabel lblUsuario = new JLabel("Usuario:");
		lblUsuario.setForeground(Color.WHITE);
		lblUsuario.setHorizontalAlignment(SwingConstants.CENTER);
		lblUsuario.setBounds(100, 190, 67, 13);
		lblUsuario.setFont(new Font("Segoe UI", Font.BOLD, 13));
		panel.add(lblUsuario);
		
		JLabel lblContraseña = new JLabel("Contraseña:");
		lblContraseña.setForeground(Color.WHITE);
		lblContraseña.setHorizontalAlignment(SwingConstants.CENTER);
		lblContraseña.setBounds(100, 235, 81, 13);
		lblContraseña.setFont(new Font("Segoe UI", Font.BOLD, 13));
		panel.add(lblContraseña);
		
		JLabel registroLabel = new JLabel("¿No tienes cuenta? Regístrate");
		registroLabel.setForeground(Color.WHITE);
		registroLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
		registroLabel.setBounds(135, 325, 200, 23);
		panel.add(registroLabel);
		
		JLabel logo = new JLabel();
		logo.setBounds(100, 10, 234, 160);
		ImageIcon im = new ImageIcon("src\\main\\java\\Imagenes\\logoFinal.png");
		ImageIcon imDimensiones = new ImageIcon(im.getImage().getScaledInstance(logo.getWidth(), logo.getHeight(), Image.SCALE_DEFAULT));
		logo.setIcon(imDimensiones);
		panel.add(logo);
		
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
				registroLabel.setForeground(Color.white);
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
