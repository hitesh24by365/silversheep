package archivos;
import java.io.File;
public class ExploradorRecursivoArchivos {
	//private String ruta;
	private String[] extension; 
	public ExploradorRecursivoArchivos(String ruta, String[] extension) {
		//this.ruta = ruta;
		this.extension = extension;
		File directorio = new File(ruta);
		if(directorio.isDirectory()){
			for(int i=0;i<extension.length;i++)
				extension[i] = extension[i].toLowerCase();
		}
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
				for(int j=0;j<extension.length;j++)
					if(listado[i].toLowerCase().endsWith(extension[j]))
						System.out.println(listado[i]);
			}
		}
	}
}