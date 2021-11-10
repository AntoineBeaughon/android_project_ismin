package com.ismin.csproject

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class FountainAdapter (
    private val ftns: ArrayList<Fountain>, private val favshelf :ArrayList<String>,
    private val listener: AdapterView.OnItemClickListener
    ) : RecyclerView.Adapter<FountainAdapter.FountainViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FountainViewHolder {
            val row = LayoutInflater.from(parent.context).inflate(R.layout.row_ftn, parent, false)
            return FountainViewHolder(row)
        }

        override fun onBindViewHolder(holder: FountainViewHolder, position: Int) {
            val ids = this.ftns[position].id
            if(favshelf.contains(ids)){
                holder.buttonFav.setImageResource(R.drawable.ic_baseline_star_24)
            }
            else{
                holder.buttonFav.setImageResource(R.drawable.ic_baseline_star_border_24)
            }
            holder.txvName.text = ids
        }

        override fun getItemCount(): Int {
            return this.ftns.size
        }

        fun updateItem(fountainsToDisplay: List<Fountain>) {
            ftns.clear();
            ftns.addAll(fountainsToDisplay)
            notifyDataSetChanged();
        }

        inner class FountainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
            View.OnClickListener {

            var txvName = itemView.findViewById<TextView>(R.id.r_ftn_txv_id)
            val buttonFav = itemView.findViewById<ImageButton>(R.id.imageButtonFav)

            init {
                itemView.setOnClickListener(this)
                buttonFav.setOnClickListener(this)
            }

            override fun onClick(v: View?) {
                when (v) {
                    buttonFav -> {
                        if(favshelf.contains(txvName.text)){
                            buttonFav.setImageResource(R.drawable.ic_baseline_star_border_24)
                            favshelf.remove(txvName.text)
                            listener.favFromAdapter(adapterPosition)

                        }else{
                            buttonFav.setImageResource(R.drawable.ic_baseline_star_24)
                            favshelf.add(txvName.text as String)
                            listener.favFromAdapter(adapterPosition)
                        }
                    }
                    else -> {
                        val position: Int = adapterPosition
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position)
                        }
                    }
                }
            }
        }

        interface OnItemClickListener {
            fun onItemClick(position: Int)
            fun favFromAdapter(position : Int)
        }
}