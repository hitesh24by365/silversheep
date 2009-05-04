package archivos;
import java.io.File;
import java.util.StringTokenizer;
public class ExploradorRecursivoArchivos {
	private String[] extensiones; 
	public ExploradorRecursivoArchivos(String extensiones) {
		StringTokenizer tokens = new StringTokenizer(extensiones, "-");
		int cont = 0;
		this.extensiones = new String[tokens.countTokens()+1];
		while(tokens.hasMoreTokens()){
			this.extensiones[cont] = tokens.nextToken();
			cont++;
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
				for(int j=0;j<extensiones.length-1;j++)
					if(listado[i].toLowerCase().endsWith(extensiones[j]))
						System.out.println(listado[i]);
			}
		}
	}
}