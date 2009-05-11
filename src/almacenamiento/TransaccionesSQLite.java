package almacenamiento;

import main.Constantes;
import main.Etiqueta;
import medios.Archivo;
import medios.ArchivoMultimedia;

import java.sql.*;

import org.sqlite.*;

public class TransaccionesSQLite implements ImplementadorMetodosAlmacenamiento {
	private final String ControladorJDBC = "org.sqlite.JDBC";
	private final String nombreBD = "jdbc:sqlite:silversheep.db";
	private String consultaTemp;
	private Connection conexion;
	private Statement instruccion;
	private ResultSet resultados;
	private ResultSetMetaData metaDatos;

	public TransaccionesSQLite() {
		try {
			Class.forName(ControladorJDBC);
			conexion = DriverManager.getConnection(nombreBD);
			instruccion = conexion.createStatement();
			resultados = null;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			System.err.println("Error SQL: "+consultaTemp);
			if(Constantes.DEBUG)
				e.printStackTrace();
		}
	}

	public ResultSet transaccionBD(String consulta) {
		try {
			resultados = instruccion.executeQuery(consulta);
		} catch (SQLException e) {
			System.err.println("Error SQL: "+consulta);
			if(Constantes.DEBUG)
				e.printStackTrace();
		}
		return resultados;
	}

	@Override
	public void aniadirAlbum(String nombre, String descripcion) {
		if (noEsta(nombre, Constantes.BD_ALBUM)) {
			try {
				instruccion.executeQuery("INSERT INTO XXXX VALUES()");
			} catch (SQLException e) {
				System.err.println("Error SQL: "+consultaTemp);
				if(Constantes.DEBUG)
					e.printStackTrace();
			}
		}
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
	public void aniadirAlbum(String nombre, String descripcion,
			Archivo[] archivos) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void aniadirArchivo(Archivo archivo) {
		if (noEsta(archivo.getNombreArchivo(), Constantes.BD_ARCHIVO)) {
			try {
				consultaTemp = "INSERT INTO archivo (nombre," +
					"fechaAdicion, tamanioKB," +
					"longitud, codec, genero, albumDisco ,esSoportado)" +
					" VALUES('"+archivo.getNombreArchivo()+"'," +
					"'"+archivo.getFechaAdicionSQL()+"'," +
					"'"+archivo.getTamanio()+"'," +
					"'"+archivo.getLongitud()+"'," +
					"'"+archivo.getCodec()+"'," +
					"'"+archivo.getGenero()+"'," +
					"'"+archivo.getAlbumDisco()+"'," +
					"'"+(archivo.esSoportado()?"":"no")+"');";
				instruccion.execute(consultaTemp);
			} catch (SQLException e) {
				System.err.println("Error SQL: "+consultaTemp);
				if(Constantes.DEBUG)
					e.printStackTrace();
			}
		}
	}

	@Override
	public void aniadirArchivoAlbum(int id, Archivo[] archivo) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean noEsta(String nombre, String tipo) {
		String consulta = "";
		if (tipo.equals(Constantes.BD_ALBUM))
			consulta = "SELECT BLA BLE";
		else if (tipo.equals(Constantes.BD_ARCHIVO))
			consulta = "SELECT nombre FROM archivo WHERE nombre='"+nombre+"'";
		else if (tipo.equals(Constantes.BD_ETIQUETA))
			consulta = "SELECT HAY etiq";
		try {
			resultados = instruccion.executeQuery(consulta);
			metaDatos = resultados.getMetaData();
			if (resultados.next())
				return false;
		} catch (SQLException e) {
			System.err.println("Error SQL: "+consulta);
			if(Constantes.DEBUG)
				e.printStackTrace();
		}
		return true;
	}
}