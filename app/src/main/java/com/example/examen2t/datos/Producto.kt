package com.example.exament2.datos

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "productos")
data class Producto(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nombre: String,
    val cantidad: Int,
    val fechaCaducidad: Date,
    val estaCaducado: Boolean = false,
    val notificacionProgramada: Boolean = false
)