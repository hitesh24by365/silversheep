package almacenamiento;

import medios.Archivo;
import medios.Etiqueta;

/**
 * La clase AlmacenarInfoBibliotecaRefinado ejecuta los metodos 
 * de la clase Biblioteca para un tipo de Implementacion de almacenamiento
 * específico. De tal manera es posible abstraer la manera en que
 * se almacenan los datos de la biblioteca
 * 
 * Esta clase fue construida basandose en el patrón de diseño Bridge
 */
public class AlmacenarInfoBibliotecaRefinado implements Biblioteca {
	//objeto que implementa los metodos de almacenamiento
	private ImplementadorMetodosAlmacenamiento implementador;
	public AlmacenarInfoBibliotecaRefinado(ImplementadorMetodosAlmacenamiento imp) {
		this.implementador = imp;
	}
	@Override
	public void aniadirAlbum(String nombre, String descripcion) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void aniadirAlbum(String nombre, String descripcion,
			Archivo[] archivos) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void aniadirArchivo(Archivo archivo) {
		implementador.aniadirArchivo(archivo);
	}
	@Override
	public void aniadirArchivoAlbum(int id, Archivo[] archivo) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void aniadirEtiqueta(Etiqueta etiqueta) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void quitarAlbum(int id) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void quitarArchivo(int id) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void quitarArchivoAlbum(int id) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void quitarEtiqueta(int id) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public boolean noEsta(String nombre, String tipo) {
		return implementador.noEsta(nombre, tipo);
	}
	@Override
	public void aniadirOpcion(String nombre, String valor) {
		implementador.aniadirOpcion(nombre, valor);
	}
	@Override
	public void quitarOpcion(String nombre, String valor) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void quitarOpcionesPorNombre(String nombre) {
		implementador.quitarOpcionesPorNombre(nombre);
	}
	@Override
	public String[] opcionesPorNombre(String nombre) {
		return implementador.opcionesPorNombre(nombre);
	}
}
