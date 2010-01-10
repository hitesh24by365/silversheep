package medios;

public class TipoArchivo {
	private String ruta;
	public TipoArchivo(String ruta){
		this.ruta = ruta;
	}
	public String toString(){
		return ruta;
	}
	public String getRuta(){
		return ruta;
	}
	public void setRuta(String ruta){
		this.ruta = ruta;
	}
}
