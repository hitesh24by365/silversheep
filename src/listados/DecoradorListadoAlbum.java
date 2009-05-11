package listados;

import javax.swing.JPanel;

import main.Album;

/**
 * Esta clase extiende {@link DecoradorListado} y aniade el atributo Album
 *
 */
public class DecoradorListadoAlbum extends DecoradorListado{
	private Album album;
	public DecoradorListadoAlbum(Listado listado) {
		super(listado);
	}
	@Override
	public JPanel obtenerPanel() {
		// TODO Auto-generated method stub
		return null;
	}
}
