package com.example.examen2t.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.examen2t.databinding.ActividadDetalleProductoBinding
import com.example.exament2.viewmodel.ProductoViewModel

class DetalleProductoActivity : AppCompatActivity() {

    private lateinit var binding: ActividadDetalleProductoBinding
    private lateinit var viewModel: ProductoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActividadDetalleProductoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[ProductoViewModel::class.java]

        configurarListeners()
        cargarDatos()
    }

    private fun configurarListeners() {
        binding.botonVolver.setOnClickListener {
            finish()
        }

        binding.botonCompartir.setOnClickListener {
            compartirProducto()
        }

        binding.botonEliminar.setOnClickListener {
            eliminarProducto()
        }

        binding.botonGuardar.setOnClickListener {
            guardarProducto()
        }
    }

    private fun cargarDatos() {
        // Aquí cargarías los datos del producto
        val productoId = intent.getIntExtra("id_producto", -1)
        if (productoId != -1) {
            // Cargar datos del producto
            binding.campoNombre.setText("Producto de ejemplo")
            binding.campoCantidad.setText("5")
            binding.campoFechaCaducidad.setText("01/01/2026 12:00")
        }
    }

    private fun compartirProducto() {
        val nombre = binding.campoNombre.text.toString()
        val cantidad = binding.campoCantidad.text.toString()
        val fecha = binding.campoFechaCaducidad.text.toString()

        val textoCompartir = "Producto: $nombre\nCantidad: $cantidad\nCaduca: $fecha"

        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_SUBJECT, "Información del Producto")
        intent.putExtra(Intent.EXTRA_TEXT, textoCompartir)

        startActivity(Intent.createChooser(intent, "Compartir producto"))
    }

    private fun eliminarProducto() {
        Toast.makeText(this, "Producto eliminado", Toast.LENGTH_SHORT).show()
        finish()
    }

    private fun guardarProducto() {
        Toast.makeText(this, "Producto guardado", Toast.LENGTH_SHORT).show()
        finish()
    }
}