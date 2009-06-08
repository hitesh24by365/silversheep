package main;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.filechooser.FileFilter;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import almacenamiento.AlmacenarInfoBibliotecaRefinado;
import almacenamiento.Biblioteca;
import almacenamiento.TransaccionesSQLite;
import archivos.ExploradorRecursivoArchivos;

import medios.Archivo;
import medios.ConstructorArchivoAudio;
import medios.ConstructorArchivoImagen;
import medios.ConstructorArchivoVideo;
import medios.DirectorMedios;
import medios.TipoArchivo;
import reproductor.ReproducirArchivoAudio;

public class PanelBiblioteca extends JPanel implements Constantes,
		ActionListener, KeyListener, MouseListener {
	private static final long serialVersionUID = -8338766233305367920L;
	private JTable listado;
	private MiModeloTabla modeloTabla;
	private Vector<Object[]> datos, datosNegativos;
	private Biblioteca biblio;
	private TransaccionesSQLite sqlite;
	private Vector<Archivo> archivos;
	// Barra de herramientas y sus botones
	private JToolBar barraHerramientas;
	private JButton btnAniadir, btnRemover, btnBorrarBusqueda,
			btnAjustarBusqueda, btnRefrescar;
	private JFileChooser selectorArchivo;
	private JTextField txtBusqueda;
	private DialogoPersonalizarBusqueda personalizarBusqueda;
	// Objeto para explorar los directorios en busca de archivos
	private ExploradorRecursivoArchivos explorador;
	// Objeto para partir las cadenas de texto que contienen extensiones
	private StringTokenizer tokens;
	// Clase que dirige la creacion de archivos
	private DirectorMedios directorMedios;
	// Clase que representa un archivo de audio
	private ConstructorArchivoAudio archivoAudio;
	// Clase que representa un archivo de video
	private ConstructorArchivoVideo archivoVideo;
	// Clase que representa un archivo de imagen
	private ConstructorArchivoImagen archivoImagen;
	// Clase que representa un archivo general
	private Archivo medio;

	public PanelBiblioteca() {
		super(new BorderLayout());
		sqlite = new TransaccionesSQLite();
		biblio = new AlmacenarInfoBibliotecaRefinado(sqlite);

		selectorArchivo = new JFileChooser();
		selectorArchivo.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		selectorArchivo.setFileFilter(new FileFilter() {
			@Override
			public boolean accept(File f) {
				if (f.isDirectory())
					return true;
				String nombre = f.getName();
				if (esPermitido(nombre))
					return true;
				return false;
			}

			public String getDescription() {
				return obtenerDescricionExtensiones();
			}
		});

		crearListado();
		crearBarraHerramientas();

		archivoAudio = new ConstructorArchivoAudio();
		archivoVideo = new ConstructorArchivoVideo();
		archivoImagen = new ConstructorArchivoImagen();
		// Iniciar el director de medios
		directorMedios = new DirectorMedios();
	}

	protected String obtenerDescricionExtensiones() {
		StringTokenizer tokens = new StringTokenizer(EXTENSIONES_TODAS, "-");
		String desc = "";
		while (tokens.hasMoreTokens())
			desc += (desc != "" ? "," : "") + " *." + tokens.nextToken();
		return desc;
	}

	private boolean esPermitido(String nombre) {//TODO hacer que funcione este filtro
		StringTokenizer tokens = new StringTokenizer(EXTENSIONES_TODAS, "-");
		while (tokens.hasMoreTokens()){
			if (nombre.toLowerCase().endsWith("." + tokens.nextToken()))
				return true;
		}
		return false;
	}

	private void crearBarraHerramientas() {
		barraHerramientas = new JToolBar();

		// iniciar botones
		btnAniadir = new JButton(new ImageIcon(this.getClass().getResource(
				IMG_SUBIR_VOLUMEN_30)));
		btnAniadir.addActionListener(this);
		btnAniadir.setToolTipText("A\u00f1adir archivo a la biblioteca");

		btnRemover = new JButton(new ImageIcon(this.getClass().getResource(
				IMG_BAJAR_VOLUMEN_30)));
		btnRemover.addActionListener(this);
		btnRemover.setToolTipText("Remover archivo de la biblioteca");

		btnRefrescar = new JButton(new ImageIcon(this.getClass().getResource(
				IMG_REFRESCAR_30)));
		btnRefrescar.addActionListener(this);
		btnRefrescar.setToolTipText("Actualizar lista");

		barraHerramientas.add(btnAniadir);
		barraHerramientas.add(btnRemover);
		barraHerramientas.add(btnRefrescar);

		// panel de busqueda
		JPanel pnlBusqueda = new JPanel();
		pnlBusqueda.setLayout(new BoxLayout(pnlBusqueda, BoxLayout.X_AXIS));
		pnlBusqueda.setBorder(BorderFactory.createTitledBorder("Filtrar"));
		pnlBusqueda.setMaximumSize(new Dimension(300, 55));

		txtBusqueda = new JTextField();
		txtBusqueda.addKeyListener(this);

		btnBorrarBusqueda = new JButton(new ImageIcon(this.getClass()
				.getResource(IMG_LIMPIAR_30)));
		btnBorrarBusqueda.setToolTipText("Limpiar campo de b\u00fasqueda");
		btnBorrarBusqueda.addActionListener(this);

		btnAjustarBusqueda = new JButton(new ImageIcon(this.getClass()
				.getResource(IMG_HERRAMIENTAS_30)));
		btnAjustarBusqueda.addActionListener(this);
		btnAjustarBusqueda
				.setToolTipText("Configurar preferencias de b\u00fasqueda");

		pnlBusqueda.add(txtBusqueda);
		pnlBusqueda.add(btnBorrarBusqueda);
		pnlBusqueda.add(btnAjustarBusqueda);

		barraHerramientas.addSeparator();
		barraHerramientas.add(pnlBusqueda);
		barraHerramientas.setFloatable(false);

		add(barraHerramientas, BorderLayout.NORTH);
	}

	private void crearListado() {
		datos = new Vector<Object[]>();
		datosNegativos = new Vector<Object[]>();

		modeloTabla = new MiModeloTabla(datos);

		listado = new JTable(modeloTabla);
		listado.setPreferredScrollableViewportSize(new Dimension(500, 70));
		listado.setFillsViewportHeight(true);
		listado.setAutoCreateRowSorter(true);
		listado.setDefaultRenderer(Archivo.class,
						new RenderizadorTipoArchivo());
		listado.addMouseListener(this);
		consultarDatos();
		if (datos.size() > 0)
			iniciarLongitudColumnas(listado);
		// Create the scroll pane and add the table to it.
		JScrollPane scrollPane = new JScrollPane(listado);

		// Add the scroll pane to this panel.
		add(scrollPane, BorderLayout.CENTER);
		modeloTabla.fireTableDataChanged();
	}

	private void consultarDatos() {
		archivos = biblio.getTodosArchivos(EXTENSIONES_TODAS);
		for (int i = 0; i < archivos.size(); i++)
			aniadirMedio(archivos.get(i));
	}

	class MiModeloTabla extends AbstractTableModel {
		private static final long serialVersionUID = 4728231179048500607L;
		private String[] nombreColumnas = { "", "Nombre", "Ruta", "Peso" };
		private boolean[] columnasActivas = new boolean[nombreColumnas.length];
		private Vector<Object[]> datos;

		public void setDatos(Vector<Object[]> datos) {
			this.datos = datos;
		}

		public MiModeloTabla(Vector<Object[]> datos) {
			this.datos = datos;
		}

		public int getColumnCount() {
			return nombreColumnas.length;
		}

		public int getRowCount() {
			if (datos == null)
				System.out.println("Es nulo");
			return datos.size();
		}

		public String getColumnName(int col) {
			return nombreColumnas[col];
		}

		public Object getValueAt(int fila, int col) {
			Object[] satan = datos.get(fila);
			return satan[col];
		}

		/*
		 * JTable uses this method to determine the default renderer/ editor for
		 * each cell. If we didn't implement this method, then the last column
		 * would contain text ("true"/"false"), rather than a check box.
		 */
		public Class getColumnClass(int c) {
			return getValueAt(0, c).getClass();
		}

		/*
		 * Don't need to implement this method unless your table's editable.
		 */
		public boolean isCellEditable(int fila, int col) {
			return false;
		}

		private void columnasVisibles() {
			// columnasActivas[fila];
		}

		/*
		 * Don't need to implement this method unless your table's data can
		 * change.
		 */
		public void setValueAt(Object valor, int fila, int col) {
			if (DEBUG) {
				System.out.println("Setting value at " + fila + "," + col
						+ " to " + valor + " (an instance of "
						+ valor.getClass() + ")");
			}
			datos.get(fila)[col] = valor;
			fireTableCellUpdated(fila, col);

			if (DEBUG) {
				System.out.println("New value of data:");
				printDebugData();
			}
		}

		private void printDebugData() {
			int numRows = getRowCount();
			int numCols = getColumnCount();

			for (int i = 0; i < numRows; i++) {
				System.out.print("    row " + i + ":");
				for (int j = 0; j < numCols; j++) {
					System.out.print("  " + datos.get(i)[j]);
				}
				System.out.println();
			}
			System.out.println("--------------------------");
		}

		public void cosas() {

		}
	}

	private void iniciarLongitudColumnas(JTable table) {
		MiModeloTabla model = (MiModeloTabla) table.getModel();
		TableColumn column = null;
		Component comp = null;
		int headerWidth = 0;
		int cellWidth = 0;
		Object[] longValues = model.datos.get(0);
		TableCellRenderer headerRenderer = table.getTableHeader()
				.getDefaultRenderer();

		for (int i = 0; i < table.getColumnCount(); i++) {
			column = table.getColumnModel().getColumn(i);

			comp = headerRenderer.getTableCellRendererComponent(null, column
					.getHeaderValue(), false, false, 0, 0);
			headerWidth = comp.getPreferredSize().width;

			comp = table.getDefaultRenderer(model.getColumnClass(i))
					.getTableCellRendererComponent(table, longValues[i], false,
							false, 0, i);
			cellWidth = comp.getPreferredSize().width;

			column.setPreferredWidth(Math.max(headerWidth, cellWidth));
			if (i == 0) {
				column.setMaxWidth(20);
			}
		}
	}

	public void aniadirMedio(Archivo ar) {
		Object[] informacionMedio = { ar, ar.getNombreCortoArchivo(),
				ar.getNombreArchivo(), bytesAKB(ar.getTamanio()) };
		datos.add(informacionMedio);
		modeloTabla.fireTableDataChanged();
	}

	private String bytesAKB(long bytes) {
		long kb = bytes / 1000;
		float kbs = kb / 1000;
		if (kbs != 0)
			return kbs + " KBs";
		kbs = bytes / 1000;
		return kbs + " Bs";
	}

	private class RenderizadorTipoArchivo extends JLabel implements
			TableCellRenderer {
		private static final long serialVersionUID = -4621477965387182269L;
		boolean isBordered = true;

		public Component getTableCellRendererComponent(JTable table,
				Object tipo, boolean isSelected, boolean hasFocus, int row,
				int column) {
			setIcon(new ImageIcon(this.getClass().getResource(
					((Archivo) tipo).getTipo())));
			setToolTipText("es de tipo");
			return this;
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnAniadir)
			seleccionarArchivos();
		if (e.getSource() == btnAjustarBusqueda)
			personalizarBusqueda = new DialogoPersonalizarBusqueda();
		if (e.getSource() == btnRefrescar)
			actualizarTabla();
		if (e.getSource() == btnRemover)
			removerArchivo();
	}

	private void removerArchivo() {
		System.out.println(listado.getSelectedRow());
	}

	private void seleccionarArchivos() {
		int resultado = selectorArchivo.showOpenDialog(this);
		if (resultado == JFileChooser.CANCEL_OPTION)
			return;
		File archivo = selectorArchivo.getSelectedFile();
		if (archivo.isDirectory())
			explorador = new ExploradorRecursivoArchivos(EXTENSIONES_TODAS);
		else {
			// Si es un archivo de audio, intentar recuperar
			// información
			if (esDeTipo(EXTENSIONES_AUDIO, archivo.toString())) {
				// Verificar que el archivo no se encuentre
				// registrado
				if (biblio.noEsta(archivo.toString(), BD_ARCHIVO)) {
					// Escoger el tipo de archivo que se desea
					// construir
					directorMedios.setArchivoMultimedia(archivoAudio);
					// Construir el archivo
					directorMedios.buildArchivo(archivo.toString());
					// Obtener objeto con los datos del archivo
					medio = directorMedios.getArchivo();
					// Enviar archivo al objeto que lo guardará
					// persistentemente
					biblio.aniadirArchivo(medio);
				} else if (DEBUG)// Si el archivo ya esta
					System.out.println("NO hago nada porque ya estas: "
							+ archivo.toString());
			}// imagen?
			if (esDeTipo(EXTENSIONES_IMAGEN, archivo.getName())) {
				// TODO capturar datos de la imagen
				if (biblio.noEsta(archivo.getName(), BD_ARCHIVO)) {
					directorMedios.setArchivoMultimedia(archivoImagen);
					directorMedios.buildArchivo(archivo.getName());
					medio = directorMedios.getArchivo();
					biblio.aniadirArchivo(medio);
				} else if (DEBUG)// Si el archivo ya esta
					System.out.println("NO hago nada porque ya estas: "
							+ archivo.getName());
			}
		}
		actualizarTabla();
	}
	
	private void actualizarTabla(){
		datos = null;
		datos = new Vector<Object[]>();
		consultarDatos();
		modeloTabla.setDatos(datos);
		System.out.println(((Archivo)datos.get(datos.size()-1)[0]).getNombreArchivo());
		if (datos.size() > 0)
			iniciarLongitudColumnas(listado);
		modeloTabla.fireTableDataChanged();
	}

	/**
	 * Verificar de si un archivo es de un tipo en especial. Esto se logra
	 * comparando su extensión con la lista de extensiones de la variable
	 * EXTENSIONES_***
	 * 
	 * @param extensiones
	 * @param archivo
	 * @return
	 */
	private boolean esDeTipo(String extensiones, String archivo) {
		tokens = new StringTokenizer(extensiones, "-");
		while (tokens.hasMoreTokens()) {
			if (archivo.endsWith(tokens.nextToken()))
				return true;
		}
		return false;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == 8) {// si está borrando cosas
			filtrarMedio(txtBusqueda.getText(), datosNegativos, datos, false);
		} else
			// si esta escribiendo
			filtrarMedio(txtBusqueda.getText(), datos, datosNegativos, true);
		if (txtBusqueda.getText().equals(""))
			modeloTabla.fireTableDataChanged();
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	/**
	 * Este metodo filtra el contenido del arbol. Su funcionamiento es el
	 * siguiente: basado en las raices que le pasemos, filtra los datos de un
	 * archivo de medios y los que no coincidan los copia a otro arbol. La
	 * manera en que funciona depende de la variable filtrar: si es true mueve
	 * los nodos que no coinciden al otro arbol, si es false, mueve los nodos
	 * que coinciden al otro arbol.
	 * 
	 * @param texto
	 * @param vectorOrigen
	 * @param vectorDestino
	 * @param filtrar
	 */
	private void filtrarMedio(String texto, Vector<Object[]> vectorOrigen,
			Vector<Object[]> vectorDestino, boolean filtrar) {
		boolean filtrando = false;
		Object clonObjeto[];
		for (int k = 0; k < vectorOrigen.size(); k++) {
			Archivo ar = (Archivo) vectorOrigen.get(k)[0];
			String[] opciones = biblio.opcionesPorNombre("criterio-busqueda");
			if (filtrar) {// si se esta filtrando... busque las que no
				// tengan coincidencias
				filtrando = (ar.tieneEstosDatos(texto, opciones, filtrar));
			} else {// si se esta quitando el filtro... busque las
				filtrando = (ar.tieneEstosDatos(texto, opciones, filtrar));
			}
			if (filtrando) {
				// Copiar nodos de un arbol a otro
				clonObjeto = vectorOrigen.get(k).clone();

				vectorDestino.add(clonObjeto);

				// Borrar los nodos copiados
				vectorOrigen.remove(k);
				k--;
			}
		}
		// esto es necesario para actualizar la estructura del arbol
		modeloTabla.fireTableDataChanged();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getClickCount() == 2) {
			Archivo arc = (Archivo) modeloTabla.getValueAt(listado
					.getSelectedRow(), 0);
			if (arc.getTipo().equals(IMG_SONIDO_16))
				System.out.println("TODO: Reproducir en pestania");
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}
}