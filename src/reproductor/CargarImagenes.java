package reproductor;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;

import main.Constantes;

import java.util.Timer;
import java.util.TimerTask;

public class CargarImagenes extends JPanel implements ActionListener, Reproductor {
	private static final long serialVersionUID = 1L;
	private JToolBar barraHerramientas;
	private JButton btnSiguiente, btnAnterior;
	private JToggleButton btnReproducir, btnFull;
	private GraphicsDevice grafica = GraphicsEnvironment
			.getLocalGraphicsEnvironment().getDefaultScreenDevice();
	private boolean estaFull = false;
	private JFrame padre;
	private PanelImagen imagen;
	private String[] rutas;
	private int imagenActual = 0;

	private Timer temporizador;

	public CargarImagenes(JFrame padre, String[] rutas) {
		setRutas(rutas);
		construirGUI(padre);
	}

	public String[] getRutas() {
		return rutas;
	}

	public void setRutas(String[] rutas) {
		this.rutas = rutas;
	}

	public CargarImagenes(JFrame padre) {
		setRutas();
		construirGUI(padre);
	}

	private void setRutas() {
		rutas = new String[0];
	}

	public void construirGUI(JFrame padre) {
		this.setLayout(new BorderLayout());

		btnSiguiente = new JButton(new ImageIcon(Constantes.IMG_SIGUIENTE_30));
		btnSiguiente.addActionListener(this);
		btnSiguiente.setToolTipText("Siguiente Imagen");

		btnAnterior = new JButton(new ImageIcon(Constantes.IMG_ANTERIOR_30));
		btnAnterior.addActionListener(this);
		btnAnterior.setToolTipText("Anterior imagen");

		btnReproducir = new JToggleButton(new ImageIcon(
				Constantes.IMG_REPRODUCIR_30));
		btnReproducir.addActionListener(this);
		btnReproducir.setToolTipText("Reproducir");

		btnFull = new JToggleButton(new ImageIcon(Constantes.IMG_FULLSCREEN_30));
		btnFull.addActionListener(this);
		btnFull.setToolTipText("Pantalla completa");

		add(crearBarraHerramientas(), BorderLayout.NORTH);

		this.padre = padre;

		if (hayImagenes())
			imagen = new PanelImagen(obtenerPrimeraImagen());
		else
			imagen = new PanelImagen();
		cambiarEstadoBotones();
		add(imagen, BorderLayout.CENTER);

		temporizador = new Timer();
	}

	class CambiarImagen extends TimerTask {
		public void run() {
			if(btnReproducir.isSelected() && rutas.length - 1 != imagenActual){
				siguiente();
				temporizador.schedule(new CambiarImagen(), 3*1000);
			}
			else{
				try{
				temporizador.wait();
				}catch(Exception e){}
				btnReproducir.setSelected(false);
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

	public void reproducirMedio() {
		if (btnReproducir.isSelected())
		{
			btnReproducir.setIcon(new ImageIcon(Constantes.IMG_PAUSAR_30));
			if(rutas.length - 1 == imagenActual){
				imagenActual = 0;
				imagen.cambiarImagen(rutas[imagenActual]);
				cambiarEstadoBotones();
			}
			temporizador.schedule(new CambiarImagen(), 3*1000);
		}
		else
			btnReproducir.setIcon(new ImageIcon(
					Constantes.IMG_REPRODUCIR_30));
	}

	public void siguiente() {
		++imagenActual;
		imagen.cambiarImagen(rutas[imagenActual]);
		cambiarEstadoBotones();
	}

	public void anterior() {
		--imagenActual;
		imagen.cambiarImagen(rutas[imagenActual]);
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
}