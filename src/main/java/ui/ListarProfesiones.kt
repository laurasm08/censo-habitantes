package ui

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TableRow
import android.widget.TextView
import bbdd.HabitantesSQLite
import com.elsda.tierramedia_pac.R

class ListarProfesiones : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listar_profesiones)
        //TODO crear toda la lógica para que se muestre bien la información en el listado de profesiones
        //Listamos todas las profesiones
        val profesion = intent.getStringExtra("PROFESION") ?: ""

        //Botón para volver atrás
        val btnAtras = findViewById<ImageButton>(R.id.btnAtrasListadoProfesion)
        btnAtras.setOnClickListener {
            finish()
        }

        //Enseña el nombre de la profesión que elegimos
        val txtNombreProfesion = findViewById<TextView>(R.id.txtNombreProfesion)
        txtNombreProfesion.text = profesion

       //Obtenemos los habitantes de esa profesión
        val habitantesPorProfesion = HabitantesSQLite(this).getListadoPorProfesion(profesion)
        val tablaProfesiones = findViewById<LinearLayout>(R.id.layoutListadoProfesion) //Para ver las profesiones

        //Enseñamos en la tabla los hbaitantes con sus datos de la profesión elegida
        if (habitantesPorProfesion.isNotEmpty()) {
            for (habitante in habitantesPorProfesion) {
                val fila = TableRow(this)

                //Insertamos el nombre del habitante en la fila
                insertarTextoEnTabla(habitante.nombre, fila)
                //Insertamos los apellidos del habitante en la fila
                insertarTextoEnTabla(habitante.apellidos, fila)
                //Insertamos la raza del habitante en la fila
                insertarTextoEnTabla(habitante.raza, fila)
                //Insertamos la ubicación del habitante en la fila
                insertarTextoEnTabla(habitante.ubicacion, fila)

                //Añadimos la fila a la tabla
                 tablaProfesiones.addView(fila)
            }

        }

    }

    /**
     * Método para insertar texto en la tabla
     * @param texto cadena de texto a añadir en la fila
     * @param fila fila de la tabla donde añadiremos el texto
     */
    private fun insertarTextoEnTabla(texto: String, fila: TableRow) {
        val textView = TextView(this@ListarProfesiones).apply {
            text = texto
            // Ajusta estos layoutParams dependiendo de tu contenedor final
            layoutParams = TableRow.LayoutParams(
                0, // Usar 0 para el ancho en TableRow con layout_weight
                TableRow.LayoutParams.WRAP_CONTENT,
                1f // Peso para distribución equitativa en TableRow
            )
            textSize = 11f
            setBackgroundColor(Color.WHITE)
            setTextColor(Color.BLACK)
        }

        fila.addView(textView)
    }
}