package es.deusto.spq.client;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import es.deusto.spq.server.jdo.Carro;
import es.deusto.spq.server.jdo.Compra;
import es.deusto.spq.server.jdo.Producto;

public class VentanaCarrito extends JFrame {

	private JPanel contentPane;
	public static DefaultTableModel modeloTablaProductos;
	private JTable tablaCarro;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaCarrito frame = new VentanaCarrito();
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
	public VentanaCarrito() {
		System.out.println(VentanaCatalogo.cli);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 808, 520);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.CENTER);
		
		JButton btnVolver = new JButton("VOLVER");
		Utilidades.modifyButton(btnVolver);
		contentPane.add(btnVolver);

		setContentPane(contentPane);
		
		modeloTablaProductos=new DefaultTableModel();
		String [] nombreColumnas = {"Nombre", "Tipo", "Stock", "Precio"};
		modeloTablaProductos.setColumnIdentifiers(nombreColumnas);
		Carro carro = Cliente.getCarro(VentanaCatalogo.cli);
		Producto p=new Producto();
		for (int i=0;i < carro.getProductos().size(); i++) {
			p=Cliente.getProducto(carro.getProductos().get(i));
			String [] pr = {p.getNombre(), String.valueOf(p.getTipo()),String.valueOf(p.getPrecio()), String.valueOf(carro.getCantidades().get(i)), };
			modeloTablaProductos.addRow(pr);
		}
		tablaCarro= new JTable(modeloTablaProductos);
		
		JScrollPane scrollTablaProductos = new JScrollPane(tablaCarro);
		panel_1.add(scrollTablaProductos);
		
		btnVolver.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				VentanaCatalogo v1 = new VentanaCatalogo();
				v1.setVisible(true);
			}
		});
	}
	
	private void RealizarFactura() {
//		List<Compra> compras = Cliente.getComprasDelUsuario(VentanaCatalogo.cli);
//		for (Compra c: compras) {
//			for (int i=0;i < c.getProductos().size();i++) {
//				System.out.println(c.getProductos().get(i) + " y la cantidad es" + c.getCantidades().get(i));
//			}
//		}
		
		
//		PrintWriter pw = null;
//		try {
//			pw = new PrintWriter("FACTURA DE " + VentanaCatalogo.cli + ".txt");
//			pw.println("FACTURA DE " + VentanaCatalogo.cli);
//			double sumaTotal = 0;
//			double suma1 = 0;
//			int i = 1;
//			ArrayList<Producto> al = null; //AQUI HAY QUE CONSEGUIR LOS PRODUCTOS COMPRADOS POR EL CLIENTE EN LA BD
//			for (Producto p: al) {
//				suma1 = (p.getPrecio() * p.getStock());
//				sumaTotal = sumaTotal + (p.getPrecio() * p.getStock());
//				pw.println(i + "- " + p.getNombre() + " " + p.getPrecio()+ " euros  " + p.getStock() + "      " + "Precio Del Mismo Articulo teniendo en cuenta las unidades compradas: " + suma1 + " euros");
//				i++;
//			}
//			pw.println("Precio total de la compra: " + sumaTotal + " euros");
//			
//			
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
	
	
	

}
