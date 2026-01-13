package com.example.exament2.datos

import android.app.Application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Date

class RepositorioProducto(aplicacion: Application) {

    private val productoDao = BaseDatosApp.obtenerBaseDatos(aplicacion).productoDao()
    private val ioScope = CoroutineScope(Dispatchers.IO)

    fun insertarProducto(producto: Producto, alCompletar: (Long) -> Unit = {}) {
        ioScope.launch {
            val id = productoDao.insertar(producto)
            alCompletar(id)
        }
    }

    fun actualizarProducto(producto: Producto) {
        ioScope.launch {
            productoDao.actualizar(producto)
        }
    }

    fun eliminarProducto(producto: Producto) {
        ioScope.launch {
            productoDao.eliminar(producto)
        }
    }

    fun obtenerTodosProductos(callback: (List<Producto>) -> Unit) {
        ioScope.launch {
            val productos = productoDao.obtenerTodosProductos()
            callback(productos)
        }
    }

    fun obtenerProductosCaducados(callback: (List<Producto>) -> Unit) {
        ioScope.launch {
            val productos = productoDao.obtenerProductosCaducados()
            callback(productos)
        }
    }

    fun obtenerProductoPorId(idProducto: Int, callback: (Producto?) -> Unit) {
        ioScope.launch {
            val producto = productoDao.obtenerProductoPorId(idProducto)
            callback(producto)
        }
    }

    fun reducirCantidad(idProducto: Int) {
        ioScope.launch {
            productoDao.reducirCantidad(idProducto)
        }
    }

    fun marcarProductosCaducados() {
        ioScope.launch {
            productoDao.marcarComoCaducado(Date())
        }
    }

    fun ordenarProductos(tipoOrdenacion: String, callback: (List<Producto>) -> Unit) {
        ioScope.launch {
            val productos = when(tipoOrdenacion) {
                "nombre" -> productoDao.obtenerProductosOrdenadosPorNombre()
                "cantidad" -> productoDao.obtenerProductosOrdenadosPorCantidad()
                "fecha" -> productoDao.obtenerProductosOrdenadosPorFecha()
                else -> productoDao.obtenerTodosProductos()
            }
            callback(productos)
        }
    }

    fun obtenerProductosParaNotificacion(callback: (List<Producto>) -> Unit) {
        ioScope.launch {
            val productos = productoDao.obtenerProductosParaNotificacion()
            callback(productos)
        }
    }

    fun marcarNotificacionProgramada(idProducto: Int) {
        ioScope.launch {
            productoDao.marcarNotificacionProgramada(idProducto)
        }
    }
}