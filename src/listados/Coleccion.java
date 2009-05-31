package listados;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.ToolTipManager;
import javax.swing.ImageIcon;
import javax.swing.Icon;

import main.Constantes;
import medios.Archivo;

import java.net.URL;
import java.util.Vector;
import java.awt.GridLayout;
import java.awt.Component;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Coleccion extends JPanel implements TreeSelectionListener, Constantes, MouseListener {
	private static final long serialVersionUID = -8359984107448140209L;
	private JTree tree;
	private Vector<Archivo> archivos;
	private ListaReproduccion listaRepro;
	private DefaultMutableTreeNode ultimoNodoSeleccionado;

	public Coleccion(Vector<Archivo> archivos) {
		super(new GridLayout(1, 0));
		this.archivos = archivos;
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				crearInterfazGrafica();
			}
		});
	}

	/** Required by TreeSelectionListener interface. */
	public void valueChanged(TreeSelectionEvent e) {
		//System.out.println("algo pasa");
		ultimoNodoSeleccionado = (DefaultMutableTreeNode) tree
				.getLastSelectedPathComponent();
		/*Object informacionArchivo = nodo.getUserObject();
		if (nodo.isLeaf()) {
			InformacionArchivo archivo = (InformacionArchivo) informacionArchivo;
			if (DEBUG) {
				System.out.print(archivo.urlArchivo + ":  \n    ");
			}
		} else {
		}
		if (DEBUG) {
			System.out.println(informacionArchivo.toString());
		}*/
	}

	private class InformacionArchivo {
		public String nombreArchivo;
		public String urlArchivo;
		public InformacionArchivo(String libro, String nombreFichero) {
			nombreArchivo = libro;
			urlArchivo = nombreFichero;
		}
		public String toString() {
			return nombreArchivo;
		}
	}


	private void crearNodos(DefaultMutableTreeNode top) {
		DefaultMutableTreeNode artista = null;
		DefaultMutableTreeNode album = null;
		DefaultMutableTreeNode cancion = null;
		//creacion de artistas
		String strArtista, strAlbum, strCancion;
		//TODO no funciona bien puesto que en cada iteración se está creando 
		//un nuevo nodo padre, que no es el mismo que utilizan los archivos que deberían ir dentro.
		for(int i=0; i<archivos.size(); i++){
			//aniadir artista
			strArtista = archivos.get(i).getArtista();
			strArtista = strArtista.equals("") ? "Desconocido" : strArtista;
			//preguntar por uno existente
			artista = obtenerNodo(top, strArtista);
			if(artista == null){
				artista = new DefaultMutableTreeNode(strArtista);
				top.add(artista);
			}
			//aniadir album
			strAlbum = archivos.get(i).getAlbumDisco();
			strAlbum = strAlbum.equals("") ? "Desconocido" : strAlbum;
			//preguntar por album existente
			album = obtenerNodo(artista, strAlbum);
			if(album == null){
				album = new DefaultMutableTreeNode(strAlbum);
				artista.add(album);
			}
			//aniadir cancion
			strCancion = archivos.get(i).getNombreCortoArchivo();
			strCancion = strCancion.equals("") ? "Desconocido" : strCancion;
			cancion = new DefaultMutableTreeNode(strCancion);
			album.add(cancion);
		}
	}

	private DefaultMutableTreeNode obtenerNodo(DefaultMutableTreeNode top,
			String texto) {
		int hijos = top.getChildCount();
		for(int i = 0; i<hijos; i++)
			if(top.getChildAt(i).toString().toLowerCase().equals(texto.toLowerCase()))
				return (DefaultMutableTreeNode)top.getChildAt(i);
		return null;
	}

	private boolean noExisteNodo(DefaultMutableTreeNode top, String artista) {
		int hijos = top.getChildCount();
		for(int i = 0; i<hijos; i++)
			if(top.getChildAt(i).toString().toLowerCase().equals(artista.toLowerCase()))
				return false;
		return true;
	}

	/** Returns an ImageIcon, or null if the path was invalid. */
	protected static ImageIcon crearIcono(String ruta) {
		URL urlImagen = Coleccion.class.getResource(ruta);
		if (urlImagen != null) {
			return new ImageIcon(urlImagen);
		} else {
			System.err.println("No puedo encontrar el archivo: " + ruta);
			return null;
		}
	}

	private void crearInterfazGrafica() {
		DefaultMutableTreeNode top = new DefaultMutableTreeNode("M\u00fasica");
		crearNodos(top);
		tree = new JTree(top);
		tree.getSelectionModel().setSelectionMode(
				TreeSelectionModel.SINGLE_TREE_SELECTION);

		// Habilitar tooltips
		ToolTipManager.sharedInstance().registerComponent(tree);

		// Asignar iconos
		ImageIcon icono = crearIcono(IMG_SONIDO_16);
		if (icono != null) {
			tree.setCellRenderer(new MiRenderizador(icono));
		} else {
			System.err.println("Tutorial icon missing; using default.");
		}

		// Escuchar cambios en la seleccion
		tree.addTreeSelectionListener(this);
		tree.addMouseListener(this);

		// Creal el scroll para el 
		JScrollPane treeView = new JScrollPane(tree);

		add(treeView);
	}

	private class MiRenderizador extends DefaultTreeCellRenderer {
		private static final long serialVersionUID = 2718364362535743782L;
		private Icon iconoNodo;

		public MiRenderizador(Icon icono) {
			iconoNodo = icono;
		}

		public Component getTreeCellRendererComponent(JTree arbol, Object valor,
				boolean sel, boolean expandido, boolean hijo, int fila,
				boolean tieneFoco) {

			super.getTreeCellRendererComponent(arbol, valor, sel, expandido,
					hijo, fila, tieneFoco);
			if (hijo && esCancion(valor)) {
				setIcon(iconoNodo);
				setToolTipText("Es una canción");
			} else {
				setToolTipText(null);
			}

			return this;
		}

		protected boolean esCancion(Object valor) {
			//DefaultMutableTreeNode nodo = (DefaultMutableTreeNode) valor;
			//InformacionArchivo nodeInfo = (InformacionArchivo) (nodo.getUserObject());
			return true;
		}
	}


	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if(e.getClickCount()==2 && ultimoNodoSeleccionado.isLeaf())
			listaRepro.cambiarValor(ultimoNodoSeleccionado.toString(), 0, 0);
	}

	public void setListaRepro(ListaReproduccion listaRepro) {
		this.listaRepro = listaRepro;
	}

	public ListaReproduccion getListaRepro() {
		return listaRepro;
	}
}