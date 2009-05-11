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
 * @author gentoo
 *
 */
public class PanelIntroduccion extends JPanel implements ActionListener{
	private static final long serialVersionUID = -5584530084045706794L;
	// Declarar botones
	private JButton btnAudio, btnImagen, btnVideo;
	// Declarar panel botones
	private JPanel panelBotones;
	// Referencia a la ventana principal
	private Ventana ventanaPrincipal;
	/**
	 * Constructor que recibe referencia a la ventana padre
	 * @param padre
	 */
	public PanelIntroduccion(Ventana padre){
		//Asignar layout
		setLayout(new BorderLayout());
		
		ventanaPrincipal =  padre;
		
		//Iniciar botones
		btnAudio = new JButton("Cargar gestor de música",new ImageIcon(Constantes.IMG_AUDIO));
		btnAudio.setHorizontalTextPosition(SwingConstants.CENTER);
		btnAudio.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnAudio.setToolTipText("Abrir gestor de música");
		btnAudio.setBorderPainted(false);
		btnAudio.setContentAreaFilled(false);
		btnAudio.addActionListener(this);

		btnImagen = new JButton("Cargar gestor de imágenes",new ImageIcon(Constantes.IMG_IMAGEN));
		btnImagen.setHorizontalTextPosition(SwingConstants.CENTER);
		btnImagen.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnImagen.setToolTipText("Abrir gestor de imágenes");
		btnImagen.setBorderPainted(false);
		btnImagen.setContentAreaFilled(false);
		btnImagen.addActionListener(this);

		btnVideo = new JButton("Cargar gestor de videos", new ImageIcon(Constantes.IMG_VIDEO));
		btnVideo.setHorizontalTextPosition(SwingConstants.CENTER);
		btnVideo.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnVideo.setToolTipText("Abrir gestor de video");
		btnVideo.setContentAreaFilled(false);
		btnVideo.setBorderPainted(false);
		btnVideo.addActionListener(this);
		
		// Iniciar panel para los botones
		panelBotones = new JPanel();
		panelBotones.setLayout(new BoxLayout(panelBotones,BoxLayout.LINE_AXIS));
        panelBotones.add(Box.createHorizontalGlue());
		
		panelBotones.add(btnAudio);
		panelBotones.add(btnImagen);
		panelBotones.add(btnVideo);
		add(panelBotones, BorderLayout.PAGE_END);
		
		setVisible(true);
	}
	@Override
	public void actionPerformed(ActionEvent evt) {
		if(evt.getSource() == btnImagen){
			//Si se presionó el boton imagen, aniadir panel imagen
			ventanaPrincipal.aniadirReproductor(Constantes.TAB_IMAGEN);
		}else if(evt.getSource() == btnAudio){
			//Si se presionó el boton audio, aniadir panel audio
			ventanaPrincipal.aniadirReproductor(Constantes.TAB_AUDIO);
		}else if(evt.getSource() == btnVideo){
			//Si se presionó el boton videe, aniadir panel video
			ventanaPrincipal.aniadirReproductor(Constantes.TAB_VIDEO);
		}
	}
}
