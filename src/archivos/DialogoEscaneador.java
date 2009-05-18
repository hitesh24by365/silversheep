package archivos;

import java.awt.BorderLayout;
import java.awt.DisplayMode;
import java.awt.Frame;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;

import main.Constantes;

/**
 * La clase {@link DialogoEscaneador} nos proporciona un diálogo donde podemos
 * configurar las opciones y ejecutar el explorador de archivos.
 * 
 */
public class DialogoEscaneador extends JDialog implements ActionListener, Constantes {
	private static final long serialVersionUID = -8545589672343226457L;
	// Objeto que contiene la lista de las rutas
	private JList listaRutas;
	// Boton para iniciar el escaneo
	private JButton btnEscanear;
	// Objeto para explorar los directorios en busca de archivos
	private ExploradorRecursivoArchivos explorador;
	// Paneles de la GUI
	private JPanel pnlOpciones, pnlRutas;
	// Casillas de los tipos de medios a buscar
	private JCheckBox chkMusica, chkImagen, chkVideo;
	// Extensiones a buscar
	private String extensiones;
	// Rutas en donde se desea buscar
	private String[] rutas;
	// Estos objetos los uso para determinar el ancho de la pantalla de tal
	// manera que podamos posicionar el dialogo en la mitad
	private GraphicsEnvironment entornoGrafico = GraphicsEnvironment
			.getLocalGraphicsEnvironment();
	private GraphicsDevice[] dispositivoGrafico = entornoGrafico.getScreenDevices();
	private DisplayMode modoPantalla = dispositivoGrafico[0].getDisplayMode();

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

		// Iniciar el botón que escanéa
		// TODO poner una imagen al botón
		btnEscanear = new JButton("Escanear");
		btnEscanear
				.setToolTipText("Escanear los directorios en búsqueda de nuevos medios");
		btnEscanear.addActionListener(this);

		// Aniadir los paneles y el botón al diálogo
		getContentPane().add(pnlOpciones, BorderLayout.NORTH);
		getContentPane().add(pnlRutas);
		getContentPane().add(btnEscanear, BorderLayout.SOUTH);

		// Asignar tamanio y posicionar
		setSize(250, 300);
		setLocation((modoPantalla.getWidth() / 2) - (getWidth() / 2),
				(modoPantalla.getHeight() / 2) - (getHeight() / 2));
		setVisible(true);
	}

	/**
	 * Iniciar el panel de las rutas en donde se va a buscar
	 */
	private void iniciarPanelRutas() {
		rutas = new String[3];
		rutas[0] = "/home/compartido/Música";
		rutas[1] = "/home/compartido/Videos";
		rutas[2] = "/home/compartido/Imágenes";
		listaRutas = new JList(rutas);
		listaRutas.setVisibleRowCount(5);
		listaRutas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		pnlRutas = new JPanel(new BorderLayout());
		pnlRutas.add(new JLabel("Rutas:"), BorderLayout.NORTH);
		pnlRutas.add(listaRutas);
	}

	/**
	 * Iniciar el panel donde están los checkbox
	 */
	private void inicarPanelOpciones() {
		chkMusica = new JCheckBox("Música", true);
		chkMusica.setToolTipText("Buscar archivos de audio");
		chkMusica.setMnemonic('M');
		chkMusica.addActionListener(this);

		chkImagen = new JCheckBox("Imágenes", true);
		chkImagen.setToolTipText("Buscar imágenes y fotos");
		chkImagen.setMnemonic('I');
		chkImagen.addActionListener(this);

		chkVideo = new JCheckBox("Videos", true);
		chkVideo.setToolTipText("Buscar archivos videos");
		chkVideo.setMnemonic('V');
		chkVideo.addActionListener(this);

		pnlOpciones = new JPanel(new GridLayout(4, 1));
		pnlOpciones.add(new JLabel("¿Qué tipo de archivos desea buscar?"));
		pnlOpciones.add(chkMusica);
		pnlOpciones.add(chkImagen);
		pnlOpciones.add(chkVideo);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnEscanear) {
			// reiniciar extensiones
			extensiones = "";
			// verificar cual checkbox está seleccionado
			if (chkMusica.isSelected())
				extensiones += EXTENSIONES_AUDIO;
			if (chkImagen.isSelected())
				extensiones += EXTENSIONES_IMAGEN;
			if (chkVideo.isSelected())
				extensiones += EXTENSIONES_VIDEO;
			// Iniciar el explorador
			explorador = new ExploradorRecursivoArchivos(extensiones);
			// Explorar cada una de las carpetas
			for (int i = 0; i < rutas.length; i++) {
				explorador.iniciarExploracion(rutas[i]);
			}
		} else if (e.getSource() == chkMusica || e.getSource() == chkImagen
				|| e.getSource() == chkVideo) {
			// No permitir que se pueda presionar el botón a menos que haya al
			// menos un checkbox seleccionado
			if (!chkMusica.isSelected() && !chkImagen.isSelected()
					&& !chkVideo.isSelected())
				btnEscanear.setEnabled(false);
			else
				btnEscanear.setEnabled(true);
		}
	}
}
