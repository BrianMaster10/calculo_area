package com.example.clculo_area

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    private lateinit var polygonButtons: List<ImageButton>
    private lateinit var numberButtons: List<Button>
    private lateinit var calculateButton: Button
    private lateinit var clearButton: Button
    private lateinit var baseEditText: TextView
    private lateinit var heightEditText: TextView
    private lateinit var areaEditText: TextView
    private lateinit var selectedPolygonTextView: TextView
    private var isTextViewCleared = true


    // Variable para almacenar el polígono seleccionado
    private var selectedPolygon: String = ""

    // Variable para almacenar el último TextView seleccionado
    private var lastSelectedTextView: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inicializa los botones de los polígonos
        polygonButtons = listOf(
            findViewById(R.id.buttonCua),
            findViewById(R.id.buttonRect),
            findViewById(R.id.buttonTri),
            findViewById(R.id.buttonCir),
            findViewById(R.id.buttonPent),
            findViewById(R.id.buttonHex)
        )

        // Inicializa los botones de los números
        numberButtons = listOf(
            findViewById(R.id.button9),
            findViewById(R.id.button8),
            findViewById(R.id.button7),
            findViewById(R.id.button6),
            findViewById(R.id.button5),
            findViewById(R.id.button4),
            findViewById(R.id.button3),
            findViewById(R.id.button2),
            findViewById(R.id.button1),
            findViewById(R.id.button_number_0)
        )

        // Inicializa los demás elementos de la interfaz
        calculateButton = findViewById(R.id.button_calculate)
        clearButton = findViewById(R.id.button_clear)
        baseEditText = findViewById(R.id.edit_text_base)
        heightEditText = findViewById(R.id.edit_text_height)
        areaEditText = findViewById(R.id.edit_text_area)
        selectedPolygonTextView = findViewById(R.id.selected_polygon)

        // Configura los listeners de los botones
        for (button in polygonButtons) {
            button.setOnClickListener { onPolygonButtonClicked(it) }
        }
        for (button in numberButtons) {
            button.setOnClickListener { onNumberButtonClicked(it) }
        }
        calculateButton.setOnClickListener { onCalculateButtonClicked() }
        clearButton.setOnClickListener { onClearButtonClicked() }

        // Configura los listeners de los TextView
        baseEditText.setOnClickListener { lastSelectedTextView = it as TextView }
        heightEditText.setOnClickListener { lastSelectedTextView = it as TextView }
        areaEditText.setOnClickListener { lastSelectedTextView = it as TextView }
    }

    private fun onPolygonButtonClicked(view: View) {
        // Almacena el polígono seleccionado
        selectedPolygon = (view as ImageButton).contentDescription.toString()
        selectedPolygonTextView.text = "Polígono seleccionado: $selectedPolygon"

        // Restablece el texto de los TextViews a su estado inicial
        baseEditText.text = ""
        heightEditText.text = ""

        // Cambia los TextViews dependiendo del polígono seleccionado
        when (selectedPolygon) {
            "Círculo" -> {
                baseEditText.text = "Radio"
            }
            "Cuadrado" -> {
                baseEditText.text = "Lado"
            }
            else -> {
                baseEditText.text = "Base"
                heightEditText.text = "Altura"
            }
        }
    }




    private fun onNumberButtonClicked(view: View) {
        // Añade el número al último TextView seleccionado
        val number = (view as Button).text.toString()
        lastSelectedTextView?.let {
            if (isTextViewCleared && (it.text == "Base" || it.text == "Altura" || it.text == "Área del..." || it.text == "Radio" || it.text == "Lado")) {
                it.text = ""
            }
            it.append(number)
        }
    }


    private fun onCalculateButtonClicked() {
        // Realiza el cálculo del área
        val base = baseEditText.text.toString().toDoubleOrNull()
        val height = heightEditText.text.toString().toDoubleOrNull()
        val area = when (selectedPolygon) {
            "Cuadrado" -> base?.let { it * it }
            "Rectángulo" -> base?.let { height?.let { it1 -> it * it1 } }
            "Triángulo" -> base?.let { height?.let { it1 -> 0.5 * it * it1 } }
            "Círculo" -> base?.let { Math.PI * it * it }
            "Pentágono" -> base?.let { height?.let { it1 -> 0.5 * it * it1 } } // Asume que es un pentágono regular
            "Hexágono" -> base?.let { height?.let { it1 -> 0.5 * it * it1 } } // Asume que es un hexágono regular
            else -> null
        }
        areaEditText.setText(area?.toString() ?: "Error")
        isTextViewCleared = true
    }

    private fun onClearButtonClicked() {
        // Borra el último carácter del TextView seleccionado
        lastSelectedTextView?.let {
            if (it.text.isNotEmpty()) {
                it.text = it.text.dropLast(1)
            }
        }
    }
}
