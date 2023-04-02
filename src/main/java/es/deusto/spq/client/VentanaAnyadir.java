package es.deusto.spq.client;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import es.deusto.spq.server.jdo.TipoProducto;

import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JButton;

public class VentanaAnyadir extends JFrame {

	private JPanel contentPane;
	private JTextField txtNombre;
	private JTextField txtPrecio;
	private JTextField txtStock;
	private JComboBox<TipoProducto> combo;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaAnyadir frame = new VentanaAnyadir();
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
	public VentanaAnyadir() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setLocationRelativeTo(null);
		setResizable(false);
		setTitle("Añadir Producto");

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JLabel lblNombre = new JLabel("Nombre");
		lblNombre.setBounds(29, 44, 55, 14);
		lblNombre.setFont(new Font("Segoe UI", Font.BOLD, 13));
		panel.add(lblNombre);
		
		JLabel lblPrecio = new JLabel("Precio");
		lblPrecio.setBounds(29, 138, 46, 14);
		lblPrecio.setFont(new Font("Segoe UI", Font.BOLD, 13));
		panel.add(lblPrecio);
		
		JLabel lblStock = new JLabel("Stock");
		lblStock.setBounds(225, 44, 46, 14);
		lblStock.setFont(new Font("Segoe UI", Font.BOLD, 13));
		panel.add(lblStock);
		
		JLabel lblTipo = new JLabel("Tipo");
		lblTipo.setBounds(225, 138, 46, 14);
		lblTipo.setFont(new Font("Segoe UI", Font.BOLD, 13));
		panel.add(lblTipo);
		
		txtNombre = new JTextField();
		txtNombre.setBounds(93, 41, 105, 19);
		Utilidades.modifyTextField(txtNombre, false);
		panel.add(txtNombre);
		txtNombre.setColumns(10);
		
		txtPrecio = new JTextField();
		txtPrecio.setBounds(93, 135, 105, 19);
		Utilidades.modifyTextField(txtPrecio, false);
		panel.add(txtPrecio);
		txtPrecio.setColumns(10);
		
		txtStock = new JTextField();
		txtStock.setBounds(275, 41, 105, 19);
		Utilidades.modifyTextField(txtStock, false);
		panel.add(txtStock);
		txtStock.setColumns(10);
		
		combo = new JComboBox<>(TipoProducto.values());
		combo.setBounds(275, 135, 86, 20);
		panel.add(combo);
		
		
		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.NORTH);
		
		JLabel lblTitulo = new JLabel("INDIQUE LOS DATOS DEL PRODUCTO");
		lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 13));
		panel_1.add(lblTitulo);
		
		JPanel panel_2 = new JPanel();
		contentPane.add(panel_2, BorderLayout.SOUTH);
		
		JButton btnVolver = new JButton("VOLVER");
		Utilidades.modifyButton(btnVolver);
		panel_2.add(btnVolver);
		
		JButton btnAnyadir = new JButton("AÑADIR");
		Utilidades.modifyButton(btnAnyadir);
		panel_2.add(btnAnyadir);
		
		btnAnyadir.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ExampleClient.insertarProducto(txtNombre.getText(),Integer.parseInt(txtPrecio.getText()), Integer.parseInt(txtStock.getText()),(TipoProducto) combo.getSelectedItem());
				dispose();
				VentanaAdmin v1 = new VentanaAdmin();
				v1.setVisible(true);
			}
		});
		
		btnVolver.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				VentanaAdmin v1 = new VentanaAdmin();
				v1.setVisible(true);	
			}
		});
	}
}
