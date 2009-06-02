package listados;
//TODO crear una estructura de arbol para esto desde cero y en otra clase.
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.JTextField;
import javax.swing.ToolTipManager;
import javax.swing.ImageIcon;
import javax.swing.Icon;

import main.Constantes;
import medios.Archivo;

import java.net.URL;
import java.util.Enumeration;
import java.util.Vector;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Component;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Coleccion extends JPanel implements TreeSelectionListener, Constantes, MouseListener, KeyListener, TreeModelListener {
	private static final long serialVersionUID = -8359984107448140209L;
	private JTree tree, tree2;
	private Vector<Archivo> archivos;
	private ListaReproduccion listaRepro;
	private DefaultMutableTreeNode ultimoNodoSeleccionado;
	private JTextField txtBuscar;
	private DefaultMutableTreeNode raizArbol, raizNegativa;
	private DefaultTreeModel modeloArbol, modeloArbolNegativo;

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
		
		ultimoNodoSeleccionado = (DefaultMutableTreeNode) tree.
				getLastSelectedPathComponent();
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
	private void crearNodos(DefaultMutableTreeNode top) {
		DefaultMutableTreeNode artista = null;
		DefaultMutableTreeNode album = null;
		DefaultMutableTreeNode cancion = null;
		//creacion de artistas
		String strArtista, strAlbum;
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
			cancion = new DefaultMutableTreeNode(archivos.get(i));
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
		setLayout(new BorderLayout());
		raizArbol = new DefaultMutableTreeNode("M\u00fasica");
		crearNodos(raizArbol);//?si se crea aca?
		modeloArbol = new DefaultTreeModel(raizArbol);
		modeloArbol.addTreeModelListener(this);
		tree = new JTree(modeloArbol);
		tree.getSelectionModel().setSelectionMode(
				TreeSelectionModel.SINGLE_TREE_SELECTION);
		tree.setShowsRootHandles(true);

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
		
		//Caja de busqueda
		txtBuscar = new JTextField();
		txtBuscar.addKeyListener(this);
		
		add(txtBuscar, BorderLayout.NORTH);
		add(treeView);
		
		/////////////////////////////////////////////////////////////////////////
		
		raizNegativa = new DefaultMutableTreeNode("Residuos");
		modeloArbolNegativo = new DefaultTreeModel(raizNegativa);
		modeloArbolNegativo.addTreeModelListener(this);
		tree2 = new JTree(modeloArbolNegativo);
		tree2.getSelectionModel().setSelectionMode(
				TreeSelectionModel.SINGLE_TREE_SELECTION);
		tree2.setShowsRootHandles(true);

		// Habilitar tooltips
		ToolTipManager.sharedInstance().registerComponent(tree2);

		// Escuchar cambios en la seleccion
		//tree2.addTreeSelectionListener(this);

		// Creal el scroll para el 
		//JScrollPane treeView2 = new JScrollPane(tree2);
		
		//add(treeView2, BorderLayout.EAST);
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
			System.out.println("Artista "+((Archivo)ultimoNodoSeleccionado.getUserObject()).getArtista());//listaRepro.aniadirMedio(ultimoNodoSeleccionado.toString(), 0, 0);
	}

	public void setListaRepro(ListaReproduccion listaRepro) {
		this.listaRepro = listaRepro;
	}

	public ListaReproduccion getListaRepro() {
		return listaRepro;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode()==8){//si está borrando cosas
			desfiltrarMedio(txtBuscar.getText());
		}
		else if(!txtBuscar.getText().equals(""))//si esta escribiendo
			filtrarMedio(txtBuscar.getText());
	}

	private void desfiltrarMedio(String text) {
		int artista, albumes, canciones;
		artista = albumes = canciones = 0;
		DefaultMutableTreeNode ndArt, ndAlb, ndCan;
		DefaultMutableTreeNode ndArtClon, ndAlbClon, ndCanClon;
		
		artista = raizNegativa.getChildCount();
		for(int i = 0; i<artista; i++){
			albumes = raizNegativa.getChildAt(i).getChildCount();
			for(int j = 0; j<albumes; j++){
				canciones = raizNegativa.getChildAt(i).getChildAt(j).getChildCount();
				for(int k = 0; k<canciones; k++){
					ndArt=(DefaultMutableTreeNode)raizNegativa.getChildAt(i);
					ndAlb=(DefaultMutableTreeNode)ndArt.getChildAt(j);
					ndCan=(DefaultMutableTreeNode)ndAlb.getChildAt(k);
					Archivo ar = (Archivo)ndCan.getUserObject();
					if(ar.getNombreCortoArchivo().toLowerCase().indexOf(text.toLowerCase()) >= 0){
						///////////////////////////////////////////////////
						///// movber el nodo a arbol positivo
						///////////////////////////////////////////////
						
						ndArtClon=(DefaultMutableTreeNode)ndArt.clone();
						ndAlbClon=(DefaultMutableTreeNode)ndAlb.clone();
						ndCanClon=(DefaultMutableTreeNode)ndCan.clone();
						
						ndArtClon = obtenerNodo(raizArbol, ndArtClon.toString());
						if(ndArtClon == null){
							ndArtClon = (DefaultMutableTreeNode)ndArt.clone();
							raizArbol.add(ndArtClon);
						}
						
						ndAlbClon = obtenerNodo(ndArtClon, ndAlbClon.toString());
						if(ndAlbClon == null){
							ndAlbClon = (DefaultMutableTreeNode)ndAlb.clone();
							ndArtClon.add(ndAlbClon);
						}
						
						ndAlbClon.add(ndCanClon);
						
						
						////////////////////////////////////////////////
						modeloArbolNegativo.removeNodeFromParent(ndCan);
						canciones--;//acortar el ciclo por que
						k--;//acabo de eliminar un nodo
						if(ndAlb.getChildCount()==0){
							modeloArbolNegativo.removeNodeFromParent(ndAlb);
							albumes--;
							j--;
							if(ndArt.getChildCount()==0){
								modeloArbolNegativo.removeNodeFromParent(ndArt);
								artista--;
								i--;
							}
						}
					}
				}
			}
		}
		modeloArbol.reload();
		modeloArbolNegativo.reload();
		expandAll(tree, true);
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	private void filtrarMedio(String text) {
		int artista, albumes, canciones;
		artista = albumes = canciones = 0;
		DefaultMutableTreeNode ndArt, ndAlb, ndCan;
		DefaultMutableTreeNode ndArtClon, ndAlbClon, ndCanClon;
		
		artista = raizArbol.getChildCount();
		for(int i = 0; i<artista; i++){
			albumes = raizArbol.getChildAt(i).getChildCount();
			for(int j = 0; j<albumes; j++){
				canciones = raizArbol.getChildAt(i).getChildAt(j).getChildCount();
				for(int k = 0; k<canciones; k++){
					ndArt=(DefaultMutableTreeNode)raizArbol.getChildAt(i);
					ndAlb=(DefaultMutableTreeNode)ndArt.getChildAt(j);
					ndCan=(DefaultMutableTreeNode)ndAlb.getChildAt(k);
					Archivo ar = (Archivo)ndCan.getUserObject();
					if(ar.getNombreCortoArchivo().toLowerCase().indexOf(text.toLowerCase()) < 0){
						///////////////////////////////////////////////////
						///// movber el nodo a arbol negativo
						///////////////////////////////////////////////
						
						ndArtClon=(DefaultMutableTreeNode)ndArt.clone();
						ndAlbClon=(DefaultMutableTreeNode)ndAlb.clone();
						ndCanClon=(DefaultMutableTreeNode)ndCan.clone();
						
						ndArtClon = obtenerNodo(raizNegativa, ndArtClon.toString());
						if(ndArtClon == null){
							ndArtClon = (DefaultMutableTreeNode)ndArt.clone();
							raizNegativa.add(ndArtClon);
						}
						
						ndAlbClon = obtenerNodo(ndArtClon, ndAlbClon.toString());
						if(ndAlbClon == null){
							ndAlbClon = (DefaultMutableTreeNode)ndAlb.clone();
							ndArtClon.add(ndAlbClon);
						}
						
						ndAlbClon.add(ndCanClon);
						
						
						////////////////////////////////////////////////
						modeloArbol.removeNodeFromParent(ndCan);
						canciones--;//acortar el ciclo por que
						k--;//acabo de eliminar un nodo
						if(ndAlb.getChildCount()==0){
							modeloArbol.removeNodeFromParent(ndAlb);
							albumes--;
							j--;
							if(ndArt.getChildCount()==0){
								modeloArbol.removeNodeFromParent(ndArt);
								artista--;
								i--;
							}
						}
					}
				}
			}
		}
		modeloArbol.reload();
		modeloArbolNegativo.reload();
		expandAll(tree, true);
	}
	public void expandAll(JTree tree, boolean expand) {
        TreeNode root = (TreeNode)tree.getModel().getRoot();
    
        // Traverse tree from root
        expandAll(tree, new TreePath(root), expand);
    }
    private void expandAll(JTree tree, TreePath parent, boolean expand) {
        // Traverse children
        TreeNode node = (TreeNode)parent.getLastPathComponent();
        if (node.getChildCount() >= 0) {
            for (Enumeration e=node.children(); e.hasMoreElements(); ) {
                TreeNode n = (TreeNode)e.nextElement();
                TreePath path = parent.pathByAddingChild(n);
                expandAll(tree, path, expand);
            }
        }
    
        // Expansion or collapse must be done bottom-up
        if (expand) {
            tree.expandPath(parent);
        } else {
            tree.collapsePath(parent);
        }
    }


	@Override
	public void treeNodesChanged(TreeModelEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void treeNodesInserted(TreeModelEvent e) {
		//System.out.println("Se inserto cosas de "+e.toString());
		
	}

	@Override
	public void treeNodesRemoved(TreeModelEvent e) {
		//System.out.println("Se borraron cosas de "+e.toString());
		
	}

	@Override
	public void treeStructureChanged(TreeModelEvent e) {
		// TODO Auto-generated method stub
		
	}
}