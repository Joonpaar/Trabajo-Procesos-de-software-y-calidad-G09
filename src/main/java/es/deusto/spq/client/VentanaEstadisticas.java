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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JTextField;
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
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.NORTH);
		
		JLabel lblNewLabel = new JLabel("DATOS");
		panel.add(lblNewLabel);
		
		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("Selecciona un usuario:");
		lblNewLabel_1.setBounds(59, 41, 129, 13);
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
		lblContrasenia.setBounds(59, 101, 129, 13);
		panel_1.add(lblContrasenia);
		
		JLabel lblTipoUsuario = new JLabel("Tipo de usuario :");
		lblTipoUsuario.setBounds(59, 139, 129, 13);
		panel_1.add(lblTipoUsuario);
		
		textContra = new JTextField();
		textContra.setBounds(198, 99, 114, 16);
		panel_1.add(textContra);
		textContra.setColumns(10);
		
		textTipoUsuario = new JTextField();
		textTipoUsuario.setBounds(198, 137, 114, 16);
		panel_1.add(textTipoUsuario);
		textTipoUsuario.setColumns(10);
		
		JButton btnBuscar = new JButton("Buscar");
		btnBuscar.setBounds(331, 37, 85, 21);
		panel_1.add(btnBuscar);
		
		JButton btnVolver = new JButton("VOLVER");
		btnVolver.setBounds(176, 238, 85, 21);
		panel_1.add(btnVolver);
		
		JLabel lblValoracion = new JLabel("Valoración realizada:");
		lblValoracion.setBounds(59, 179, 129, 13);
		panel_1.add(lblValoracion);
		
		textValoracion = new JTextField();
		textValoracion.setColumns(10);
		textValoracion.setBounds(198, 176, 114, 16);
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
