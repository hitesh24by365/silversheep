package listados;

abstract class DecoradorListado implements Listado{
	protected Listado listado;
	public DecoradorListado(Listado listado) {
		this.listado = listado;
	}
}
