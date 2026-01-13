package com.example.exament2.datos

import androidx.room.*
import java.util.Date

@Dao
interface ProductoDao {

    @Insert
    suspend fun insertar(producto: Producto): Long

    @Update
    suspend fun actualizar(producto: Producto)

    @Delete
    suspend fun eliminar(producto: Producto)

    @Query("SELECT * FROM productos WHERE estaCaducado = 0 ORDER BY nombre ASC")
    suspend fun obtenerTodosProductos(): List<Producto>

    @Query("SELECT * FROM productos WHERE estaCaducado = 1 ORDER BY fechaCaducidad ASC")
    suspend fun obtenerProductosCaducados(): List<Producto>

    @Query("SELECT * FROM productos WHERE id = :idProducto")
    suspend fun obtenerProductoPorId(idProducto: Int): Producto?

    @Query("SELECT * FROM productos WHERE fechaCaducidad <= :fecha AND estaCaducado = 0")
    suspend fun obtenerProductosPorCaducar(fecha: Date): List<Producto>

    @Query("UPDATE productos SET cantidad = cantidad - 1 WHERE id = :idProducto AND cantidad > 0")
    suspend fun reducirCantidad(idProducto: Int)

    @Query("UPDATE productos SET estaCaducado = 1 WHERE fechaCaducidad <= :fecha")
    suspend fun marcarComoCaducado(fecha: Date)

    @Query("SELECT * FROM productos ORDER BY nombre ASC")
    suspend fun obtenerProductosOrdenadosPorNombre(): List<Producto>

    @Query("SELECT * FROM productos ORDER BY cantidad ASC")
    suspend fun obtenerProductosOrdenadosPorCantidad(): List<Producto>

    @Query("SELECT * FROM productos ORDER BY fechaCaducidad ASC")
    suspend fun obtenerProductosOrdenadosPorFecha(): List<Producto>

    @Query("UPDATE productos SET notificacionProgramada = 1 WHERE id = :idProducto")
    suspend fun marcarNotificacionProgramada(idProducto: Int)

    @Query("SELECT * FROM productos WHERE notificacionProgramada = 0 AND estaCaducado = 0")
    suspend fun obtenerProductosParaNotificacion(): List<Producto>
}