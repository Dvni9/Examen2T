package com.example.exament2.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.exament2.datos.Producto
import com.example.exament2.datos.RepositorioProducto
import kotlinx.coroutines.launch
import java.util.Date

class ProductoViewModel(aplicacion: Application) : AndroidViewModel(aplicacion) {

    private val repositorio = RepositorioProducto(aplicacion)

    val productos = MutableLiveData<List<Producto>>()
    val productosCaducados = MutableLiveData<List<Producto>>()

    fun cargarProductos() {
        viewModelScope.launch {
            repositorio.obtenerTodosProductos { listaProductos ->
                productos.postValue(listaProductos)
            }
        }
    }

    fun cargarProductosCaducados() {
        viewModelScope.launch {
            repositorio.obtenerProductosCaducados { listaProductos ->
                productosCaducados.postValue(listaProductos)
            }
        }
    }

    fun insertarProducto(producto: Producto) {
        viewModelScope.launch {
            repositorio.insertarProducto(producto) {
                cargarProductos()
            }
        }
    }

    fun actualizarProducto(producto: Producto) {
        viewModelScope.launch {
            repositorio.actualizarProducto(producto)
            cargarProductos()
            cargarProductosCaducados()
        }
    }

    fun eliminarProducto(producto: Producto) {
        viewModelScope.launch {
            repositorio.eliminarProducto(producto)
            cargarProductos()
            cargarProductosCaducados()
        }
    }

    fun obtenerProductoPorId(idProducto: Int, callback: (Producto?) -> Unit) {
        viewModelScope.launch {
            repositorio.obtenerProductoPorId(idProducto) { producto ->
                callback(producto)
            }
        }
    }

    fun reducirCantidad(idProducto: Int) {
        viewModelScope.launch {
            repositorio.reducirCantidad(idProducto)
            cargarProductos()
        }
    }

    fun marcarProductosCaducados() {
        viewModelScope.launch {
            repositorio.marcarProductosCaducados()
            cargarProductos()
            cargarProductosCaducados()
        }
    }

    fun ordenarProductos(tipoOrdenacion: String) {
        viewModelScope.launch {
            repositorio.ordenarProductos(tipoOrdenacion) { productosOrdenados ->
                productos.postValue(productosOrdenados)
            }
        }
    }

    fun obtenerProductosParaNotificacion(callback: (List<Producto>) -> Unit) {
        viewModelScope.launch {
            repositorio.obtenerProductosParaNotificacion(callback)
        }
    }

    fun marcarNotificacionProgramada(idProducto: Int) {
        viewModelScope.launch {
            repositorio.marcarNotificacionProgramada(idProducto)
        }
    }
}