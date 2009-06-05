package listados;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import reproductor.ReproducirArchivoAudio;
import reproductor.Reproductor;

import main.Constantes;
import main.ObservadorReproduccionPestania;
import medios.Archivo;

public class ListaReproduccion extends JPanel implements Constantes,
		MouseListener, ActionListener, Reproductor {
	private static final long serialVersionUID = -8338766233305367920L;
	private JTable listado;
	private MiModeloTabla modeloTabla;
	private ReproducirArchivoAudio hiloReproduccion;
	private JToolBar barraHerramientas;
	private Vector<Object[]> datos;
	private boolean reproduciendo = false, mute = false, aleatorio = false;
	private JButton btnSiguiente, btnAnterior, btnReproducir, btnDetener,
			btnSubirVolumen, btnBajarVolumen, btnMute;
	private JToggleButton btnRandom;
	private int filaSeleccionada, volumen = 100;
	private ObservadorReproduccionPestania observador;

	public ListaReproduccion() {
		super(new BorderLayout());
		crearListado();
	}

	private void crearListado() {
		datos = new Vector<Object[]>();

		modeloTabla = new MiModeloTabla(datos);

		listado = new JTable(modeloTabla);
		listado.setPreferredScrollableViewportSize(new Dimension(500, 70));
		listado.setFillsViewportHeight(true);
		listado.setAutoCreateRowSorter(true);
		listado.addMouseListener(this);
		if (datos.size() > 0)
			iniciarLongitudColumnas(listado);

		// Create the scroll pane and add the table to it.
		JScrollPane scrollPane = new JScrollPane(listado);

		// aniadir barra herramientas
		add(crearBarraHerramientas(), BorderLayout.SOUTH);

		// Add the scroll pane to this panel.
		add(scrollPane, BorderLayout.CENTER);

		hiloReproduccion = new ReproducirArchivoAudio();
	}

	public class MiModeloTabla extends AbstractTableModel {
		private static final long serialVersionUID = 4728231179048500607L;
		private String[] nombreColumnas = { "T\u00edtulo", "Artista",
				"\u00c1lbum", "Longitud", "G\u00e9nero", "Ubicaci\u00f3n",
				"Contador" };
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

		for (int i = 0; i < 5; i++) {
			column = table.getColumnModel().getColumn(i);

			comp = headerRenderer.getTableCellRendererComponent(null, column
					.getHeaderValue(), false, false, 0, 0);
			headerWidth = comp.getPreferredSize().width;

			comp = table.getDefaultRenderer(model.getColumnClass(i))
					.getTableCellRendererComponent(table, longValues[i], false,
							false, 0, i);
			cellWidth = comp.getPreferredSize().width;

			column.setPreferredWidth(Math.max(headerWidth, cellWidth));
		}
	}

	public void aniadirMedio(Archivo ar) {
		Object[] informacionMedio = { ar.getNombreCortoArchivo(),
				ar.getArtista(), ar.getAlbumDisco(),
				convertirMilisegundos(ar.getLongitud()), ar.getGenero(),
				ar.getNombreArchivo(), ar.getContador() };
		datos.add(informacionMedio);
		modeloTabla.fireTableDataChanged();
		actualizarEstadoControles();
	}

	private String convertirMilisegundos(int longitud) {
		int segundos = longitud / 1000000;
		int minutos = segundos / 60;
		segundos = segundos % 60;
		return minutos + ":" + (segundos < 10 ? "0" : "") + segundos;
	}

	/**
	 * Metodos de la interfaz {@link MouseListener}
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getClickCount() == 2) {
			filaSeleccionada = listado.getSelectedRow();
			if (reproduciendo)
				hiloReproduccion.siguienteSaltado(this);
			else
				reproducirMedio();
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

	/**
	 * Crear la barra de controles
	 */
	private Component crearBarraHerramientas() {
		btnSiguiente = new JButton(new ImageIcon(this.getClass().getResource(
				IMG_SIGUIENTE_30)));
		btnSiguiente.addActionListener(this);
		btnSiguiente.setToolTipText("Siguiente tema");

		btnAnterior = new JButton(new ImageIcon(this.getClass().getResource(
				IMG_ANTERIOR_30)));
		btnAnterior.addActionListener(this);
		btnAnterior.setToolTipText("Anterior tema");

		btnReproducir = new JButton(new ImageIcon(this.getClass().getResource(
				IMG_REPRODUCIR_30)));
		btnReproducir.addActionListener(this);
		btnReproducir.setToolTipText("Reproducir");

		btnDetener = new JButton(new ImageIcon(this.getClass().getResource(
				IMG_DETENER_30)));
		btnDetener.addActionListener(this);
		btnDetener.setToolTipText("Detener");

		btnSubirVolumen = new JButton(new ImageIcon(this.getClass()
				.getResource(IMG_SUBIR_VOLUMEN_30)));
		btnSubirVolumen.addActionListener(this);
		btnSubirVolumen.setToolTipText("Subir volumen");

		btnBajarVolumen = new JButton(new ImageIcon(this.getClass()
				.getResource(IMG_BAJAR_VOLUMEN_30)));
		btnBajarVolumen.addActionListener(this);
		btnBajarVolumen.setToolTipText("Bajar Volumen");

		btnMute = new JButton(new ImageIcon(this.getClass().getResource(
				IMG_VOLUMEN_ALTO_30)));
		btnMute.setText("100");
		btnMute.setBorderPainted(false);
		btnMute.setContentAreaFilled(false);
		btnMute.addActionListener(this);

		btnRandom = new JToggleButton(new ImageIcon(this.getClass()
				.getResource(IMG_RANDOM_30)));
		btnRandom.setToolTipText("Reproducci\u00f3n aleatoria");
		btnRandom.addActionListener(this);

		barraHerramientas = new JToolBar();
		barraHerramientas.add(btnAnterior);
		barraHerramientas.add(btnReproducir);
		barraHerramientas.add(btnDetener);
		barraHerramientas.add(btnSiguiente);
		barraHerramientas.addSeparator();
		barraHerramientas.add(btnBajarVolumen);
		barraHerramientas.add(btnMute);
		barraHerramientas.add(btnSubirVolumen);
		barraHerramientas.addSeparator();
		barraHerramientas.add(btnRandom);
		return barraHerramientas;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnReproducir) {
			reproducirMedio();
		} else if (e.getSource() == btnSiguiente) {
			siguiente();
		} else if (e.getSource() == btnAnterior) {
			anterior();
		} else if (e.getSource() == btnDetener) {
			detener();
		} else if (e.getSource() == btnRandom) {
			if (btnRandom.isSelected())
				aleatorio = true;
			else
				aleatorio = false;
		} else if (e.getSource() == btnSubirVolumen) {
			volumen += 5;
			if (volumen > 100)
				volumen = 100;
			actualizarLabelVolumen();
			hiloReproduccion.cambiarVolumen(volumen);
		} else if (e.getSource() == btnBajarVolumen) {
			volumen -= 5;
			if (volumen < 0)
				volumen = 0;
			actualizarLabelVolumen();
			hiloReproduccion.cambiarVolumen(volumen);
		} else if (e.getSource() == btnMute) {
			mute = !mute;
			if (!mute) {
				hiloReproduccion.cambiarVolumen(volumen);
				actualizarLabelVolumen();
			} else {
				btnMute.setIcon(new ImageIcon(this.getClass().getResource(
						IMG_VOLUMEN_MUTE_30)));
				hiloReproduccion.cambiarVolumen(0);
			}
		}

	}

	/**
	 * Actualizar el icono del label de volumen
	 */
	private void actualizarLabelVolumen() {
		if (volumen == 0)
			btnMute.setIcon(new ImageIcon(this.getClass().getResource(
					IMG_VOLUMEN_CERO_30)));
		if (volumen > 66)
			btnMute.setIcon(new ImageIcon(this.getClass().getResource(
					IMG_VOLUMEN_ALTO_30)));
		if (volumen > 0 && volumen < 33)
			btnMute.setIcon(new ImageIcon(this.getClass().getResource(
					IMG_VOLUMEN_BAJO_30)));
		if (volumen >= 33 && volumen <= 66)
			btnMute.setIcon(new ImageIcon(this.getClass().getResource(
					IMG_VOLUMEN_MEDIO_30)));
		btnMute.setText("" + volumen);
	}

	/**
	 * Reproducir el medio que esta antes que el que esta siendo reproducido
	 */
	public void anterior() {
		hiloReproduccion.anterior(this);
	}

	/**
	 * Reproducir el medio que esta despues que el que esta siendo reproducido
	 */
	public void siguiente() {
		hiloReproduccion.siguiente(this);
	}

	/**
	 * Este metodo reproduce un medio situado en la fila seleccionada
	 */
	public void reproducirMedio() {
		try {
			if (!reproduciendo) {
				hiloReproduccion.setRutaMedio((String) modeloTabla.getValueAt(
						filaSeleccionada, 5));
				reproduciendo = true;
				hiloReproduccion.reproducir(this);
			} else {
				reproduciendo = false;
				hiloReproduccion.pausar(this);
			}
		} catch (IllegalThreadStateException itex) {
			itex.printStackTrace();
		}
		invertirControles();
	}

	public void detener() {
		reproduciendo = false;
		hiloReproduccion.detener(this);
		invertirControles();
	}

	/**
	 * Invierte las acciones y apariencia del boton reproducir/pausar
	 */
	public void invertirControles() {
		if (reproduciendo) {
			btnReproducir.setIcon(new ImageIcon(this.getClass().getResource(
					IMG_PAUSAR_30)));
			btnReproducir.setToolTipText("Pausar");
			
		} else {
			btnReproducir.setIcon(new ImageIcon(this.getClass().getResource(
					IMG_REPRODUCIR_30)));
			btnReproducir.setToolTipText("Reproducir");
			
		}
		if(observador != null)
			observador.cambioReproduccion(reproduciendo);
	}

	/**
	 * Selecciona la siguiente pista en la lista de reproduccion. Hace un wrap
	 * de ser necesario
	 */
	public void seleccionarSiguientePista() {
		int filas = listado.getRowCount();
		if(aleatorio)
			filaSeleccionada = Math.abs(new Random().nextInt() % filas);
		else
			filaSeleccionada++;
		if (filaSeleccionada >= filas)
			filaSeleccionada = 0;
		listado.setRowSelectionInterval(filaSeleccionada, filaSeleccionada);
	}

	/**
	 * Selecciona la anterior pista en la lista de reproduccion. Hace un wrap de
	 * ser necesario
	 */
	public void seleccionarAnteriorPista() {
		int filas = listado.getRowCount();
		if(aleatorio)
			filaSeleccionada = new Random().nextInt() % filas;
		else
			filaSeleccionada--;
		if (filaSeleccionada < 0)
			filaSeleccionada = filas - 1;
		listado.setRowSelectionInterval(filaSeleccionada, filaSeleccionada);
	}

	public boolean isReproduciendo() {
		return reproduciendo;
	}

	public void setReproduciendo(boolean reproduciendo) {
		this.reproduciendo = reproduciendo;
	}

	public int getVolumen() {
		return volumen;
	}
	private void actualizarEstadoControles() {
		boolean hayArchivosPorReproducir = true;
		if(listado.getRowCount() == 0)
			hayArchivosPorReproducir = false;
		btnAnterior.setEnabled(hayArchivosPorReproducir);
		btnSiguiente.setEnabled(hayArchivosPorReproducir);
		btnDetener.setEnabled(hayArchivosPorReproducir);
		btnReproducir.setEnabled(hayArchivosPorReproducir);
		btnSubirVolumen.setEnabled(hayArchivosPorReproducir);
		btnBajarVolumen.setEnabled(hayArchivosPorReproducir);
		btnMute.setEnabled(hayArchivosPorReproducir);
		btnRandom.setEnabled(hayArchivosPorReproducir);
		if(observador != null){
			if(hayArchivosPorReproducir){
				observador.cambioReproduccion(reproduciendo);
				observador.cambioReproduccion(hayArchivosPorReproducir, true);
			}
			else
				observador.cambioReproduccion(hayArchivosPorReproducir, true);
			observador.cambioAnterior(hayArchivosPorReproducir);
			observador.cambioSiguiente(hayArchivosPorReproducir);
		}
	}

	@Override
	public void registrarObservador(ObservadorReproduccionPestania obs) {
		this.observador = obs;
		actualizarEstadoControles();
	}


}