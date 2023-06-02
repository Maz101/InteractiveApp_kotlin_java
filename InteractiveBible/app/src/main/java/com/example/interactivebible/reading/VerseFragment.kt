package com.example.interactivebible.reading


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.interactivebible.databinding.FragmentVerseBinding
//import io.realm.query

//import io.realm.kotlin.where

//import io.realm.mongodb.AppConfiguration



class VerseFragment : Fragment(), VerseAdapter.ItemClickListener, ChapterAdapter.ItemClickListener, book_chapter {

    private var _binding: FragmentVerseBinding? = null
    private val binding get() = _binding!!
    val bundle = activity?.intent?.extras

    private val viewModel: ItemViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        _binding = FragmentVerseBinding.inflate(inflater, container, false)

        val verse_number = viewModel.selectedVerse()?.toInt()
        var data_ = ArrayList<Int>()

        if (viewModel.getVerseData() == null) {
            for (i in 1..verse_number!!) {
                data_.add(i)
            }
        }
        else{
            data_ = viewModel.getVerseData()!!
        }


        val verserecyclerView = _binding!!.verserecyclerView
        verserecyclerView.setHasFixedSize(true)
        verserecyclerView.layoutManager = GridLayoutManager(activity, 4)
        val verseadapter = VerseAdapter(this, data_)
        verseadapter.setClickListener(this)
        verserecyclerView.adapter = verseadapter
//        if (verse_number != null) {
//            verserecyclerView.scrollToPosition(verse_number)
//        }

        viewModel.selectverseAdapter(verseadapter)
        viewModel.selectVerseData(data_)

        return binding.root

    }



    override fun onItemClick(view: View?, index: Int) {
        var chapter_d = viewModel.getChapter()
        val intent = activity?.intent
       // lateinit var result : Any

        if (chapter_d == null){
            chapter_d = 1.toString()
        }
        else{
            intent?.putExtra("chapter",chapter_d.toString())
        }
        val position = index + 1
        viewModel.setVerseView(view)


        //get

        startActivity(Intent(
            activity,
            ReadingActivity::class.java),
        )

        viewModel.selectVerse(position.toString())


    }

    override fun getBook(): String {
        return viewModel.selectedItem()
    }

    override fun getChapter(): String {
        return viewModel.getChapter().toString()
    }


}


