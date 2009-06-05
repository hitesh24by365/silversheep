package almacenamiento;

import main.Constantes;
import medios.Archivo;
import medios.Etiqueta;

import java.sql.*;
import java.util.StringTokenizer;
import java.util.Vector;

import com.sun.org.apache.xml.internal.serializer.utils.StringToIntTable;

/**
 * La clase {@link TransaccionesSQLite} implementa la interfaz
 * {@link ImplementadorMetodosAlmacenamiento} y define los métodos que permiten
 * almacenar los datos de la biblioteca en una base de datos SQLite
 * 
 */
public class TransaccionesSQLite implements ImplementadorMetodosAlmacenamiento,
		Constantes {
	// Ruta del controlador JDBC
	private final String CONTROLADOR_JDBC = BD_RUTA_CONTROLADOR_JDBC;
	// Nombre de la base de datos a usar
	private final String NOMBRE_BD = DB_NOMBRE_BASE_DATOS;
	// Esta variable se usará para crear las consultas de cualquier instrucción
	// SQL del programa
	private String consultaTemp;
	// Objeto para realizar la conexión
	private Connection conexion;
	// Objeto para ejecutar instrucciones SQL
	private Statement instruccion;
	// Objeto que almacena el resultado de las consultas
	private ResultSet resultados;
	// Objeto que almacena metadatos de las consultas
	private ResultSetMetaData metaDatos;

	/**
	 * Constructor de la clase {@link TransaccionesSQLite}
	 */
	public TransaccionesSQLite() {
		try {
			// Crear el conector e iniciar objetos de conexión
			Class.forName(CONTROLADOR_JDBC);
			conexion = DriverManager.getConnection(NOMBRE_BD);
			instruccion = conexion.createStatement();
			//instruccion = conexion.createStatement();
			resultados = null;
		} catch (ClassNotFoundException e) {
			if (DEBUG)
				e.printStackTrace();
		} catch (SQLException e) {
			System.err.println("Error SQL: " + consultaTemp);
			if (DEBUG)
				e.printStackTrace();
		}
	}

	/**
	 * Método para realizar consultas a la base de datos
	 * 
	 * @param consulta
	 * @return
	 */
	public ResultSet transaccionBD(String consulta) {
		try {
			resultados = instruccion.executeQuery(consulta);
		} catch (SQLException e) {
			System.err.println("Error SQL: " + consulta);
			if (DEBUG)
				e.printStackTrace();
		}
		return resultados;
	}

	@Override
	public void aniadirAlbum(String nombre, String descripcion) {
		if (noEsta(nombre, BD_ALBUM)) {
			try {
				instruccion.executeQuery("INSERT INTO XXXX VALUES()");
			} catch (SQLException e) {
				System.err.println("Error SQL: " + consultaTemp);
				if (DEBUG)
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
		if (noEsta(archivo.getNombreArchivo(), BD_ARCHIVO)) {
			try {
				String album = null;
				if(archivo.getAlbumDisco() !=null)
					album = archivo.getAlbumDisco().replaceAll("\"", COMILLAS_DOBLES); 
				// Construir la consulta para insertar un nuevo archivo
				consultaTemp = "INSERT INTO archivo (nombre,"
						+ "fechaAdicion, tamanioKB,"
						+ "longitud, codec, artista, genero, albumDisco ,esSoportado, contador)"
						+ " VALUES(\""
						+ archivo.getNombreArchivo()
						+ "\","
						+ "\""
						+ archivo.getFechaAdicionSQL()
						+ "\","
						+ "\""
						+ archivo.getTamanio()
						+ "\","
						+ "\""
						+ archivo.getLongitud()
						+ "\","
						+ "\""
						+ archivo.getCodec()
						+ "\","
						+ "\""
						+ archivo.getArtista()
						+ "\","
						+ "\""
						+ archivo.getGenero()
						+ "\","
						+ "\""
						+ album
						+ "\","
						+ "\""
						+ (archivo.esSoportado() ? "" : "no") + "\", 0);";
				instruccion.execute(consultaTemp);
				if(DEBUG)
					System.out.println("Sentencia SQL: "+consultaTemp);
			} catch (SQLException e) {
				System.err.println("Error SQL: " + consultaTemp);
				if (DEBUG)
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
		// verificar si algun tipo en especial ya se encuentra almacenado en la
		// base de datos
		if (tipo.equals(BD_ALBUM))
			consulta = "SELECT BLA BLE";
		else if (tipo.equals(BD_ARCHIVO))
			consulta = "SELECT nombre FROM archivo WHERE nombre=\"" + nombre
					+ "\"";
		else if (tipo.equals(BD_ETIQUETA))
			consulta = "SELECT HAY etiq";
		try {
			resultados = instruccion.executeQuery(consulta);
			metaDatos = resultados.getMetaData();
			// si hay filas, entonces el archivo ya se encuentra registrado
			if (resultados.next())
				return false;
		} catch (SQLException e) {
			System.err.println("Error SQL: " + consulta);
			if (DEBUG)
				e.printStackTrace();
		}
		return true;
	}

	@Override
	public void aniadirOpcion(String nombre, String valor) {
		try {
			// Construir la consulta para insertar un nuevo archivo
			consultaTemp = "INSERT INTO opciones (nombre, valor)"
					+ " VALUES(\"" + nombre + "\"," + "\"" + valor + "\");";
			instruccion.execute(consultaTemp);
		} catch (SQLException e) {
			System.err.println("Error SQL: " + consultaTemp);
			if (DEBUG)
				e.printStackTrace();
		}
	}

	@Override
	public void quitarOpcion(String nombre, String valor) {
		// TODO Auto-generated method stub

	}

	@Override
	public void quitarOpcionesPorNombre(String nombre) {
		try {
			// Construir la consulta para insertar un nuevo archivo
			consultaTemp = "DELETE FROM opciones WHERE nombre = \"" + nombre
					+ "\";";
			instruccion.execute(consultaTemp);
		} catch (SQLException e) {
			System.err.println("Error SQL: " + consultaTemp);
			if (DEBUG)
				e.printStackTrace();
		}
	}

	@Override
	public String[] opcionesPorNombre(String nombre) {
		String[] datos = null;
		int tamanio = 0;
		try {
			//TODO hacer que se pueda usar la función beforeFirst en vez de consultar dos veces la base de datos
			consultaTemp = "SELECT valor FROM opciones WHERE nombre = \"" + nombre + "\";";
			resultados = instruccion.executeQuery(consultaTemp);
			while (resultados.next())
				tamanio++;
			resultados = instruccion.executeQuery(consultaTemp);
			datos = new String[tamanio];
			tamanio = 0;
			while (resultados.next())
				// 1 significa la segunda columna de la tabla opciones
				datos[tamanio++] = (String) resultados.getObject(1);
		} catch (SQLException e) {
			System.err.println("Error SQL: " + consultaTemp);
			if (true)
				e.printStackTrace();
		}
		return datos;
	}

	@Override
	public Vector<Archivo> getTodosArchivos(String tipo) {
		String consulta = "SELECT * FROM archivo ";
		if(tipo.equals(EXTENSIONES_AUDIO))
			consulta += "ORDER BY artista ASC";
		if(tipo.equals(EXTENSIONES_IMAGEN))
			consulta += "ORDER BY nombre ASC";
		Vector<Archivo> archivos = new Vector<Archivo>();
		Archivo temp = null;
		int contador = 0;
		try {
			resultados = instruccion.executeQuery(consulta);
			while(resultados.next()){
				temp = new Archivo(resultados);
				if(temp.esTipo(tipo))
					archivos.add(temp);
				contador++;
			}
		} catch (SQLException e) {
			System.err.println("Error SQL: " + consulta);
			if (DEBUG)
				e.printStackTrace();
		}
		return archivos;
	}
}