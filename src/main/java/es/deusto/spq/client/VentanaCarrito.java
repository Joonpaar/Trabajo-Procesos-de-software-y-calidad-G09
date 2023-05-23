package es.deusto.spq.client;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;

import es.deusto.spq.server.jdo.Carro;
import es.deusto.spq.server.jdo.Compra;
import es.deusto.spq.server.jdo.Producto;
import es.deusto.spq.server.jdo.User;
import java.awt.Color;

public class VentanaCarrito extends JFrame {

	private JPanel contentPane;
	public static DefaultTableModel modeloTablaProductos;
	private JTable tablaCarro;
	
	//VARIABLES PARA LA VALORACION DEL SERVICIO
	private JProgressBar pb;
	private SpinnerModel modelosp;
	private JSpinner sp;

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
		contentPane.setBackground(Color.DARK_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		ImageIcon icono = new ImageIcon("src\\main\\java\\Imagenes\\B.png");
		Image imagenIcono = icono.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH);
		ImageIcon iconoRedimensionado = new ImageIcon(imagenIcono);
		setIconImage(iconoRedimensionado.getImage());
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.DARK_GRAY);
		panel_1.setBounds(84, 10, 462, 412);
		contentPane.add(panel_1);
		
		//INICIALIZACION DEL SPINNER
		JLabel lblValorar = new JLabel("VALORANOS: 0-100");
		lblValorar.setBounds(564, 110, 115, 29);
		contentPane.add(lblValorar);
		lblValorar.setVisible(false);
		
		
		modelosp = new SpinnerNumberModel(50, 0, 100, 1);
		sp = new JSpinner(modelosp);
		sp.setBackground(Color.DARK_GRAY);
		sp.setVisible(false);
		sp.setBounds(564, 147, 37, 30);
		contentPane.add(sp);
		
		pb = new JProgressBar(0, 100);
		pb.setBackground(Color.DARK_GRAY);
		pb.setValue(50);
		pb.setVisible(false);
		pb.setBounds(615, 147, 115, 29);
		contentPane.add(pb);
		
		
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
		
		sp.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				// TODO Auto-generated method stub
				int valor = (int) sp.getValue();
				pb.setValue(valor);
			}
		});
		
		pb.addMouseListener(new MouseAdapter() {
			
			
			@Override
			public void mouseClicked(MouseEvent e) {
				int resp = JOptionPane.showConfirmDialog(null,"¿ESTA SEGURO DE SU OPCION?");
				if(resp == JOptionPane.OK_OPTION) {
					int valor = pb.getValue();
					Cliente.editarUser(VentanaCatalogo.cli, valor);
					JOptionPane.showMessageDialog(null, "GRACIAS POR COLABORAR!!!", "GRACIAS!!!", JOptionPane.NO_OPTION);
					lblValorar.setVisible(false);
					sp.setVisible(false);
					pb.setVisible(false);

					
				}
				
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
			        User usuario = Cliente.getUsuarioPorNombre(VentanaCatalogo.cli);
			        System.out.println("EL usuario es:" + usuario.getLogin() + " " + usuario.getValoracion());
			        if (usuario.getValoracion() == -1) {
			        	JOptionPane.showMessageDialog(null, "VALORONAS DEL 0 AL 100 MEDIANTE LA BARRA Y GUARDA TU RESPUESTA HACIENDO CLICK ENCIMA DE ELLA", "GRACIAS!!!", JOptionPane.NO_OPTION);
				        lblValorar.setVisible(true);
				        sp.setVisible(true);
				        pb.setVisible(true);
			        }

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
	}
}
