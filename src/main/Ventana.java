package main;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.DisplayMode;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import reproductor.FabricaAudio;
import reproductor.FabricaImagenes;
import reproductor.FabricaReproductores;
import reproductor.FabricaVideo;
import reproductor.Reproductor;
import archivos.DialogoEscaneador;

public class Ventana extends JFrame implements ActionListener {
	private static final long serialVersionUID = -712789070387375137L;
	// La barra de menu y sus items
	private JMenuBar barraMenu;
	private JMenu menuArchivo, menuAyuda, menuHerramientas;
	private JMenuItem itmAcercaDe, itmSalir, itmEscanear;
	// El contenedor de componentes de la GUI
	private Container contenedor;
	// Panel que contiene los componentes del intro
	private PanelIntroduccion intro;
	// Permiten posicionar la ventana en la mitad ;)
	private GraphicsEnvironment ge = GraphicsEnvironment
			.getLocalGraphicsEnvironment();
	private GraphicsDevice[] gs = ge.getScreenDevices();
	private DisplayMode dm = gs[0].getDisplayMode();
	// Pestanias de reproducciones
	private JTabbedPane pestaniasReproductores;
	// Fabrica de reproductores
	private FabricaReproductores fabricaReproductores;

	/**
	 * Constructor de la ventana
	 */
	public Ventana() {
		super("SilverSheep - Reproductor de medios");
		contenedor = getContentPane();
		contenedor.setLayout(new BorderLayout());

		// Iniciar los elementos del menu
		iniciarBarraMenu();

		// Iniciar panel de introducción
		intro = new PanelIntroduccion(this);
		pestaniasReproductores = new JTabbedPane();
		pestaniasReproductores.addTab("Bienvenido", new ImageIcon(
				"img/notas.png"), intro);
		pestaniasReproductores.setTabComponentAt(pestaniasReproductores
				.getTabCount() - 1, new Pestania(pestaniasReproductores,
				Constantes.IMG_OVEJA_16, true));
		contenedor.add(pestaniasReproductores);

		// Mostrar ventana
		setSize(800, 600);
		setLocation((dm.getWidth() / 2) - (getWidth() / 2),
				(dm.getHeight() / 2) - (getHeight() / 2));
		setVisible(true);
	}

	private void aniadirReproductorPestania(String titulo, Reproductor rep,
			String icono) {
		pestaniasReproductores.addTab(titulo, new ImageIcon(icono),
				(JPanel) rep);
		pestaniasReproductores.setTabComponentAt(pestaniasReproductores
				.getTabCount() - 1, new Pestania(pestaniasReproductores, rep,
				icono, true));
		pestaniasReproductores.setSelectedIndex(pestaniasReproductores
				.getTabCount() - 1);
	}

	/**
	 * Iniciar componentes de la barra de menu
	 */
	public void iniciarBarraMenu() {
		// Iniciar items de menu
		menuArchivo = new JMenu("Archivo");
		menuArchivo.setMnemonic('A');

		itmSalir = new JMenuItem("Salir");
		itmSalir.setMnemonic('S');
		itmSalir.addActionListener(this);

		menuArchivo.add(itmSalir);

		// menu archivo
		menuHerramientas = new JMenu("Herramientas");
		menuHerramientas.setMnemonic('H');

		itmEscanear = new JMenuItem("Escanear");
		itmEscanear.setMnemonic('E');
		itmEscanear.addActionListener(this);

		menuHerramientas.add(itmEscanear);

		// menu ayuda
		menuAyuda = new JMenu("Ayuda");
		menuAyuda.setMnemonic('y');

		itmAcercaDe = new JMenuItem("Acerca de...");
		itmAcercaDe.setMnemonic('c');
		itmAcercaDe.addActionListener(this);

		menuAyuda.add(itmAcercaDe);

		// barra de menu
		barraMenu = new JMenuBar();
		barraMenu.add(menuArchivo);
		barraMenu.add(menuHerramientas);
		barraMenu.add(menuAyuda);
		setJMenuBar(barraMenu);
	}

	/**
	 * Abrir dialogo que escanea directorios
	 */
	private void abrirDialogoEscaneador() {
		new DialogoEscaneador(this);
	}

	/**
	 * Abrir el diálogo del los créditos y licencia, re-gomelo ¿no?
	 */
	private void abrirDialogoAcercaDe() {
		new DialogoAcercaDe(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == itmSalir) {
			System.exit(0);
		} else if (e.getSource() == itmAcercaDe) {
			abrirDialogoAcercaDe();
		} else if (e.getSource() == itmEscanear) {
			abrirDialogoEscaneador();
		}
	}

	/**
	 * Aniadir reproductores/pestañas
	 * 
	 * @param tipo
	 */
	public void aniadirReproductor(String tipo) {
		String icono = "";
		if (tipo.equals(Constantes.TAB_IMAGEN)) {
			fabricaReproductores = new FabricaImagenes();
			icono = Constantes.IMG_IMAGEN_16;
		} else if (tipo.equals(Constantes.TAB_AUDIO)) {
			fabricaReproductores = new FabricaAudio();
			icono = Constantes.IMG_SONIDO_16;
		} else if (tipo.equals(Constantes.TAB_VIDEO)) {
			fabricaReproductores = new FabricaVideo();
			icono = Constantes.IMG_VIDEO_16;
		}
		aniadirReproductorPestania(tipo, fabricaReproductores
				.crearReproductor(this), icono);
	}
}