package main;

public interface ObservadorReproduccionPestania {
	public void cambioReproduccion(boolean reproduciendo);
	public void cambioSiguiente(boolean habilitado);
	public void cambioAnterior(boolean habilitado);
}
