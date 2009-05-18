package reproductor;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;

import main.Constantes;
import main.ObservadorReproduccionPestania;
import main.Ventana;

import java.util.Timer;
import java.util.TimerTask;

public class CargarImagenes extends JPanel implements ActionListener,
		Reproductor, Constantes {
	private static final long serialVersionUID = 1L;
	// Barra de herramientas y sus botones
	private JToolBar barraHerramientas;
	private JButton btnSiguiente, btnAnterior, btnReproducir;
	private JToggleButton btnFull;
	// Esto nos permite manipular el Fullscreen
	private GraphicsDevice grafica = GraphicsEnvironment
			.getLocalGraphicsEnvironment().getDefaultScreenDevice();
	private boolean estaFull = false;
	// Referencia al Padre
	private Ventana padre;
	private PanelImagen panelImagen;
	private String[] rutas;
	private int imagenActual = 0;

	private Timer temporizador;
	private ObservadorReproduccionPestania observador;
	//se esta reproduciendo algo?
	private boolean estadoReproduccion;
	public CargarImagenes(Ventana padre, String[] rutas) {
		setRutas(rutas);
		construirGUI(padre);
	}

	public CargarImagenes(Ventana padre) {
		setRutas();
		construirGUI(padre);
	}

	private void setRutas() {
		rutas = new String[6];
		rutas[0] = "ejemplo/chiva.jpg";
		rutas[1] = "ejemplo/black-sheep.png";
		rutas[2] = "ejemplo/baby-sheep.jpg";
		rutas[3] = "ejemplo/sheep-family.jpg";
		rutas[4] = "ejemplo/sheep-sleep.jpg";
		rutas[5] = "ejemplo/sheep-suspicious.jpg";

		// rutas = new String[0];
	}

	public String[] getRutas() {
		return rutas;
	}

	public void setRutas(String[] rutas) {
		this.rutas = rutas;
	}

	public void construirGUI(Ventana padre) {
		this.setLayout(new BorderLayout());

		btnSiguiente = new JButton(new ImageIcon(this.getClass().getResource(
				IMG_SIGUIENTE_30)));
		btnSiguiente.addActionListener(this);
		btnSiguiente.setToolTipText("Siguiente Imagen");

		btnAnterior = new JButton(new ImageIcon(this.getClass().getResource(
				IMG_ANTERIOR_30)));
		btnAnterior.addActionListener(this);
		btnAnterior.setToolTipText("Anterior imagen");

		btnReproducir = new JButton(new ImageIcon(this.getClass()
				.getResource(IMG_REPRODUCIR_30)));
		btnReproducir.addActionListener(this);
		btnReproducir.setToolTipText("Reproducir");

		btnFull = new JToggleButton(new ImageIcon(this.getClass().getResource(
				IMG_FULLSCREEN_30)));
		btnFull.addActionListener(this);
		btnFull.setToolTipText("Pantalla completa");

		add(crearBarraHerramientas(), BorderLayout.NORTH);

		this.padre = padre;

		if (hayImagenes())
			panelImagen = new PanelImagen(obtenerPrimeraImagen());
		else
			panelImagen = new PanelImagen();
		cambiarEstadoBotones();
		add(panelImagen, BorderLayout.CENTER);

		temporizador = new Timer();
	}

	class CambiarImagen extends TimerTask {
		public void run() {
			if (estadoReproduccion && rutas.length - 1 != imagenActual)
				siguiente();
			else {
				try {
					temporizador.wait();
				} catch (Exception e) {
					e.printStackTrace();
				}
				estadoReproduccion = true;
				reproducirMedio();
			}
		}
	}

	private String obtenerPrimeraImagen() {
		return rutas[0];
	}

	private boolean hayImagenes() {
		if (rutas.length > 0)
			return true;
		else
			return false;
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnFull) {
			if (btnFull.isSelected()) {
				padre.dispose();
				padre.setUndecorated(true);
				grafica.setFullScreenWindow(padre);
				this.add(barraHerramientas, BorderLayout.NORTH);
			} else {
				padre.dispose();
				padre.setUndecorated(false);
				padre.setVisible(true);
				grafica.setFullScreenWindow(null);
				this.add(barraHerramientas, BorderLayout.NORTH);
			}
			estaFull = !estaFull;
		} else if (e.getSource() == btnReproducir) {
			reproducirMedio();
		} else if (e.getSource() == btnSiguiente) {
			siguiente();
		} else if (e.getSource() == btnAnterior) {
			anterior();
		}
	}

	/**
	 * Reproducir un slide de imágenes
	 */
	public void reproducirMedio() {
		estadoReproduccion = !estadoReproduccion;
		cambiarEstadoBotonReproduccion();
	}

	public void cambiarEstadoBotonReproduccion(){
		if (estadoReproduccion) {
			btnReproducir.setIcon(new ImageIcon(this.getClass().getResource(
					IMG_PAUSAR_30)));
			if (rutas.length - 1 == imagenActual) {
				imagenActual = 0;
				panelImagen.cambiarImagen(rutas[imagenActual]);
				cambiarEstadoBotones();
			}
			temporizador.schedule(new CambiarImagen(), 3 * 1000);
		} else{
			btnReproducir.setIcon(new ImageIcon(this.getClass().getResource(
					IMG_REPRODUCIR_30)));
		}
		if(observador != null)
			observador.cambioReproduccion(estadoReproduccion);
	}
	public void siguiente() {
		++imagenActual;
		panelImagen.cambiarImagen(rutas[imagenActual]);
		cambiarEstadoBotones();
		if (estadoReproduccion && rutas.length - 1 != imagenActual)
			temporizador.schedule(new CambiarImagen(), 3 * 1000);
		else
		{
			estadoReproduccion = false;
			cambiarEstadoBotonReproduccion();
		}
	}

	public void anterior() {
		--imagenActual;
		panelImagen.cambiarImagen(rutas[imagenActual]);
		cambiarEstadoBotones();
	}

	// hay que mejorar este metodo para que no sea tan repetitivo
	private void cambiarEstadoBotones() {
		if (rutas.length - 1 == imagenActual)
			btnSiguiente.setEnabled(false);
		else
			btnSiguiente.setEnabled(true);
		if (imagenActual == 0)
			btnAnterior.setEnabled(false);
		else
			btnAnterior.setEnabled(true);
		if (observador != null) {
			observador.cambioSiguiente(btnSiguiente.isEnabled());
			observador.cambioAnterior(btnAnterior.isEnabled());
		}
	}

	// crear barra de herramientas
	private Component crearBarraHerramientas() {
		barraHerramientas = new JToolBar();
		barraHerramientas.add(btnAnterior);
		barraHerramientas.add(btnReproducir);
		barraHerramientas.add(btnSiguiente);
		barraHerramientas.add(btnFull);
		return barraHerramientas;
	}

	@Override
	public void registrarObservador(ObservadorReproduccionPestania obs) {
		this.observador = obs;
	}
}