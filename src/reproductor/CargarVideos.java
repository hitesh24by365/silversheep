package reproductor;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import main.Constantes;
import main.ObservadorReproduccionPestania;


public class CargarVideos extends JPanel implements Reproductor, Constantes{

	private static final long serialVersionUID = 3406839885057199917L;
	private ObservadorReproduccionPestania observador;

	public CargarVideos(JFrame padre) {
		setLayout(new BorderLayout());
		JLabel oveja = new JLabel(new ImageIcon(this.getClass().getResource(IMG_OVEJA_MUERTA)));
		oveja.setText("A\u00fan en desarrollo ;)");
		oveja.setHorizontalTextPosition(SwingConstants.CENTER);
		oveja.setVerticalTextPosition(SwingConstants.BOTTOM);
		oveja.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 40));
		add(oveja, BorderLayout.NORTH);
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

	@Override
	public void detener() {
		// TODO Auto-generated method stub
		
	}

}
