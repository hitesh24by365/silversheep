package reproductor;

import main.ObservadorReproduccionPestania;

public interface Reproductor{
	public void reproducirMedio();
	public void anterior();
	public void siguiente();
	public void registrarObservador(ObservadorReproduccionPestania obs);
}