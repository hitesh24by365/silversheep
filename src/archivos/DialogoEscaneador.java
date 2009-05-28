package archivos;

import java.awt.BorderLayout;
import java.awt.DisplayMode;
import java.awt.Frame;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;

import almacenamiento.AlmacenarInfoBibliotecaRefinado;
import almacenamiento.Biblioteca;
import almacenamiento.TransaccionesSQLite;

import main.Constantes;
import medios.DirectorMedios;

/**
 * La clase {@link DialogoEscaneador} nos proporciona un diálogo donde podemos
 * configurar las opciones y ejecutar el explorador de archivos.
 * 
 */
public class DialogoEscaneador extends JDialog implements ActionListener,
		Constantes {
	private static final long serialVersionUID = -8545589672343226457L;
	// Objeto que contiene la lista de las rutas
	private JList listaRutas;
	// Boton para iniciar el escaneo
	private JButton btnEscanear, btnAniadirRuta, btnRemoverRuta;
	// Objeto para explorar los directorios en busca de archivos
	private ExploradorRecursivoArchivos explorador;
	// Paneles de la GUI
	private JPanel pnlOpciones, pnlRutas;
	// Casillas de los tipos de medios a buscar
	private JCheckBox chkMusica, chkImagen, chkVideo;
	// Extensiones a buscar
	private String extensiones;
	// Rutas en donde se desea buscar
	private Vector<String> rutas;
	// Estos objetos los uso para determinar el ancho de la pantalla de tal
	// manera que podamos posicionar el dialogo en la mitad
	private GraphicsEnvironment entornoGrafico = GraphicsEnvironment
			.getLocalGraphicsEnvironment();
	private GraphicsDevice[] dispositivoGrafico = entornoGrafico
			.getScreenDevices();
	private DisplayMode modoPantalla = dispositivoGrafico[0].getDisplayMode();
	// objeto para aniadir rutas
	private JFileChooser selectorRuta;
	// Objeto biblioteca para almacenar los datos
	private Biblioteca biblio;
	// Objeto para almacenar los datos en una base de datos
	private TransaccionesSQLite sqlite;

	/**
	 * Constructor. Debe recibir la referencia al Frame que lo invocó y un
	 * título.
	 * 
	 * @param propietario
	 * @param titulo
	 */
	public DialogoEscaneador(Frame propietario) {
		super(propietario, "Escaneador archivos");

		// Iniciar los paneles de la GUI
		inicarPanelOpciones();
		iniciarPanelRutas();

		selectorRuta = new JFileChooser();
		selectorRuta.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

		// Iniciar el botón que escanéa
		btnEscanear = new JButton("Escanear", new ImageIcon(this.getClass()
				.getResource(IMG_REFRESCAR_16)));
		btnEscanear.setToolTipText("Escanear los directorios");
		btnEscanear.addActionListener(this);

		// Aniadir los paneles y el botón al diálogo
		getContentPane().add(pnlOpciones, BorderLayout.NORTH);
		getContentPane().add(pnlRutas);
		getContentPane().add(btnEscanear, BorderLayout.SOUTH);

		// iniciar interfaz de transacciones usando
		// el patron de disenio Bridge
		sqlite = new TransaccionesSQLite();
		// Iniciar la biblioteca de tal maner que use la implementación de
		// SQLite
		biblio = new AlmacenarInfoBibliotecaRefinado(sqlite);

		// Asignar tamanio y posicionar
		setSize(250, 300);
		setLocation((modoPantalla.getWidth() / 2) - (getWidth() / 2),
				(modoPantalla.getHeight() / 2) - (getHeight() / 2));
		
		cargarPreferencias();
		
		setVisible(true);
	}

	/**
	 * Iniciar el panel de las rutas en donde se va a buscar
	 */
	private void iniciarPanelRutas() {
		rutas = new Vector<String>();
		listaRutas = new JList();
		listaRutas.setVisibleRowCount(5);
		listaRutas
				.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		listaRutas.setListData(rutas);

		btnAniadirRuta = new JButton(new ImageIcon(this.getClass().getResource(
				IMG_ANIADIR_RUTA_16)));
		btnAniadirRuta.setToolTipText("Añadir una nueva ruta");
		btnAniadirRuta.addActionListener(this);

		btnRemoverRuta = new JButton(new ImageIcon(this.getClass().getResource(
				IMG_REMOVER_RUTA_16)));
		btnRemoverRuta.setToolTipText("Remover ruta seleccionada");
		btnRemoverRuta.addActionListener(this);

		// Iniciar panel para los botones
		JPanel panelAniadir = new JPanel();
		panelAniadir
				.setLayout(new BoxLayout(panelAniadir, BoxLayout.LINE_AXIS));
		panelAniadir.add(Box.createHorizontalGlue());
		panelAniadir.add(btnAniadirRuta);
		panelAniadir.add(btnRemoverRuta);

		pnlRutas = new JPanel(new BorderLayout());
		pnlRutas.add(panelAniadir, BorderLayout.NORTH);
		pnlRutas.add(listaRutas);
	}

	/**
	 * Iniciar el panel donde están los checkbox
	 */
	private void inicarPanelOpciones() {
		chkMusica = new JCheckBox("M\u00fasica", true);
		chkMusica.setToolTipText("Buscar archivos de audio");
		chkMusica.setSelected(false);
		chkMusica.setMnemonic('M');
		chkMusica.addActionListener(this);

		chkImagen = new JCheckBox("Im\u00e1genes", true);
		chkImagen.setToolTipText("Buscar im\u00e1genes y fotos");
		chkImagen.setSelected(false);
		chkImagen.setMnemonic('I');
		chkImagen.addActionListener(this);

		chkVideo = new JCheckBox("Videos", true);
		chkVideo.setToolTipText("Buscar archivos videos");
		chkVideo.setSelected(false);
		chkVideo.setMnemonic('V');
		chkVideo.addActionListener(this);

		pnlOpciones = new JPanel(new GridLayout(4, 1));
		pnlOpciones.add(new JLabel("\u00bfQu\u00e9 tipo de archivos desea buscar?"));
		pnlOpciones.add(chkMusica);
		pnlOpciones.add(chkImagen);
		pnlOpciones.add(chkVideo);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnEscanear) {
			// reiniciar extensiones
			extensiones = "";
			// verificar cual checkbox esta seleccionado
			if (chkMusica.isSelected())
				extensiones += EXTENSIONES_AUDIO;
			if (chkImagen.isSelected())
				extensiones += EXTENSIONES_IMAGEN;
			if (chkVideo.isSelected())
				extensiones += EXTENSIONES_VIDEO;
			// Iniciar el explorador
			explorador = new ExploradorRecursivoArchivos(extensiones);
			// Explorar cada una de las carpetas
			for (int i = 0; i < rutas.size(); i++)
				explorador.iniciarExploracion((String)rutas.get(i));
			guardarPreferencias();
			this.dispose();
		} else if (e.getSource() == chkMusica || e.getSource() == chkImagen
				|| e.getSource() == chkVideo) {
			// No permitir que se pueda presionar el boton a menos que haya al
			// menos un checkbox seleccionado
			if (!chkMusica.isSelected() && !chkImagen.isSelected()
					&& !chkVideo.isSelected())
				btnEscanear.setEnabled(false);
			else if (rutas.size() > 0)
				btnEscanear.setEnabled(true);
		} else if (e.getSource() == btnAniadirRuta) {
			aniadirRuta();
		} else if (e.getSource() == btnRemoverRuta) {
			removerRuta();
		}
	}

	private void removerRuta() {
		Object seleccionados[] = listaRutas.getSelectedValues();
		for (int i = 0; i < seleccionados.length; i++)
			rutas.remove(seleccionados[i]);
		listaRutas.setListData(rutas);
		if (rutas.size() == 0)
			btnEscanear.setEnabled(false);
	}

	private void aniadirRuta() {
		int resultado = selectorRuta.showOpenDialog(this);
		if (resultado == JFileChooser.CANCEL_OPTION)
			return;
		rutas.add(selectorRuta.getSelectedFile().toString());
		listaRutas.setListData(rutas);
		btnEscanear.setEnabled(true);
	}
	/**
	 * Este metodo carga las preferencias de la GUI
	 */
	private void cargarPreferencias(){
		String[] opciones = biblio.opcionesPorNombre("tipo-archivo");
		for(int i=0; i<opciones.length; i++){
			if(opciones[i].equals("img"))
				chkImagen.setSelected(true);
			if(opciones[i].equals("musica"))
				chkMusica.setSelected(true);
			if(opciones[i].equals("video"))
				chkVideo.setSelected(true);
		}
	}
	/**
	 * Este metodo guarda las preferencias de la GUI en la base de datos
	 */
	private void guardarPreferencias(){
		//borrar las rutas actuales
		biblio.quitarOpcionesPorNombre("ruta-exploracion");
		//borrar las rutas actuales
		biblio.quitarOpcionesPorNombre("tipo-archivo");
		//guardar las rutas de la lista
		for (int i = 0; i < rutas.size(); i++)
			biblio.aniadirOpcion("ruta-exploracion", (String)rutas.get(i));
		if(chkImagen.isSelected())//aniadir seleccion imagen
			biblio.aniadirOpcion("tipo-archivo", "img");
		if(chkVideo.isSelected())//aniadir seleccion video
			biblio.aniadirOpcion("tipo-archivo", "video");
		if(chkMusica.isSelected())//aniadir seleccion musica
			biblio.aniadirOpcion("tipo-archivo", "musica");
	}
}
