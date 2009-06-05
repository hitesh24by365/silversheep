package reproductor;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import almacenamiento.AlmacenarInfoBibliotecaRefinado;
import almacenamiento.Biblioteca;
import almacenamiento.TransaccionesSQLite;

import listados.Coleccion;
import listados.ListaReproduccion;
import main.Constantes;
import main.ObservadorReproduccionPestania;
import medios.Archivo;

public class CargarAudio extends JPanel implements Reproductor, Constantes {

	/**
	 * 
	 */
	private static final long serialVersionUID = 293180966986541248L;
	private ObservadorReproduccionPestania observador;
	private JSplitPane panelSeparador;
	private Coleccion arbolColeccion;
	private ListaReproduccion listaReproduccion;

	public CargarAudio(JFrame padre) {
		setLayout(new BorderLayout());
		arbolColeccion = new Coleccion();
		listaReproduccion = new ListaReproduccion();
		
		panelSeparador = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		panelSeparador.setLeftComponent(arbolColeccion);
		panelSeparador.setRightComponent(listaReproduccion);
		
		arbolColeccion.setListaRepro(listaReproduccion);

		panelSeparador.setDividerLocation(200);

		panelSeparador.setPreferredSize(new Dimension(500, 300));
		add(panelSeparador);
	}

	@Override
	public void anterior() {
		listaReproduccion.anterior();
	}

	@Override
	public void reproducirMedio() {
		listaReproduccion.reproducirMedio();
	}

	@Override
	public void siguiente() {
		listaReproduccion.siguiente();

	}

	@Override
	public void registrarObservador(ObservadorReproduccionPestania obs) {
		this.observador = obs;
		this.listaReproduccion.registrarObservador(this.observador);
	}

	@Override
	public void detener() {
		// TODO Auto-generated method stub
		
	}

}