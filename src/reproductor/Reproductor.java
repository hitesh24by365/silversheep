package reproductor;

import main.Observador;

public interface Reproductor{
	public void reproducirMedio();
	public void anterior();
	public void siguiente();
	public void registrarObservador(Observador obs);
}