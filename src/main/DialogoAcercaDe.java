package main;

import java.awt.BorderLayout;
import java.awt.DisplayMode;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

/**
 * Muestra un cuadro de dialogo que contiene la informacion del proyecto, como
 * la version, los autores, la licienca, creditos, etc.
 * 
 */
public class DialogoAcercaDe extends JDialog implements ActionListener,
		Constantes {
	private static final long serialVersionUID = 1984723608505634922L;
	// Boton para cerrar cuadro de dialogo
	private JButton btnCerrar;
	private JLabel lblLogo;
	// Pestanias de contenido
	private JTabbedPane panelPestanias;
	// Areas para mostrar la información
	private JTextArea info, creditos;
	// Objetos para situar el dialogo en la mitad de la ventana
	private GraphicsEnvironment entornoGrafico = GraphicsEnvironment
			.getLocalGraphicsEnvironment();
	private GraphicsDevice[] dispositivoGrafico = entornoGrafico
			.getScreenDevices();
	private DisplayMode modoPantalla = dispositivoGrafico[0].getDisplayMode();

	/**
	 * El constructor recibe una referencia a la ventana que lo invoco
	 * 
	 * @param padre
	 */
	public DialogoAcercaDe(Frame padre) {
		super(padre, "Acerca de SilverSheep...");
		// Iniciar los objetos de la GUI
		iniciarComponentes();
	}

	/**
	 * Iniciar la GUI
	 */
	private void iniciarComponentes() {

		// iniciar el panel de pestañas
		panelPestanias = new JTabbedPane();

		lblLogo = new JLabel(new ImageIcon(this.getClass()
				.getResource(IMG_LOGO)));
		lblLogo.setText("SilverSheep " + VERSION);
		lblLogo.setHorizontalTextPosition(SwingConstants.CENTER);
		lblLogo.setVerticalTextPosition(SwingConstants.BOTTOM);
		lblLogo.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 25));

		// Iniciar areas de texto
		info = new JTextArea();
		info.setLineWrap(true);
		info.setEditable(false);
		info
				.setText("Copyleft 2009 Ning\u00fan derecho reservado. SilverSheep es software "
						+ "libre; usted es libre de copiar, modificar y redistribuir este programa "
						+ "bajo los t\u00e9rminos de la licencia GNU/GPL versi\u00f3n 3.0 "
						+ "o superior. SilverSheep se distribuye sin ning\u00fan tipo de garant\u00eda.\n\n"
						+ "\n\nMaterial no creado por mi, como el logo o los iconos, estan bajo la licencia que los autores "
						+ "hayan decidido para los mismos.");

		creditos = new JTextArea();
		creditos.setLineWrap(true);
		creditos.setEditable(false);
		creditos
				.setText("SilverSheep fue desarrollado por el equipo MinoTauro.\n\n"
						+ "Ferney Toledo <ferneytoledo@gmail.com>\n"
						+ "Marcela Guiza <marcelaguiza@gmail.com>\n"
						+ "Luis Fernando Arango <luispaisita@gmail.com>\n"
						+ "Cristian Castiblanco <cristian@elhacker.net>\n\n"
						+ "Iconos e im\u00e1genes:"
						+ "Iconos Gartoon Redux por Tweenk\n"
						+ "El logo de SilverSheep fue diseniado por Ferney Toledo <ferneytoledo@gmail.com>\n\n"
						+ "Librer\u00edas externas:\n"
						+ "API de para decodificar archivos MP3 de JavaZoom [JavaLayer www.javazoom.net/javalayer/javalayer.html]\n"
						+ "Conector JSQlite [www.ch-werner.de/javasqlite/]\n"
						+ "API de Tritonus para extracción de tags ID3 [www.tritonus.org]");

		// Aniadir objetos
		panelPestanias.addTab("Info", new JScrollPane(info));
		panelPestanias.addTab("Cr\u00e9ditos", new JScrollPane(creditos));

		btnCerrar = new JButton("Cerrar");
		btnCerrar.setMnemonic('C');
		btnCerrar.setToolTipText("Cerrar esta ventana");
		btnCerrar.addActionListener(this);

		setResizable(false);

		add(lblLogo, BorderLayout.NORTH);
		add(panelPestanias, BorderLayout.CENTER);
		add(btnCerrar, BorderLayout.SOUTH);

		// Mostrar dialogo
		setSize(274, 500);
		setLocation((modoPantalla.getWidth() / 2) - (getWidth() / 2),
				(modoPantalla.getHeight() / 2) - (getHeight() / 2));
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent evnt) {
		if (evnt.getSource() == btnCerrar)
			dispose();
	}

}
