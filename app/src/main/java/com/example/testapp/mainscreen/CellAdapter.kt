package com.example.testapp.mainscreen

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.testapp.R
import com.example.testapp.database.Cell


class CellAdapter(
) :
    ListAdapter<Cell, CellAdapter.CellViewHolder>(CellDiffCallback()) {

    inner class CellViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val img: ImageView = itemView.findViewById(R.id.img)
        val emoji: TextView = itemView.findViewById(R.id.emoji)
        val state: TextView = itemView.findViewById(R.id.state)
        val description: TextView = itemView.findViewById(R.id.description)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CellViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.cell_item, parent, false)
        return CellViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CellViewHolder, position: Int) {
        getItem(position).background?.let { holder.img.setBackgroundResource(it) }
        holder.emoji.text = getItem(position).emoji
        holder.description.text = getItem(position)?.description
        holder.state.text = getItem(position)?.stateCell
    }


    class CellDiffCallback : DiffUtil.ItemCallback<Cell>() {
        override fun areItemsTheSame(
            oldItem: Cell,
            newItem: Cell
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: Cell,
            newItem: Cell
        ): Boolean {
            return oldItem == newItem
        }
    }
}