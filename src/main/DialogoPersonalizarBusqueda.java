package main;

import java.awt.DisplayMode;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;


import almacenamiento.AlmacenarInfoBibliotecaRefinado;
import almacenamiento.Biblioteca;
import almacenamiento.TransaccionesSQLite;

public class DialogoPersonalizarBusqueda extends JDialog implements
		ActionListener {
	private static final long serialVersionUID = 2982524173720241542L;
	private JCheckBox chkNombre, chkArtista, chkAlbum, chkGenero;
	private JButton btnOK, btnCanelar;
	// Objeto biblioteca para almacenar los datos
	private Biblioteca biblio;
	// Objeto para almacenar los datos en una base de datos
	private TransaccionesSQLite sqlite;
	// Estos objetos los uso para determinar el ancho de la pantalla de tal
	// manera que podamos posicionar el dialogo en la mitad
	private GraphicsEnvironment entornoGrafico = GraphicsEnvironment
			.getLocalGraphicsEnvironment();
	private GraphicsDevice[] dispositivoGrafico = entornoGrafico
			.getScreenDevices();
	private DisplayMode modoPantalla = dispositivoGrafico[0].getDisplayMode();

	public DialogoPersonalizarBusqueda() {
		// definir disenio
		setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		setTitle("Configurar criterios de b\u00fasqueda");
		//Aniadir los paneles
		add(crearPanelCheckBox());
		add(crearPanelBotones());
		// iniciar interfaz de transacciones usando
		// el patron de disenio Bridge
		sqlite = new TransaccionesSQLite();
		// Iniciar la biblioteca de tal maner que use la implementación de
		// SQLite
		biblio = new AlmacenarInfoBibliotecaRefinado(sqlite);
		
		// Asignar tamanio y posicionar
		pack();
		setLocation((modoPantalla.getWidth() / 2) - (getWidth() / 2),
				(modoPantalla.getHeight() / 2) - (getHeight() / 2));
		cargarPreferencias();
		setVisible(true);
	}

	private JPanel crearPanelCheckBox() {
		JPanel panelChk = new JPanel();
		panelChk.setLayout(new GridLayout(3, 2));

		chkNombre = crearCheckBox("Buscar por nombre",
				"Incluir nombre en el criterio de b\u00fasqueda", 'n');

		chkArtista = crearCheckBox("Buscar por artista",
				"Incluir artista en el criterio de b\u00fasqueda", 'a');

		chkAlbum = crearCheckBox("Buscar por album",
				"Incluir album en el criterio de b\u00fasqueda", 'l');

		chkGenero = crearCheckBox("Buscar por g\u00e9nero",
				"Incluir g\u00e9nero en el criterio de b\u00fasqueda", 'g');
		
		panelChk.add(new JLabel("Criterios de b\u00fasqueda:"));
		panelChk.add(new JLabel());
		panelChk.add(chkNombre);
		panelChk.add(chkArtista);
		panelChk.add(chkAlbum);
		panelChk.add(chkGenero);

		return panelChk;
	}

	private JPanel crearPanelBotones() {
		JPanel panelAceptar = new JPanel();
		panelAceptar.setLayout(new BoxLayout(panelAceptar, BoxLayout.X_AXIS));
		
		btnOK = new JButton("Aceptar");
		btnOK.setToolTipText("Aceptar y cerrar");
		btnOK.addActionListener(this);
		btnOK.setMnemonic('A');
		
		btnCanelar = new JButton("Cancelar");
		btnCanelar.setToolTipText("Cancelar configuraci\u00f3n");
		btnCanelar.addActionListener(this);
		btnCanelar.setMnemonic('C');
		
		panelAceptar.add(Box.createHorizontalGlue());
		panelAceptar.add(btnOK);
		panelAceptar.add(Box.createHorizontalStrut(20));
		panelAceptar.add(btnCanelar);
		panelAceptar.add(Box.createHorizontalGlue());

		return panelAceptar;
	}

	private JCheckBox crearCheckBox(String titulo, String tooltip, char mnemonic) {
		JCheckBox chkBox = new JCheckBox(titulo);
		chkBox.setToolTipText(tooltip);
		chkBox.setSelected(false);
		chkBox.setMnemonic(mnemonic);
		chkBox.addActionListener(this);

		return chkBox;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnOK) {
			guardarPreferencias();
			this.dispose();
		}
		else if (e.getSource() == btnCanelar) {
			this.dispose();
		}
	}
	/**
	 * Este metodo carga las preferencias de la GUI
	 */
	private void cargarPreferencias(){
		String[] opciones = biblio.opcionesPorNombre("criterio-busqueda");
		for(int i=0; i<opciones.length; i++){
			if(opciones[i].equals("nombre"))
				chkNombre.setSelected(true);
			if(opciones[i].equals("artista"))
				chkArtista.setSelected(true);
			if(opciones[i].equals("album"))
				chkAlbum.setSelected(true);
			if(opciones[i].equals("genero"))
				chkGenero.setSelected(true);
		}
	}
	/**
	 * Este metodo guarda las preferencias de la GUI en la base de datos
	 */
	private void guardarPreferencias(){
		//borrar los criterios actuales
		biblio.quitarOpcionesPorNombre("criterio-busqueda");
		//guardar las rutas de la lista
		if(chkNombre.isSelected())//aniadir seleccion nombre
			biblio.aniadirOpcion("criterio-busqueda", "nombre");
		if(chkArtista.isSelected())//aniadir seleccion artista
			biblio.aniadirOpcion("criterio-busqueda", "artista");
		if(chkAlbum.isSelected())//aniadir seleccion album
			biblio.aniadirOpcion("criterio-busqueda", "album");
		if(chkGenero.isSelected())//aniadir seleccion genero
			biblio.aniadirOpcion("criterio-busqueda", "genero");
	}
}