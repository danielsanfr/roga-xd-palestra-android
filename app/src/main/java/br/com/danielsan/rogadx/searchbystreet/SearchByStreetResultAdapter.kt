package br.com.danielsan.rogadx.searchbystreet

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.json.JSONObject

class SearchByStreetResultAdapter(
    val listener: (JSONObject) -> Unit
) : ListAdapter<JSONObject, SearchByStreetResultAdapter.MyViewHolder>(SearchByStreetResultAdapter) {
    companion object : DiffUtil.ItemCallback<JSONObject>() {
        override fun areItemsTheSame(oldItem: JSONObject, newItem: JSONObject): Boolean = false

        override fun areContentsTheSame(oldItem: JSONObject, newItem: JSONObject): Boolean = false
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(android.R.layout.simple_list_item_2, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = getItem(position)
        holder.itemView.findViewById<TextView>(android.R.id.text1).text = item.optString("bairro")
        holder.itemView.findViewById<TextView>(android.R.id.text2).text = item.optString("logradouro")
        holder.itemView.setOnClickListener { listener(item) }
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {}
}
