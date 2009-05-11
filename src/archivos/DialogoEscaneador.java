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

public class DialogoEscaneador extends JDialog implements ActionListener {
	private static final long serialVersionUID = -8545589672343226457L;
	private JList listaRutas;
	private JButton btnEscanear;
	private ExploradorRecursivoArchivos explorador;
	private JPanel pnlOpciones, pnlRutas;
	private JCheckBox chkMusica, chkImagen, chkVideo;
	private String extensiones;
	private String[] rutas;
	private GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
    private GraphicsDevice[] gs = ge.getScreenDevices();
    private DisplayMode dm = gs[0].getDisplayMode();
    
	public DialogoEscaneador(Frame propietario, String titulo) {
		super(propietario, titulo);

		inicarPanelOpciones();
		iniciarPanelRutas();

		btnEscanear = new JButton("Escanear");
		btnEscanear.addActionListener(this);

		getContentPane().add(pnlOpciones, BorderLayout.NORTH);
		getContentPane().add(pnlRutas);
		getContentPane().add(btnEscanear, BorderLayout.SOUTH);
		
		setSize(250, 300);
		setLocation((dm.getWidth()/2)-(getWidth()/2), (dm.getHeight()/2)-(getHeight()/2));
		setVisible(true);
	}

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

	private void inicarPanelOpciones() {
		chkMusica = new JCheckBox("Música",true);
		chkMusica.setToolTipText("Buscar archivos de audio");
		chkMusica.setMnemonic('M');
		chkMusica.addActionListener(this);

		chkImagen = new JCheckBox("Imágenes",true);
		chkImagen.setToolTipText("Buscar imágenes y fotos");
		chkImagen.setMnemonic('I');
		chkImagen.addActionListener(this);

		chkVideo = new JCheckBox("Videos",true);
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
			extensiones = "";
			if (chkMusica.isSelected())
				extensiones += Constantes.EXTENSIONES_AUDIO;
			if (chkImagen.isSelected())
				extensiones += Constantes.EXTENSIONES_IMAGEN;
			if (chkVideo.isSelected())
				extensiones += Constantes.EXTENSIONES_VIDEO;
			explorador = new ExploradorRecursivoArchivos(extensiones);
			for (int i = 0; i < rutas.length; i++) {
				explorador.iniciarExploracion(rutas[i]);
			}
		}else if (e.getSource() == chkMusica ||e.getSource() == chkImagen ||e.getSource() == chkVideo){
			if(!chkMusica.isSelected() && !chkImagen.isSelected() && !chkVideo.isSelected())
				btnEscanear.setEnabled(false);
			else
				btnEscanear.setEnabled(true);
		}
	}
}
