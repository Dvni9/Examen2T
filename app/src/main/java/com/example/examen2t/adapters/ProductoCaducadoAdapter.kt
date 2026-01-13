package com.example.examen2t.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.examen2t.R
import com.example.exament2.datos.Producto
import java.text.SimpleDateFormat
import java.util.Locale

class ProductoCaducadoAdapter(
    private var productos: List<Producto>,
    private val onItemClick: (Producto) -> Unit
) : RecyclerView.Adapter<ProductoCaducadoAdapter.ProductoCaducadoViewHolder>() {

    class ProductoCaducadoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textoNombre: TextView = itemView.findViewById(R.id.texto_nombre_producto_caducado)
        val textoCantidad: TextView = itemView.findViewById(R.id.texto_cantidad_producto_caducado)
        val textoFecha: TextView = itemView.findViewById(R.id.texto_fecha_producto_caducado)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductoCaducadoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_producto_caducado, parent, false)
        return ProductoCaducadoViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductoCaducadoViewHolder, position: Int) {
        val producto = productos[position]
        val formatoFecha = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())

        holder.textoNombre.text = producto.nombre
        holder.textoCantidad.text = "Cantidad: ${producto.cantidad}"
        holder.textoFecha.text = "Caducó: ${formatoFecha.format(producto.fechaCaducidad)}"

        holder.itemView.setOnClickListener {
            onItemClick(producto)
        }
    }

    override fun getItemCount(): Int = productos.size

    fun actualizarProductos(nuevosProductos: List<Producto>) {
        productos = nuevosProductos
        notifyDataSetChanged()
    }
}