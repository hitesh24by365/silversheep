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

import sun.awt.X11.Screen;

public class DialogoAcercaDe extends JDialog implements ActionListener{
	private static final long serialVersionUID = 1984723608505634922L;
	private JButton btnCerrar;
	private JLabel lblImagen;
	private JTabbedPane panelPestanias;
	private JTextArea info, creditos;
	private GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
    private GraphicsDevice[] gs = ge.getScreenDevices();
    private DisplayMode dm = gs[0].getDisplayMode();
    public DialogoAcercaDe(Frame parent) {
        super(parent, "Acerca de SilverSheep");
        iniciarComponentes();
    }

    public void cerrarDialogo() {
        dispose();
    }

    private void iniciarComponentes() {
    	
    	panelPestanias = new JTabbedPane();
    	
    	lblImagen = new JLabel(new ImageIcon(Constantes.IMG_LOGO));
    	lblImagen.setText("SilverSheep "+Constantes.VERSION);
    	lblImagen.setHorizontalTextPosition(SwingConstants.CENTER);
    	lblImagen.setVerticalTextPosition(SwingConstants.BOTTOM);
    	lblImagen.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 25));
    	
    	info = new JTextArea();
    	info.setLineWrap(true);
    	info.setText("Copyleft 2009 Ningun derecho reservado. SilverSheep es software " +
    			"libre; usted es libre de copiar, modificar y redistribuir este programa " +
    			"bajo los terminos de la licencia GNU/GPL version 3.0 " +
    			"o superior. SilverSheep se distribuye sin ningun tipo de garantia.\n\n" +
    			"La licencia sera mantenida durante el desarrollo y, " +
    			"ojala, despues de el, a menos que alguno de los colaboradores " +
    			"exprese su inconformidad con la misma.\n\nMaterial no creado por mi, " +
    			"como el logo o los iconos, estan bajo la licencia que los autores " +
    			"hayan decidido para los mismos");
    	
    	creditos = new JTextArea();
    	creditos.setLineWrap(true);
    	creditos.setText("SilverSheep fue desarrollado por el equipo MinoTauro.\n\n" +
    			"Ferney Toledo <bla@bla.com>\n" +
    			"Marcela Guiza <bla@bla.com>\n" +
    			"Luis Arango <bla@bla.com>\n" +
    			"Cristian Castiblanco <bla@bla.com>\n\n" +
    			"Iconos e imagenes:" +
    			"Iconos Comix <bla@bla.com> by bbbbbb\n" +
    			"El logo de SilverSheep fue diseniado por Ferney Toledo <blabla>\n\n" +
    			"Librerias externas:\n" +
    			"Se uso el api de MPR bla de Javazoom\n" +
    			"Se uso el conector JSqlite blabla\n" +
    			"Se uso el conector JSqlite blabla\n" +
    			"");
    	
    	panelPestanias.addTab("Info", new JScrollPane(info));
    	panelPestanias.addTab("Creditos", new JScrollPane(creditos));
    	
        btnCerrar = new JButton("Cerrar");
        btnCerrar.setMnemonic('C');
        btnCerrar.setToolTipText("Cerrar esta ventana");
        btnCerrar.addActionListener(this);
        
        setResizable(false);

        add(lblImagen, BorderLayout.NORTH);
        add(panelPestanias, BorderLayout.CENTER);
        add(btnCerrar, BorderLayout.SOUTH);
        
        setSize(274,500);
        setLocation((dm.getWidth()/2)-(getWidth()/2), (dm.getHeight()/2)-(getHeight()/2));
        setVisible(true);
    }

	@Override
	public void actionPerformed(ActionEvent evnt) {
		if(evnt.getSource() == btnCerrar)
			cerrarDialogo();
	}
    
}
