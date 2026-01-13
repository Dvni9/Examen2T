package com.example.examen2t.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.examen2t.R
import com.example.examen2t.databinding.ActividadPrincipalBinding

import com.example.exament2.viewmodel.ProductoViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActividadPrincipalBinding
    private lateinit var viewModel: ProductoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActividadPrincipalBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[ProductoViewModel::class.java]

        configurarNavegacion()
    }

    private fun configurarNavegacion() {
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.contenedor_fragmentos) as NavHostFragment
        val navController = navHostFragment.navController

        binding.navegacionInferior.setupWithNavController(navController)
    }
}