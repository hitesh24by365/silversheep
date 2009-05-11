package main;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingConstants;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

public class PanelIntroduccion extends JPanel implements ActionListener{
	private JButton btnAudio, btnImagen, btnVideo;
	private Box cajaHorizontal = Box.createHorizontalBox();
	private JPanel panelBotones;
	private Ventana ventanaPrincipal;
	public PanelIntroduccion(Ventana padre){
		setLayout(new BorderLayout());
		
		ventanaPrincipal =  padre;
		
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
			ventanaPrincipal.aniadirReproductor(Constantes.TAB_IMAGEN);
		}else if(evt.getSource() == btnAudio){
			ventanaPrincipal.aniadirReproductor(Constantes.TAB_AUDIO);
		}else if(evt.getSource() == btnVideo){
			ventanaPrincipal.aniadirReproductor(Constantes.TAB_VIDEO);
		}
	}
}
