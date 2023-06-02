package com.example.interactivebible.reading

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.interactivebible.R

lateinit var varClickListener: VerseAdapter.ItemClickListener

class VerseAdapter (dataSet1: VerseFragment, private val dataSet: ArrayList<Int>) : RecyclerView.Adapter<VerseAdapter.ViewHolder>() {

    // inflates the cell layout from xml when needed
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.recycler_c_v_2, viewGroup, false)

        return ViewHolder(view)
    }

    // binds the data to the TextView in each cell
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.textView.text = dataSet[position].toString()
    }

    // total number of cells
    override fun getItemCount() = dataSet.size

    // stores and recycles views as they are scrolled off screen
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view),
        View.OnClickListener {
        var textView: TextView

        override fun onClick(view: View) {
            if (varClickListener != null) varClickListener.onItemClick(view, adapterPosition)
        }

        init {
            textView = itemView.findViewById(R.id.recycle_textview_two)
            itemView.setOnClickListener(this)
        }
    }


    // convenience method for getting data at click position
    fun getItem(id: Int): Int {
        return dataSet[id]
    }

    // allows clicks events to be caught
    fun setClickListener(itemClickListener: VerseFragment) {
        if (itemClickListener != null) {
            varClickListener = itemClickListener
        }
    }

    // parent activity will implement this method to respond to click events
    interface ItemClickListener {
        fun onItemClick(view: View?, position: Int)
    }
//
//    // data is passed into the constructor

}