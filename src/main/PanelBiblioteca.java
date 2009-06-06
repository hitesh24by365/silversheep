package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.border.Border;
import javax.swing.filechooser.FileFilter;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.text.TableView.TableRow;

import almacenamiento.AlmacenarInfoBibliotecaRefinado;
import almacenamiento.Biblioteca;
import almacenamiento.TransaccionesSQLite;

import medios.Archivo;
import medios.TipoArchivo;
import reproductor.ReproducirArchivoAudio;

public class PanelBiblioteca extends JPanel implements Constantes,
		ActionListener {
	private static final long serialVersionUID = -8338766233305367920L;
	private JTable listado;
	private MiModeloTabla modeloTabla;
	private Vector<Object[]> datos;
	private Biblioteca biblio;
	private TransaccionesSQLite sqlite;
	private Vector<Archivo> archivos;
	// Barra de herramientas y sus botones
	private JToolBar barraHerramientas;
	private JButton btnAniadir, btnRemover;
	private JFileChooser selectorArchivo;

	public PanelBiblioteca() {
		super(new BorderLayout());
		sqlite = new TransaccionesSQLite();
		biblio = new AlmacenarInfoBibliotecaRefinado(sqlite);

		selectorArchivo = new JFileChooser();
		selectorArchivo.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
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
	}

	protected String obtenerDescricionExtensiones() {
		StringTokenizer tokens = new StringTokenizer(EXTENSIONES_TODAS, "-");
		String desc = "";
		while (tokens.hasMoreTokens())
			desc += (desc != "" ? ",": "")+" ." + tokens.nextToken();
		return desc;
	}

	private boolean esPermitido(String nombre) {
		StringTokenizer tokens = new StringTokenizer(EXTENSIONES_TODAS, "-");
		while (tokens.hasMoreTokens())
			if (nombre.toLowerCase().endsWith("." + tokens.nextToken()))
				return true;
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

		barraHerramientas.add(btnAniadir);
		barraHerramientas.add(btnRemover);

		add(barraHerramientas, BorderLayout.NORTH);
	}

	private void crearListado() {
		datos = new Vector<Object[]>();

		modeloTabla = new MiModeloTabla(datos);

		listado = new JTable(modeloTabla);
		listado.setPreferredScrollableViewportSize(new Dimension(500, 70));
		listado.setFillsViewportHeight(true);
		listado.setAutoCreateRowSorter(true);
		listado.setDefaultRenderer(TipoArchivo.class,
				new RenderizadorTipoArchivo());
		consultarDatos();
		if (datos.size() > 0)
			iniciarLongitudColumnas(listado);
		// Create the scroll pane and add the table to it.
		JScrollPane scrollPane = new JScrollPane(listado);

		// Add the scroll pane to this panel.
		add(scrollPane, BorderLayout.CENTER);
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

		public MiModeloTabla(Vector<Object[]> datos) {
			this.datos = datos;
		}

		public int getColumnCount() {
			return nombreColumnas.length;
		}

		public int getRowCount() {
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
		Object[] informacionMedio = { ar.getTipo(), ar.getNombreCortoArchivo(),
				ar.getNombreArchivo(), bytesAKB(ar.getTamanio()) };
		datos.add(informacionMedio);
		modeloTabla.fireTableDataChanged();
	}

	private String bytesAKB(long bytes) {
		long kb = bytes / 1000;
		float kbs = kb / 1000;
		if(kbs != 0)
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
			setIcon(new ImageIcon(this.getClass().getResource(tipo.toString())));
			setToolTipText("es de tipo");
			return this;
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnAniadir) {
			seleccionarArchivos();
		}
	}

	private void seleccionarArchivos() {
		int resultado = selectorArchivo.showOpenDialog(this);
		if (resultado == JFileChooser.CANCEL_OPTION)
			return;
		File[] archivos = selectorArchivo.getSelectedFiles();
		for (int i = 0; i < archivos.length; i++) {
			File file = archivos[i];
			//TODO Aniadir arcgivos a la biblioteca
		}
	}

}