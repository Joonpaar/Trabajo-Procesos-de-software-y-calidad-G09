package es.deusto.spq.client;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.SwingConstants;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
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
		setBounds(100, 100, 450, 345);
		contentPane = new JPanel();
		contentPane.setBackground(Color.DARK_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setLocationRelativeTo(null);
		setResizable(false);
		setTitle("Registro");
		ImageIcon icono = new ImageIcon("src\\main\\java\\Imagenes\\B.png");
		Image imagenIcono = icono.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH);
		ImageIcon iconoRedimensionado = new ImageIcon(imagenIcono);
		setIconImage(iconoRedimensionado.getImage());
		
		setBackground(Color.DARK_GRAY);
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.NORTH);
		panel.setBackground(Color.DARK_GRAY);
		
		JLabel lblTitulo = new JLabel("REGISTRO");
		lblTitulo.setForeground(Color.WHITE);
		lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 13));
		panel.add(lblTitulo);
		
		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.CENTER);
		panel_1.setBackground(Color.DARK_GRAY);
		panel_1.setLayout(null);
		
		JLabel lblNombre = new JLabel("Nombre usuario:");
		lblNombre.setForeground(Color.WHITE);
		lblNombre.setHorizontalAlignment(SwingConstants.CENTER);
		lblNombre.setBounds(46, 35, 151, 14);
		lblNombre.setFont(new Font("Segoe UI", Font.BOLD, 13));
		Utilidades.JLabelWithPopup(lblNombre);
		panel_1.add(lblNombre);
		
		txtNombre = new JTextField();
		txtNombre.setBounds(229, 33, 115, 19);
		Utilidades.modifyTextField(txtNombre, true);
		panel_1.add(txtNombre);
		txtNombre.setColumns(10);
		
		JLabel lblContrasenya = new JLabel("Contraseña:");
		lblContrasenya.setForeground(Color.WHITE);
		lblContrasenya.setHorizontalAlignment(SwingConstants.CENTER);
		lblContrasenya.setBounds(46, 76, 151, 14);
		lblContrasenya.setFont(new Font("Segoe UI", Font.BOLD, 13));
		Utilidades.JLabelWithPopup(lblContrasenya);
		panel_1.add(lblContrasenya);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(229, 73, 115, 19);
		Utilidades.modifyPasswordField(passwordField);
		panel_1.add(passwordField);
		
		JButton btnCrear = new JButton("REGISTRARSE");
		btnCrear.setBounds(154, 193, 118, 23);
		Utilidades.modifyButton(btnCrear);
		panel_1.add(btnCrear);
		
		passwordField_1 = new JPasswordField();
		passwordField_1.setBounds(229, 114, 115, 19);
		Utilidades.modifyPasswordField(passwordField_1);
		panel_1.add(passwordField_1);
		
		JLabel lblRepetirContra = new JLabel("Repita la contraseña:");
		lblRepetirContra.setForeground(Color.WHITE);
		lblRepetirContra.setHorizontalAlignment(SwingConstants.CENTER);
		lblRepetirContra.setBounds(46, 117, 151, 14);
		lblRepetirContra.setFont(new Font("Segoe UI", Font.BOLD, 13));
		Utilidades.JLabelWithPopup(lblRepetirContra);
		panel_1.add(lblRepetirContra);
		
		JPanel panel_2 = new JPanel();
		contentPane.add(panel_2, BorderLayout.SOUTH);
		panel_2.setBackground(Color.DARK_GRAY);
		
		JButton btnVolver = new JButton("VOLVER");
		panel_2.add(btnVolver);
		Utilidades.modifyButton(btnVolver);
		
		btnVolver.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				VentanaLogin v1 = new VentanaLogin();
				v1.setVisible(true);
			}
		});
		btnCrear.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(passwordField.getText().equals(passwordField_1.getText())) {
					Cliente.registerUser(txtNombre.getText(), passwordField.getText());
					dispose();
					VentanaLogin v=new VentanaLogin();
					v.setVisible(true);
				}else JOptionPane.showMessageDialog(null, "Las contraseñas no coinciden", "Error", JOptionPane.ERROR_MESSAGE);

				
				
			}
		});
	}
}
