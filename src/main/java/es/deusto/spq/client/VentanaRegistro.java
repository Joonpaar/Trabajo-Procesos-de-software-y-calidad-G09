package es.deusto.spq.client;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.SwingConstants;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;

public class VentanaRegistro extends JFrame {

	private JPanel contentPane;
	private JTextField txtNombre;
	private JPasswordField passwordField;
	private JPasswordField passwordField_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaRegistro frame = new VentanaRegistro();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public VentanaRegistro() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.NORTH);
		
		JLabel lblTitulo = new JLabel("REGISTRO");
		panel.add(lblTitulo);
		
		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(null);
		
		JLabel lblNombre = new JLabel("Nombre usuario");
		lblNombre.setHorizontalAlignment(SwingConstants.CENTER);
		lblNombre.setBounds(46, 41, 151, 14);
		panel_1.add(lblNombre);
		
		txtNombre = new JTextField();
		txtNombre.setBounds(229, 38, 86, 20);
		panel_1.add(txtNombre);
		txtNombre.setColumns(10);
		
		JLabel lblContrasenya = new JLabel("Contraseña");
		lblContrasenya.setHorizontalAlignment(SwingConstants.CENTER);
		lblContrasenya.setBounds(46, 81, 151, 14);
		panel_1.add(lblContrasenya);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(229, 78, 86, 20);
		panel_1.add(passwordField);
		
		JRadioButton radioAdmin = new JRadioButton("ADMIN");
		radioAdmin.setBounds(132, 163, 65, 23);
		panel_1.add(radioAdmin);
		
		JRadioButton radioUser = new JRadioButton("USUARIO");
		radioUser.setBounds(212, 163, 78, 23);
		panel_1.add(radioUser);
		
		ButtonGroup group = new ButtonGroup();
		group.add(radioAdmin);
		group.add(radioUser);
		
		JButton btnCrear = new JButton("REGISTRARSE");
		btnCrear.setBounds(154, 193, 118, 23);
		panel_1.add(btnCrear);
		
		passwordField_1 = new JPasswordField();
		passwordField_1.setBounds(229, 119, 86, 20);
		panel_1.add(passwordField_1);
		
		JLabel lblRepetirContra = new JLabel("Repita la contraseña");
		lblRepetirContra.setHorizontalAlignment(SwingConstants.CENTER);
		lblRepetirContra.setBounds(46, 122, 151, 14);
		panel_1.add(lblRepetirContra);
	}
}
