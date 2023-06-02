package com.example.interactivebible.reading

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.interactivebible.R


var pageClickListener: PageAdapter.ItemClickListener? = null
class PageAdapter(private val dataSet: ArrayList<CharSequence>) : RecyclerView.Adapter<PageAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        //        View.OnClickListener {
        val textView: TextView
        init {
            textView = view.findViewById(R.id.recycle_page_textview)
        }
    }

    // inflates the cell layout from xml when needed
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.recycle_page, viewGroup, false)

        return ViewHolder(view)
    }


    // binds the data to the TextView in each cell
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val items = dataSet[position]
        viewHolder.textView.text = items
    }

    // total number of cells
    override fun getItemCount() : Int {
        return dataSet.size
    }


    // convenience method for getting data at click position


    interface ItemClickListener {
        fun onItemClick(view: View?, position: Int)
    }



}