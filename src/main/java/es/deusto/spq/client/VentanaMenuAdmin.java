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
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Color;

public class VentanaMenuAdmin extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaMenuAdmin frame = new VentanaMenuAdmin();
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
	public VentanaMenuAdmin() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 350, 350);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBackground(Color.DARK_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		ImageIcon icono = new ImageIcon("src\\main\\java\\Imagenes\\B.png");
		Image imagenIcono = icono.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH);
		ImageIcon iconoRedimensionado = new ImageIcon(imagenIcono);
		setIconImage(iconoRedimensionado.getImage());

		JPanel panelNorte = new JPanel();
		panelNorte.setBackground(Color.DARK_GRAY);
		contentPane.add(panelNorte, BorderLayout.NORTH);

		JLabel lblBienvenida = new JLabel("BIENVENIDO ADMIN");
		lblBienvenida.setForeground(Color.WHITE);
		lblBienvenida.setFont(new Font("Segoe UI", Font.BOLD, 14));
		panelNorte.add(lblBienvenida);

		JPanel panel = new JPanel();
		panel.setBackground(Color.DARK_GRAY);
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);

		JButton btnAnyadir = new JButton("AÑADIR PRODUCTO");
		btnAnyadir.setBounds(76, 28, 162, 23);
		Utilidades.modifyButton(btnAnyadir);
		panel.add(btnAnyadir);

		JButton btnEditar = new JButton("EDITAR/BORRAR PRODUCTO");
		btnEditar.setBounds(58, 84, 202, 23);
		Utilidades.modifyButton(btnEditar);
		panel.add(btnEditar);
		
		JButton btnEstadisticas = new JButton("DATOS");
		btnEstadisticas.setBounds(76, 140, 162, 23);
		Utilidades.modifyButton(btnEstadisticas);
		panel.add(btnEstadisticas);

		JButton btnVolver = new JButton("VOLVER");
		btnVolver.setBounds(76, 220, 162, 23);
		Utilidades.modifyButton(btnVolver);
		panel.add(btnVolver);

		btnAnyadir.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				VentanaAnyadir ventana = new VentanaAnyadir();
				ventana.setVisible(true);
				dispose();

			}
		});
		
		btnEditar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				VentanaEditarBorrar ventana = new VentanaEditarBorrar();
				ventana.setVisible(true);
				dispose();

			}
		});
		
		btnVolver.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				VentanaCatalogo ventana = new VentanaCatalogo();
				ventana.setVisible(true);
				dispose();

			}
		});
		
		btnEstadisticas.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				VentanaEstadisticas ventana = new VentanaEstadisticas();
				ventana.setVisible(true);
				dispose();

			}
		});
	}
}
