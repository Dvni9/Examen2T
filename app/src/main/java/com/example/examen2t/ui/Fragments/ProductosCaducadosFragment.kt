package com.example.exament2.ui.fragmentos

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.examen2t.adapters.ProductoCaducadoAdapter
import com.example.examen2t.databinding.FragmentoProductosCaducadosBinding
import com.example.exament2.ui.DetalleProductoActivity
import com.example.exament2.viewmodel.ProductoViewModel

class ProductosCaducadosFragment : Fragment() {

    private var _binding: FragmentoProductosCaducadosBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: ProductoViewModel
    private lateinit var adaptador: ProductoCaducadoAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentoProductosCaducadosBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity())[ProductoViewModel::class.java]

        configurarRecyclerView()
        observarViewModel()
    }

    private fun configurarRecyclerView() {


        binding.recyclerViewCaducados.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewCaducados.adapter = adaptador
    }

    private fun observarViewModel() {
        viewModel.productosCaducados.observe(viewLifecycleOwner) { productos ->
            adaptador.actualizarProductos(productos)
        }

        viewModel.cargarProductosCaducados()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}