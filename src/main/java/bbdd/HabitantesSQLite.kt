package bbdd

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.elsda.tierramedia_pac.habitantes.HabitanteTierraMedia

/**
 * Clase HabitantesSQLite para crear el CRUD de la Base de Datos
 *
 */
class HabitantesSQLite(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    // Nombre y versión de la Base de Datos
    companion object {
        private const val DATABASE_NAME = "tierraMedia.db"
        private const val DATABASE_VERSION = 1
    }

    // Método crear => establece las esctructuras de tablas de la Base de Datos
    override fun onCreate(db: SQLiteDatabase?) {
        // Creamos la tabla habitantes
        val crearTablaHabitantes = """
            CREATE TABLE habitantes(
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nombre TEXT NOT NULL,
                apellidos TEXT NOT NULL,
                edad INTEGER NOT NULL,
                raza TEXT NOT NULL,
                ubicacion TEXT NOT NULL,
                profesion TEXT NOT NULL
            )
        """.trimIndent()

        // Creamos la tabla de habitantes
        db?.execSQL(crearTablaHabitantes)

    }

    /**
     * Método para actualizar la versión de la BBDD
     */
    override fun onUpgrade(db: SQLiteDatabase?, viejaVersion: Int, nuevaVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS habitantes")
        onCreate(db)
    }

    /**
     * Inserta un habitante en la BBDD
     * @param habitante datos del nuevo habitante
     * @return el identificador del habitante
     */
    fun insertarHabitante(habitante: HabitanteTierraMedia): Long {
        // TODO completar el método insertar habitante
        val db = writableDatabase // Accedemos a la BBDD en modo escritura
        // Creamos un objeto ContentValues para añadir los valores del habitante
        val valores = ContentValues().apply {
            // Añadimos los valores del habitante
            put("nombre", habitante.nombre)
            put("apellidos", habitante.apellidos)
            put("edad", habitante.edad)
            put("raza", habitante.raza)
            put("ubicacion", habitante.ubicacion)
            put("profesion", habitante.profesion)
        }

        // Insertamos el nuevo habitante en la BBDD y mostramos su ID
        val id = db.insert("habitantes", null, valores)
        db.close() //Cerramos conexión
        return id //Devolvemos el ID del habitante

    }

    /**
     * Actualiza los datos de un Habitante
     * @param idHabitante el identificador del habitante
     * @param habitante los datos del habitante
     */
    fun actualizarHabitante(idHabitante: Int, habitante: HabitanteTierraMedia) {
        // TODO completar el método actualizar habitante
        val db = writableDatabase // Accedemos a la BBDD en modo escritura
        val valores = ContentValues().apply {
            // Añadimos los valores del habitante
            put("nombre", habitante.nombre)
            put("apellidos", habitante.apellidos)
            put("edad", habitante.edad)
            put("raza", habitante.raza)
            put("ubicacion", habitante.ubicacion)
            put("profesion", habitante.profesion)
        }
        // Actualizamos el habitante en la BBDD
        db.update("habitantes", valores, "id=?", arrayOf(idHabitante.toString()))
        db.close()
    }

    /**
     * Borra un habitante de la BBDD
     * @param idHabitante el identificador del habitante
     */
    fun borrarHabitante(idHabitante: Int) {
        // TODO completar el método borrar habitante
        val db = writableDatabase // Accedemos a la BBDD en modo escritura
        // Borramos el habitante de la BBDD
        db.delete("habitantes", "id=?", arrayOf(idHabitante.toString()))
        db.close()
    }

    /**
     * Obtenemos el número total de habitantes de la Tierra Media
     * @return el número total de habitantes de la Tierra Media
     */
    fun getNumeroHabitantes(): Int {
        val db = readableDatabase // Accedemos a la BBDD en sólo lectura
        val consulta = "SELECT count(*) as numHabitantes FROM habitantes"
        val cursor = db.rawQuery(consulta, null)

        // Accedemos a los datos de la consulta
        cursor.use {
            if (it.moveToFirst())
                return it.getInt(it.getColumnIndexOrThrow("numHabitantes"))
        }

        // Si no obtenemos el número de habitantes => devuelve -1
        return -1
    }

    /**
     * Obtenemos el número de habitantes de una raza de la Tierra Media
     * @param raza la raza que se quiere revisar
     * @return el número de habitantes de una raza de la Tierra Media
     */
    fun getNumeroHabitantesPorRaza(raza: String): Int {
        // TODO crear el código para obtener el número de habitantes por raza
        val db = readableDatabase // Accedemos a la BBDD en modo lectura
        val consulta = "SELECT count(*) as numHabitantes FROM habitantes WHERE raza=?" //Hacemos la consulta
        val cursor = db.rawQuery(consulta, arrayOf(raza)) //Ejecutamos la consulta
        cursor.use {
            if (it.moveToFirst()){
                return it.getInt(it.getColumnIndexOrThrow("numHabitantes")) //Devolvemos el número de habitantes

            }
        }

        return 0 //Si no se encuentran habitantes devuelve 0
    }

    /**
     * Método para listar los habitantes por raza
     * @param raza la raza escogida para listar
     * @return listado de habitantes por raza
     */
    fun getListadoPorRaza(raza: String): List<HabitanteTierraMedia> {
        val db = readableDatabase // Accedemos a la BBDD en modo lectura
        val listaHabitantes = mutableListOf<HabitanteTierraMedia>()

        // Recorremos los valores de la consulta
        try {
            val consulta = "SELECT * FROM habitantes WHERE raza=? ORDER BY id DESC"
            db.rawQuery(consulta, arrayOf(raza)).use { cursor ->
                // Recorremos los valores de la consulta
                if (cursor.moveToFirst()) {
                    do {
                        // Cogemos los valores de un habitante
                        val nombre = cursor.getString(cursor.getColumnIndexOrThrow("nombre"))
                        val apellidos = cursor.getString(cursor.getColumnIndexOrThrow("apellidos"))
                        val edad = cursor.getInt(cursor.getColumnIndexOrThrow("edad"))
                        val razaObtenida = cursor.getString(cursor.getColumnIndexOrThrow("raza"))
                        val ubicacion = cursor.getString(cursor.getColumnIndexOrThrow("ubicacion"))
                        val profesion = cursor.getString(cursor.getColumnIndexOrThrow("profesion"))
                        // Creamos el objeto habitante con los datos y lo añadimos a la lista
                        val habitante = HabitanteTierraMedia(
                            nombre,
                            apellidos,
                            edad,
                            razaObtenida,
                            ubicacion,
                            profesion
                        )
                        listaHabitantes.add(habitante)
                    } while (cursor.moveToNext())
                }
            }
        } catch (e: Exception) {
            // Lanzar una RuntimeException para no requerir manejo explícito
            throw RuntimeException("Error al obtener el listado por raza: ${e.message}", e)
        } finally {
            // Cerramos la BBDD aquí si no usamos el bloque use{}
            db.close()
        }
        return listaHabitantes
    }

    /**
     * Método para listar los habitantes por profesión
     * @param profesion la raza escogida para listar
     * @return listado de habitantes por profesión
     */
    fun getListadoPorProfesion(profesion: String): List<HabitanteTierraMedia> {
        //TODO crear el listado de habitantes por profesión
        val db = readableDatabase // Accedemos a la BBDD en modo lectura
        val listaHabitantes = mutableListOf<HabitanteTierraMedia>()
        val consulta = "SELECT * FROM habitantes WHERE profesion=? ORDER BY id DESC"
        db.rawQuery(consulta, arrayOf(profesion)).use{
            cursor ->
            if (cursor.moveToFirst()) {
                do {
                    val nombre = cursor.getString(cursor.getColumnIndexOrThrow("nombre"))
                    val apellidos = cursor.getString(cursor.getColumnIndexOrThrow("apellidos"))
                    val edad = cursor.getInt(cursor.getColumnIndexOrThrow("edad"))
                    val raza = cursor.getString(cursor.getColumnIndexOrThrow("raza"))
                    val ubicacion = cursor.getString(cursor.getColumnIndexOrThrow("ubicacion"))
                    val profesionObtenida = cursor.getString(cursor.getColumnIndexOrThrow("profesion"))

                    // Creamos el objeto habitante con los datos y lo añadimos a la lista
                    val habitante = HabitanteTierraMedia(
                        nombre,
                        apellidos,
                        edad,
                        raza,
                        ubicacion,
                        profesionObtenida
                    )
                    listaHabitantes.add(habitante)
                } while (cursor.moveToNext())
            }
        }
        db.close() //Se cierra la conexión
        return listaHabitantes //Devolvemos la lista de habitantes
    }
}
