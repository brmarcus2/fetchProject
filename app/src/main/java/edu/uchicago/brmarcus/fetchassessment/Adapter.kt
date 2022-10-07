package edu.uchicago.brmarcus.fetchassessment

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import edu.uchicago.brmarcus.fetchassessment.data.Item

class Adapter () : RecyclerView.Adapter<Adapter.ViewHolder>() {
    var allItems = mutableListOf<Item>()

    //set the items on the adaptor
    fun setItemList(items: List<Item>) {
        this.allItems = items.toMutableList()
        notifyDataSetChanged()
    }
    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_view_design, parent, false)

        return ViewHolder(view)
    }

    // a list item to a card
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = allItems[position]

        holder.id.text = item.id.toString()
        holder.listId.text = item.listId.toString()
        holder.itemName.text = item.name.toString()
    }

    //number of items in the list
    override fun getItemCount(): Int {
        return allItems.size
    }

    // binds the item attributes to the corresponding text view on the card
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val id: TextView = itemView.findViewById(R.id.id)
        val listId: TextView = itemView.findViewById(R.id.list_id)
        val itemName: TextView = itemView.findViewById(R.id.name)
    }
}