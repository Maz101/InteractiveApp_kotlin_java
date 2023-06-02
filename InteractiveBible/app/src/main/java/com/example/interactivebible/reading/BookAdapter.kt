package com.example.interactivebible.reading

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.interactivebible.R


lateinit var bookClickListener: BookAdapter.ItemClickListener
lateinit var book_v : String
lateinit var book_textView: TextView

class BookAdapter(dataArray: BookNamesFragment, private val data: List<CharSequence>) : RecyclerView.Adapter<BookAdapter.ViewHolder>() {
    var selectedFont = 0
    var position_ : Int? = null
    // inflates the cell layout from xml when needed
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.recycler_book, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.textView.text = data[position].toString()
     //   val item = data[position]

        if (position_ == position){
            viewHolder.changeFont(1)
        }
        else {
            viewHolder.changeFont(0)
        }

    }

    override fun getItemCount() = data.size

    fun setClickListener(itemClickListener : BookNamesFragment) {
        if (itemClickListener != null){
            bookClickListener = itemClickListener
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view),
        View.OnClickListener {
        var textView: TextView

        override fun onClick(view: View) {
            if (bookClickListener != null){
                bookClickListener.onItemClick(view, adapterPosition)
                BookCustomFontHelper.setCustomFont(book_textView,0,itemView.context)
                changeFont(1)
            }
        }

        init {
            textView = itemView.findViewById(R.id.recycle_book)
            itemView.setOnClickListener(this)
        }

        fun changeFont(font: Int) {
            BookCustomFontHelper.setCustomFont(textView, font, itemView.context)
        }

    }

    //fun getItem(id: Int): String {
      //  return data[id]
    //}

    interface ItemClickListener {
        fun onItemClick(view: View?, position: Int)
    }



}

object BookCustomFontHelper {

    fun setCustomFont(textview: TextView, font: Int?, context: Context?) {
        if (font == 1) {
            val typeface = ResourcesCompat.getFont(context!!, R.font.inter_bold)
            textview.typeface = typeface
            book_textView = textview
        }

        else{
            val typeface = ResourcesCompat.getFont(context!!, R.font.inter_regular)
            textview.typeface = typeface
        }

    }
}
