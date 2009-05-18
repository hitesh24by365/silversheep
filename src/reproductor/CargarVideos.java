package reproductor;

import javax.swing.JFrame;
import javax.swing.JPanel;

import main.ObservadorReproduccionPestania;


public class CargarVideos extends JPanel implements Reproductor{

	private static final long serialVersionUID = 3406839885057199917L;
	private ObservadorReproduccionPestania observador;


	public CargarVideos(JFrame padre) {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void anterior() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void reproducirMedio() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void siguiente() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void registrarObservador(ObservadorReproduccionPestania obs) {
		this.observador = obs;
		
	}

}
