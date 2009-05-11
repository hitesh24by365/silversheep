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
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import reproductor.CargarImagenes;
import reproductor.FabricaAudio;
import reproductor.FabricaImagenes;
import reproductor.FabricaReproductores;
import reproductor.FabricaVideo;
import archivos.ExploradorRecursivoArchivos;
import archivos.DialogoEscaneador;

public class Ventana extends JFrame implements ActionListener {
	private static final long serialVersionUID = -712789070387375137L;
	private JMenuBar barraMenu;
	private JMenu menuArchivo, menuAyuda, menuHerramientas;
	private JMenuItem itmAcercaDe, itmSalir, itmEscanear;
	private Container contenedor;
	private DialogoEscaneador dialogoEscanear;
	private PanelIntroduccion intro;
	private GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
    private GraphicsDevice[] gs = ge.getScreenDevices();
    private DisplayMode dm = gs[0].getDisplayMode();
    private JTabbedPane pestaniasReproductores;
    private FabricaReproductores fabricaReproductores;
    
	public Ventana() {
		super("SilverSheep - Reproductor de medios");
		contenedor = getContentPane();
		contenedor.setLayout(new BorderLayout(60,16));

		iniciarBarraMenu();
		
		intro = new PanelIntroduccion(this);
		pestaniasReproductores = new JTabbedPane();
		aniadirElementoPestania("Intro",(JPanel)intro);
		contenedor.add(pestaniasReproductores);

		setSize(800, 600);
		setLocation((dm.getWidth()/2)-(getWidth()/2), (dm.getHeight()/2)-(getHeight()/2));
		setVisible(true);
	}

	private void aniadirElementoPestania(String titulo, JPanel panel) {
		// TODO 
		pestaniasReproductores.addTab(titulo, new ImageIcon(), panel);
		pestaniasReproductores.setTabComponentAt(pestaniasReproductores.getTabCount()-1, new Pestania(pestaniasReproductores));
		pestaniasReproductores.setSelectedIndex(pestaniasReproductores.getTabCount()-1);
	}

	public void iniciarBarraMenu() {
		//menu archivo
		menuArchivo = new JMenu("Archivo");
		menuArchivo.setMnemonic('A');

		itmSalir = new JMenuItem("Salir");
		itmSalir.setMnemonic('S');
		itmSalir.addActionListener(this);

		menuArchivo.add(itmSalir);

		//menu archivo
		menuHerramientas = new JMenu("Herramientas");
		menuHerramientas.setMnemonic('H');

		itmEscanear = new JMenuItem("Escanear");
		itmEscanear.setMnemonic('E');
		itmEscanear.addActionListener(this);

		menuHerramientas.add(itmEscanear);

		//menu ayuda
		menuAyuda = new JMenu("Ayuda");
		menuAyuda.setMnemonic('y');

		itmAcercaDe = new JMenuItem("Acerca de...");
		itmAcercaDe.setMnemonic('c');
		itmAcercaDe.addActionListener(this);

		menuAyuda.add(itmAcercaDe);
		
		//barra de menu
		barraMenu = new JMenuBar();
		barraMenu.add(menuArchivo);
		barraMenu.add(menuHerramientas);
		barraMenu.add(menuAyuda);
		setJMenuBar(barraMenu);
	}
	
	private void abrirDialogoEscaneador(){
		new DialogoEscaneador(this, "Busca lo que quieras");
	}
	
	private void abrirDialogoAcercaDe(){
		new DialogoAcercaDe(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == itmSalir) {
			System.exit(0);
		} else if (e.getSource() == itmAcercaDe) {
			abrirDialogoAcercaDe();
		}
		else if (e.getSource() == itmEscanear){
			abrirDialogoEscaneador();
		}
	}
	
	public void aniadirReproductor(String tipo){
		if(tipo.equals(Constantes.TAB_IMAGEN))
			fabricaReproductores = new FabricaImagenes();
		else if(tipo.equals(Constantes.TAB_AUDIO))
			fabricaReproductores = new FabricaAudio();
		else if(tipo.equals(Constantes.TAB_VIDEO))
			fabricaReproductores = new FabricaVideo();
		aniadirElementoPestania(tipo,fabricaReproductores.crearReproductor(this));
	}
}