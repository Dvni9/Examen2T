package com.example.exament2.ui.fragmentos

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.examen2t.databinding.FragmentoAnadirProductoBinding
import com.example.exament2.datos.Producto
import com.example.exament2.viewmodel.ProductoViewModel
import java.text.SimpleDateFormat
import java.util.*

class AnadirProductoFragment : Fragment() {

    private var _binding: FragmentoAnadirProductoBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: ProductoViewModel
    private val calendario = Calendar.getInstance()
    private val formatoFecha = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentoAnadirProductoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = androidx.lifecycle.ViewModelProvider(requireActivity())[ProductoViewModel::class.java]

        configurarSelectorFecha()
        actualizarDisplayFecha()

        binding.botonGuardar.setOnClickListener {
            guardarProducto()
        }

        binding.botonLimpiar.setOnClickListener {
            limpiarCampos()
        }
    }

    private fun configurarSelectorFecha() {
        binding.botonSeleccionarFecha.setOnClickListener {
            mostrarSelectorFecha()
        }
    }

    private fun mostrarSelectorFecha() {
        val selectorFecha = DatePickerDialog(
            requireContext(),
            { _, año, mes, dia ->
                calendario.set(Calendar.YEAR, año)
                calendario.set(Calendar.MONTH, mes)
                calendario.set(Calendar.DAY_OF_MONTH, dia)
                mostrarSelectorHora()
            },
            calendario.get(Calendar.YEAR),
            calendario.get(Calendar.MONTH),
            calendario.get(Calendar.DAY_OF_MONTH)
        )
        selectorFecha.show()
    }

    private fun mostrarSelectorHora() {
        val selectorHora = TimePickerDialog(
            requireContext(),
            { _, hora, minuto ->
                calendario.set(Calendar.HOUR_OF_DAY, hora)
                calendario.set(Calendar.MINUTE, minuto)
                actualizarDisplayFecha()
            },
            calendario.get(Calendar.HOUR_OF_DAY),
            calendario.get(Calendar.MINUTE),
            true
        )
        selectorHora.show()
    }

    private fun actualizarDisplayFecha() {
        binding.textoFechaSeleccionada.text = formatoFecha.format(calendario.time)
    }

    private fun guardarProducto() {
        val nombre = binding.campoNombre.text.toString().trim()
        val cantidadStr = binding.campoCantidad.text.toString().trim()

        if (nombre.isEmpty() || cantidadStr.isEmpty()) {
            Toast.makeText(requireContext(), "Complete todos los campos", Toast.LENGTH_SHORT).show()
            return
        }

        val cantidad = cantidadStr.toIntOrNull() ?: 0
        if (cantidad <= 0) {
            Toast.makeText(requireContext(), "Cantidad inválida", Toast.LENGTH_SHORT).show()
            return
        }

        val producto = Producto(
            nombre = nombre,
            cantidad = cantidad,
            fechaCaducidad = calendario.time
        )

        viewModel.insertarProducto(producto)
        limpiarCampos()
        Toast.makeText(requireContext(), "Producto añadido", Toast.LENGTH_SHORT).show()
    }

    private fun limpiarCampos() {
        binding.campoNombre.text.clear()
        binding.campoCantidad.text.clear()
        calendario.time = Date()
        actualizarDisplayFecha()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}