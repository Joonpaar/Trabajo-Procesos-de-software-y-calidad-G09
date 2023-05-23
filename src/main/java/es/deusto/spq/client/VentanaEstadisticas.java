package es.deusto.spq.client;

import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import es.deusto.spq.server.jdo.Compra;
import es.deusto.spq.server.jdo.User;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JLabel;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class VentanaEstadisticas extends JFrame {

	private JPanel contentPane;
	private JTextField textContra;
	private JTextField textTipoUsuario;
	private JTextField textValoracion;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaEstadisticas frame = new VentanaEstadisticas();
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
	public VentanaEstadisticas() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 339);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		contentPane.setBackground(Color.DARK_GRAY);
		
		ImageIcon icono = new ImageIcon("src\\main\\java\\Imagenes\\B.png");
		Image imagenIcono = icono.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH);
		ImageIcon iconoRedimensionado = new ImageIcon(imagenIcono);
		setIconImage(iconoRedimensionado.getImage());
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.DARK_GRAY);
		contentPane.add(panel, BorderLayout.NORTH);
		
		JLabel lblNewLabel = new JLabel("DATOS");
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
		panel.add(lblNewLabel);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.DARK_GRAY);
		contentPane.add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("Selecciona un usuario:");
		lblNewLabel_1.setForeground(Color.WHITE);
		lblNewLabel_1.setFont(new Font("Segoe UI", Font.BOLD, 13));
		lblNewLabel_1.setBounds(44, 41, 144, 13);
		panel_1.add(lblNewLabel_1);
		
		JComboBox<String> comboBox = new JComboBox<>();
		for (User u : Cliente.getUsuarios()) {
			if (!u.getLogin().contains("0")) {
				comboBox.addItem(u.getLogin());
			}
		}
		comboBox.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		comboBox.setBackground(Color.WHITE);
		comboBox.setBounds(198, 37, 114, 17);
		panel_1.add(comboBox);
		
		JLabel lblContrasenia = new JLabel("Constraseña: ");
		lblContrasenia.setForeground(Color.WHITE);
		lblContrasenia.setBounds(44, 100, 129, 13);
		lblContrasenia.setFont(new Font("Segoe UI", Font.BOLD, 13));
		panel_1.add(lblContrasenia);
		
		JLabel lblTipoUsuario = new JLabel("Tipo de usuario :");
		lblTipoUsuario.setForeground(Color.WHITE);
		lblTipoUsuario.setBounds(44, 138, 129, 13);
		lblTipoUsuario.setFont(new Font("Segoe UI", Font.BOLD, 13));
		panel_1.add(lblTipoUsuario);
		
		textContra = new JTextField();
		textContra.setBounds(198, 99, 114, 16);
		Utilidades.modifyTextField(textContra, false);
		panel_1.add(textContra);
		textContra.setColumns(10);
		
		textTipoUsuario = new JTextField();
		textTipoUsuario.setBounds(198, 137, 114, 16);
		Utilidades.modifyTextField(textTipoUsuario, false);
		panel_1.add(textTipoUsuario);
		textTipoUsuario.setColumns(10);
		
		JButton btnBuscar = new JButton("Buscar");
		btnBuscar.setBounds(331, 37, 85, 21);
		Utilidades.modifyButton(btnBuscar);
		panel_1.add(btnBuscar);
		
		JButton btnVolver = new JButton("VOLVER");
		btnVolver.setBounds(176, 238, 85, 21);
		Utilidades.modifyButton(btnVolver);
		panel_1.add(btnVolver);
		
		JLabel lblValoracion = new JLabel("Valoración realizada:");
		lblValoracion.setForeground(Color.WHITE);
		lblValoracion.setBounds(44, 177, 129, 13);
		lblValoracion.setFont(new Font("Segoe UI", Font.BOLD, 13));
		panel_1.add(lblValoracion);
		
		textValoracion = new JTextField();
		textValoracion.setColumns(10);
		textValoracion.setBounds(198, 176, 114, 16);
		Utilidades.modifyTextField(textValoracion, false);
		panel_1.add(textValoracion);
		
		btnBuscar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				textContra.setText(Cliente.getUsuarioPorNombre(comboBox.getSelectedItem().toString()).getPassword());
				
				if (Cliente.getUsuarioPorNombre(comboBox.getSelectedItem().toString()).getTipoUser() == 1) {
					textTipoUsuario.setText("Admin");
				}else {
					textTipoUsuario.setText("Usuario");
				}
				textTipoUsuario.setText(Cliente.getUsuarioPorNombre(comboBox.getSelectedItem().toString()).getTipoUser() + "");
				textValoracion.setText(Cliente.getUsuarioPorNombre(comboBox.getSelectedItem().toString()).getValoracion() + "");
				
			}
		});
		
		btnVolver.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				VentanaMenuAdmin v1 = new VentanaMenuAdmin();
				v1.setVisible(true);
				
			}
		});
		
	}
}
