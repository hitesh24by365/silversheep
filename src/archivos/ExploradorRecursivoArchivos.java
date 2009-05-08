package archivos;
import java.io.File;
import java.util.StringTokenizer;

import main.ArchivoMultimedia;
import main.Audio;
import main.Video;
import almacenamiento.Biblioteca;
import almacenamiento.AlmacenarInfoBibliotecaRefinado;
import almacenamiento.TransaccionesSQLite;
public class ExploradorRecursivoArchivos {
	private String[] extensiones;
	private Biblioteca biblio;
	private TransaccionesSQLite sqlite;
	private ArchivoMultimedia archivoTemp;
	public ExploradorRecursivoArchivos(String extensiones) {
		//iniciar interfaz de transacciones usando 
		//el patron de disenio Bridge
		sqlite = new TransaccionesSQLite();
		biblio = new AlmacenarInfoBibliotecaRefinado(sqlite);
		StringTokenizer tokens = new StringTokenizer(extensiones, "-");
		int cont = 0;
		this.extensiones = new String[tokens.countTokens()+1];
		while(tokens.hasMoreTokens()){
			this.extensiones[cont] = tokens.nextToken();
			cont++;
		}
	}
	public void iniciarExploracion(String ruta){
		File directorio = new File(ruta);
		if(directorio.isDirectory() && directorio.exists())
			explorar(ruta);
	}
	public void explorar(String ruta){
		File directorio = new File(ruta);
		String[] listado = directorio.list();
		for(int i=0;i<listado.length;i++){
			File archivo = new File(ruta+"/"+listado[i]);
			if(archivo.isDirectory()){
				if(archivo.canRead())
					explorar(archivo.getPath());
			}
			else{
				for(int j=0;j<extensiones.length-1;j++)
					if(listado[i].toLowerCase().endsWith(extensiones[j])){
						if(esVideo(listado[i]))
							archivoTemp = new Video();
						archivoTemp.setNombreArchivo(ruta+"/"+listado[i]);
						archivoTemp.iniciarDatos();
						biblio.aniadirArchivo(archivoTemp);
					}
			}
		}
	}
	private boolean esAudio(String archivo) {
		for(int i=0;i<extensiones.length;i++){
			if(archivo.endsWith(".avi"))
				return true;
		}
		return false;
	}
	private boolean esVideo(String archivo) {
		for(int i=0;i<extensiones.length;i++){
			if(archivo.endsWith(".avi"))
				return true;
		}
		return false;
	}
}