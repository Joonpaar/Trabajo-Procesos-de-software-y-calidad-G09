package es.deusto.spq.client;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
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
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 808, 520);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(84, 10, 462, 412);
		contentPane.add(panel_1);
		
		JButton btnVolver = new JButton("VOLVER");
		btnVolver.setBounds(564, 202, 115, 29);
		Utilidades.modifyButton(btnVolver);
		contentPane.add(btnVolver);
		
		JButton btnComprar = new JButton("COMPRAR");
		btnComprar.setBounds(564, 257, 115, 29);
		Utilidades.modifyButton(btnComprar);
		contentPane.add(btnComprar);
		
		JButton btnBorrar = new JButton("BORRAR");
		btnBorrar.setBounds(564, 303, 115, 29);
		Utilidades.modifyButton(btnBorrar);
		contentPane.add(btnBorrar);

		setContentPane(contentPane);
		
		modeloTablaProductos=new DefaultTableModel();
		String [] nombreColumnas = {"Nombre", "Tipo", "Pecio", "Cantidad"};
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
		
		btnBorrar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int fil = tablaCarro.getSelectedRow();
				String nombre = String.valueOf(modeloTablaProductos.getValueAt(fil, 0));
				Carro carro=Cliente.getCarro(VentanaCatalogo.cli);
				List<Integer> cants=carro.getCantidades();
				List<String> prods=carro.getProductos();
				
				int posicion = prods.indexOf(nombre);
				prods.remove(posicion);
				cants.remove(posicion);
				Cliente.actualizarCarro(prods, cants);
				
				try {modeloTablaProductos.removeRow(fil);
				} catch (ArrayIndexOutOfBoundsException e2) {
				}
				tablaCarro.repaint();
			}
		});
		
		btnVolver.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				VentanaCatalogo v1 = new VentanaCatalogo();
				v1.setVisible(true);
			}
		});
		btnComprar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				realizarFactura();
				List<Integer> cants=new ArrayList<>();
				List<String> prods=new ArrayList<>();
				for (int i = 0; i < modeloTablaProductos.getRowCount(); i++) {
					String prod=String.valueOf(modeloTablaProductos.getValueAt(i, 0));
					int cant=Integer.parseInt(String.valueOf(modeloTablaProductos.getValueAt(i, 3)));
					Producto producto=Cliente.getProducto(prod);
					if(producto.getStock()>=cant) {
						Cliente.editarProducto(producto.getNombre(), producto.getTipo().name(), producto.getStock()-cant, producto.getPrecio());
						cants.add(cant);
						prods.add(prod);
					}else {
				        JOptionPane.showMessageDialog(null, "No queda suficiente " + producto.getNombre() + " en stock.");
				        return;
					}	
				}
				if(prods.size()>0 && prods.size()==cants.size()) {
					Cliente.comprarProducto(prods, cants);
					prods.clear();
					cants.clear();
					Cliente.actualizarCarro(prods, cants);
			        JOptionPane.showMessageDialog(null, "Compra realizada.");
			        modeloTablaProductos.setRowCount(0);
				}else {
			        JOptionPane.showMessageDialog(null, "No tienes productos.");
				}
			}
		});
	}
	
	public void realizarFactura() {
		 FileWriter writer;
		 try {
            writer = new FileWriter("FACTURA DE "+VentanaCatalogo.cli+".txt");
            writer.write("FACTURA DE " + VentanaCatalogo.cli + "\n");
            double sumaTotal = 0;
            double suma = 0;
            int j=1;
            Carro carr = Cliente.getCarro(VentanaCatalogo.cli);
            for(int i=0; i<carr.getProductos().size(); i++){
                String nom = carr.getProductos().get(i);
                Producto p = Cliente.getProducto(nom);
                suma = (p.getPrecio()*(carr.getCantidades().get(i)));
                sumaTotal += suma;
                writer.write(j + "- "+p.getNombre()+"- "+p.getPrecio()+" euros - "+carr.getCantidades().get(i) +" unidades\n");
                j++;
            }
            writer.write("Precio total: "+sumaTotal+" euros");
            writer.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
		
		
//		PrintWriter pw = null;
//		try {
//			pw = new PrintWriter(new File("./" + "FACTURA DE " + VentanaCatalogo.cli + ".txt"));
//			pw.println("FACTURA DE " + VentanaCatalogo.cli);
//			pw.print("BUENOS DIAS");
//			double sumaTotal = 0;
//			double suma1 = 0;
//			int j = 1;
//			Carro carrit = Cliente.getCarro(VentanaCatalogo.cli);
//			for (int i=0; i<carrit.getProductos().size();i++) {
//				String nom = carrit.getProductos().get(i);
//				Producto p = Cliente.getProducto(nom);
//				suma1 = (p.getPrecio() * carrit.getCantidades().get(i));
//				sumaTotal = sumaTotal + (p.getPrecio() * carrit.getCantidades().get(i));
//				pw.println(j + "- " + p.getNombre() + " " + p.getPrecio()+ " euros  " + carrit.getCantidades().get(i) + "      " + "Precio Del Mismo Articulo teniendo en cuenta las unidades compradas: " + suma1 + " euros");
//				j++;
//			}
//			pw.println("Precio total de la compra: " + sumaTotal + " euros");
//			
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
	
	
	
}
