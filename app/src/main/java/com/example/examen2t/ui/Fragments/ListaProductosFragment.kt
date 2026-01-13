package com.example.exament2.ui.fragmentos

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.examen2t.R
import com.example.examen2t.adapters.ProductoAdapter
import com.example.examen2t.databinding.FragmentoListaProductosBinding
import com.example.exament2.datos.Producto
import com.example.examen2t.ui.DetalleProductoActivity
import com.example.exament2.viewmodel.ProductoViewModel

class ListaProductosFragment : Fragment() {

    private var _binding: FragmentoListaProductosBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: ProductoViewModel
    private lateinit var adaptador: ProductoAdapter
    private lateinit var preferenciasCompartidas: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentoListaProductosBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity())[ProductoViewModel::class.java]
        preferenciasCompartidas = requireActivity().getSharedPreferences("preferencias_app", Context.MODE_PRIVATE)

        configurarRecyclerView()
        observarViewModel()
        cargarPreferenciaOrdenacion()
    }

    private fun configurarRecyclerView() {
        adaptador = ProductoAdapter(emptyList(),
            onItemClick = { producto ->
                val intent = Intent(requireContext(), DetalleProductoActivity::class.java)
                intent.putExtra("id_producto", producto.id)
                startActivity(intent)
            },
            onLongItemClick = { producto, vistaAncla ->
                mostrarMenuContexto(producto, vistaAncla)
            }
        )

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adaptador
    }

    private fun observarViewModel() {
        viewModel.productos.observe(viewLifecycleOwner) { productos ->
            adaptador.actualizarProductos(productos)
        }

        viewModel.cargarProductos()
        viewModel.marcarProductosCaducados()
    }

    private fun mostrarMenuContexto(producto: Producto, vistaAncla: View) {
        val popup = PopupMenu(requireContext(), vistaAncla)
        popup.menuInflater.inflate(R.menu.menu_contexto_producto, popup.menu)

        popup.setOnMenuItemClickListener { item ->
            when(item.itemId) {
                R.id.menu_reducir -> {
                    viewModel.reducirCantidad(producto.id)
                    true
                }
                R.id.menu_eliminar -> {
                    viewModel.eliminarProducto(producto)
                    true
                }
                R.id.menu_editar -> {
                    val intent = Intent(requireContext(), DetalleProductoActivity::class.java)
                    intent.putExtra("id_producto", producto.id)
                    intent.putExtra("es_edicion", true)
                    startActivity(intent)
                    true
                }
                R.id.menu_compartir -> {
                    compartirProducto(producto)
                    true
                }
                else -> false
            }
        }

        popup.show()
    }

    private fun compartirProducto(producto: Producto) {
        val formatoFecha = java.text.SimpleDateFormat("dd/MM/yyyy HH:mm", java.util.Locale.getDefault())
        val textoCompartir = "Producto: ${producto.nombre}\nCantidad: ${producto.cantidad}\nCaduca: ${formatoFecha.format(producto.fechaCaducidad)}"

        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_SUBJECT, "Información del Producto")
        intent.putExtra(Intent.EXTRA_TEXT, textoCompartir)

        startActivity(Intent.createChooser(intent, "Compartir producto"))
    }

    private fun cargarPreferenciaOrdenacion() {
        val tipoOrdenacion = preferenciasCompartidas.getString("tipo_ordenacion", "nombre") ?: "nombre"
        viewModel.ordenarProductos(tipoOrdenacion)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_ordenacion, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val tipoOrdenacion = when(item.itemId) {
            R.id.menu_ordenar_nombre -> "nombre"
            R.id.menu_ordenar_cantidad -> "cantidad"
            R.id.menu_ordenar_fecha -> "fecha"
            else -> "nombre"
        }

        preferenciasCompartidas.edit().putString("tipo_ordenacion", tipoOrdenacion).apply()
        viewModel.ordenarProductos(tipoOrdenacion)
        return true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}