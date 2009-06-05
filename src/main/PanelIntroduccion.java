package main;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingConstants;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;

/**
 * Panel de bienvenida a SilverSheep
 * 
 */
public class PanelIntroduccion extends JPanel implements ActionListener,
		Constantes {
	private static final long serialVersionUID = -5584530084045706794L;
	// Declarar botones
	private JButton btnAudio, btnImagen, btnVideo, btnBiblio;
	// Declarar panel botones
	private JPanel panelBotones;
	// Referencia a la ventana principal
	private Ventana ventanaPrincipal;

	/**
	 * Constructor que recibe referencia a la ventana padre
	 * 
	 * @param padre
	 */
	public PanelIntroduccion(Ventana padre) {
		// Asignar layout
		setLayout(new BorderLayout());

		ventanaPrincipal = padre;

		// Iniciar botones
		btnBiblio = new JButton("Gestionar la biblioteca", new ImageIcon(this
				.getClass().getResource(IMG_BIBLIO)));
		btnBiblio.setHorizontalTextPosition(SwingConstants.CENTER);
		btnBiblio.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnBiblio.setToolTipText("Gestionar la biblioteca");
		btnBiblio.setBorderPainted(false);
		btnBiblio.setContentAreaFilled(false);
		btnBiblio.addActionListener(this);

		btnAudio = new JButton("Cargar gestor de m\u00fasica", new ImageIcon(
				this.getClass().getResource(IMG_AUDIO)));
		btnAudio.setHorizontalTextPosition(SwingConstants.CENTER);
		btnAudio.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnAudio.setToolTipText("Abrir gestor de m\u00fasica");
		btnAudio.setBorderPainted(false);
		btnAudio.setContentAreaFilled(false);
		btnAudio.addActionListener(this);

		btnImagen = new JButton("Cargar gestor de im\u00e1genes",
				new ImageIcon(this.getClass().getResource(IMG_IMAGEN)));
		btnImagen.setHorizontalTextPosition(SwingConstants.CENTER);
		btnImagen.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnImagen.setToolTipText("Abrir gestor de im\u00e1genes");
		btnImagen.setBorderPainted(false);
		btnImagen.setContentAreaFilled(false);
		btnImagen.addActionListener(this);

		btnVideo = new JButton("Cargar gestor de videos", new ImageIcon(this
				.getClass().getResource(IMG_VIDEO)));
		btnVideo.setHorizontalTextPosition(SwingConstants.CENTER);
		btnVideo.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnVideo.setToolTipText("Abrir gestor de video");
		btnVideo.setContentAreaFilled(false);
		btnVideo.setBorderPainted(false);
		btnVideo.addActionListener(this);

		// Iniciar panel para los botones
		panelBotones = new JPanel();
		panelBotones
				.setLayout(new BoxLayout(panelBotones, BoxLayout.LINE_AXIS));
		panelBotones.add(Box.createHorizontalGlue());

		panelBotones.add(btnBiblio);
		panelBotones.add(btnAudio);
		panelBotones.add(btnImagen);
		panelBotones.add(btnVideo);
		add(panelBotones, BorderLayout.PAGE_END);

		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent evt) {
		if (evt.getSource() == btnImagen) {
			// Si se presiono el boton imagen, aniadir panel imagen
			ventanaPrincipal.aniadirReproductor(TAB_IMAGEN);
		} else if (evt.getSource() == btnAudio) {
			// Si se presiono el boton audio, aniadir panel audio
			ventanaPrincipal.aniadirReproductor(TAB_AUDIO);
		} else if (evt.getSource() == btnVideo) {
			// Si se presiono el boton video, aniadir panel video
			ventanaPrincipal.aniadirReproductor(TAB_VIDEO);
		} else if (evt.getSource() == btnBiblio) {
			// Si se presiono el boton biblioteca, aniadir panel biblioteca
			ventanaPrincipal.aniadirReproductor(TAB_BIBLIO);
		}
	}
}