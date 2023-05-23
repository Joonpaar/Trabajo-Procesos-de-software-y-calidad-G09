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
	private JTextField textDinero;
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
		
		JLabel lblNewLabel = new JLabel("ESTADÍSTICAS");
		panel.add(lblNewLabel);
		
		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("Selecciona un cliente:");
		lblNewLabel_1.setBounds(59, 41, 129, 13);
		panel_1.add(lblNewLabel_1);
		
		JComboBox<String> comboBox = new JComboBox<>();
		for (User u : Cliente.getUsuarios()) {
			comboBox.addItem(u.getLogin());
		}
		comboBox.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		comboBox.setBackground(Color.WHITE);
		comboBox.setBounds(198, 37, 114, 17);
		panel_1.add(comboBox);
		
		JLabel lblNewLabel_2 = new JLabel("Dinero gastado:");
		lblNewLabel_2.setBounds(59, 107, 129, 13);
		panel_1.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("Valoración realizada:");
		lblNewLabel_3.setBounds(59, 163, 129, 13);
		panel_1.add(lblNewLabel_3);
		
		textDinero = new JTextField();
		textDinero.setBounds(198, 104, 114, 16);
		panel_1.add(textDinero);
		textDinero.setColumns(10);
		
		textValoracion = new JTextField();
		textValoracion.setBounds(198, 160, 114, 16);
		panel_1.add(textValoracion);
		textValoracion.setColumns(10);
		
		JButton btnCalcular = new JButton("Calcular");
		btnCalcular.setBounds(331, 37, 85, 21);
		panel_1.add(btnCalcular);
		
		JButton btnVolver = new JButton("VOLVER");
		btnVolver.setBounds(176, 238, 85, 21);
		panel_1.add(btnVolver);
		
		textDinero.setText(comboBox.getSelectedItem().toString());
		
		btnCalcular.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				List<Compra> comprasUsuario = Cliente.getComprasDelUsuario(comboBox.getSelectedItem().toString());
				
				if (comprasUsuario != null) {
					int suma = 0;
					for (Compra c : comprasUsuario) {
						for (String p : c.getProductos()) {
							suma = suma + Cliente.getProducto(p).getPrecio();
						}
					}
					textDinero.setText(suma + "");
				}
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
