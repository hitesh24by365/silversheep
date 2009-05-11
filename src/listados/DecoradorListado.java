package listados;

/**
 * Implementación de la interfaz Listado. Esto hace parte del patrón de diseño
 * Decorador
 * 
 */
abstract class DecoradorListado implements Listado {
	//Objeto listado
	protected Listado listado;

	/**
	 * Constructor de la clase.
	 * @param listado
	 */
	public DecoradorListado(Listado listado) {
		this.listado = listado;
	}
}
