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


class ProductoAdapter(
    private var productos: List<Producto>,
    private val onItemClick: (Producto) -> Unit,
    private val onLongItemClick: (Producto, View) -> Unit
) : RecyclerView.Adapter<ProductoAdapter.ProductoViewHolder>() {

    class ProductoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textoNombre: TextView = itemView.findViewById(R.id.texto_nombre_producto)
        val textoCantidad: TextView = itemView.findViewById(R.id.texto_cantidad_producto)
        val textoFecha: TextView = itemView.findViewById(R.id.texto_fecha_producto)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_producto, parent, false)
        return ProductoViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductoViewHolder, position: Int) {
        val producto = productos[position]
        val formatoFecha = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())

        holder.textoNombre.text = producto.nombre
        holder.textoCantidad.text = "Cantidad: ${producto.cantidad}"
        holder.textoFecha.text = "Caduca: ${formatoFecha.format(producto.fechaCaducidad)}"

        holder.itemView.setOnClickListener {
            onItemClick(producto)
        }

        holder.itemView.setOnLongClickListener {
            onLongItemClick(producto, holder.itemView)
            true
        }
    }

    override fun getItemCount(): Int = productos.size

    fun actualizarProductos(nuevosProductos: List<Producto>) {
        productos = nuevosProductos
        notifyDataSetChanged()
    }
}