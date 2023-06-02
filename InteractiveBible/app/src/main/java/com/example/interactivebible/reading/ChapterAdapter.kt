package com.example.interactivebible.reading

import android.content.Context
import android.graphics.Typeface
import android.os.Build
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.RecyclerView
import com.example.interactivebible.R
import java.lang.reflect.Type

//import sun.awt.AppContext.getAppContext





lateinit var mClickListener: ChapterAdapter.ItemClickListener
lateinit var chap_v : String
lateinit var old_textView: TextView

//private val viewModel: ItemViewModel by activityViewModels()

class ChapterAdapter(dataSet1: ChapterFragment, private val dataSet_: ArrayList<Int>) : RecyclerView.Adapter<ChapterAdapter.ViewHolder>() {
    var selectedFont = 0
    var position_ : Int? = null

    // inflates the cell layout from xml when needed
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.recycler_c_v, viewGroup, false)
        return ViewHolder(view)
    }

    // binds the data to the TextView in each cell
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.textView.text = dataSet_[position].toString()
        val item = dataSet_[position]

        if (position_ == position +1){
            viewHolder.changeFont(1)
        }

        else {
            viewHolder.changeFont(0)
        }

    }

    // total number of cells
    override fun getItemCount() = dataSet_.size

    // stores and recycles views as they are scrolled off screen
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view),
        View.OnClickListener {
        var textView: TextView

        override fun onClick(view: View) {
            if (mClickListener != null){
                mClickListener.onItemClick(view, adapterPosition)
                CustomFontHelper.setCustomFont(old_textView,0,itemView.context)
                changeFont(1)
            }
        }

        init {
            textView = itemView.findViewById(R.id.recycle_textview)
            itemView.setOnClickListener(this)
        }

        fun changeFont(font: Int) {
            CustomFontHelper.setCustomFont(textView, font, itemView.context)
        }

    }

    fun insertChapter(chap:String){
        chap_v = chap
    }

    // convenience method for getting data at click position
    fun getItem(id: Int): Int {
        return dataSet_[id]
    }

    // allows clicks events to be caught
    fun setClickListener(itemClickListener: ChapterFragment) {
        if (itemClickListener != null) {
            mClickListener = itemClickListener
        }
    }

    // parent activity will implement this method to respond to click events
    interface ItemClickListener {
        fun onItemClick(view: View?, position: Int)
    }

}

class ButtonPlus : AppCompatButton {
    constructor(context: Context?) : super(context!!)
    constructor(context: Context?, attrs: AttributeSet?) : super(context!!, attrs) {
        if (context != null) {
            CustomFontHelper.setCustomFont(this,0, context)
        }
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyle: Int) : super(context!!,
        attrs,
        defStyle) {
        CustomFontHelper.setCustomFont(this, 0, context)
    }

}

object CustomFontHelper {

    fun setCustomFont(textview: TextView, font: Int?, context: Context?) {
        if (font == 1) {
            val typeface = ResourcesCompat.getFont(context!!, R.font.inter_bold)
            textview.typeface = typeface
            old_textView = textview
        }

        else{
            val typeface = ResourcesCompat.getFont(context!!, R.font.inter_regular)
            textview.typeface = typeface
        }

    }
}
