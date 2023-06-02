package com.example.interactivebible.reading

import android.graphics.Color
import android.os.Bundle
import android.util.TypedValue
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.*
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.interactivebible.databinding.FragmentBookNamesBinding
import com.example.interactivebible.data.Book
import androidx.recyclerview.widget.RecyclerView
import com.example.interactivebible.R


val booklist = listOf("Genesis", "Exodus", "Leviticus", "Numbers", "Deuteronomy", "Joshua", "Judges", "Ruth", "1 Samuel", "2 Samuel",
    "1 Kings", "2 Kings", "1 Chronicles", "2 Chronicles", "Ezra", "Nehemiah",
    "Esther", "Job", "Psalms", "Proverbs", "Ecclesiastes", "Song of Solomon", "Isaiah", "Jeremiah", "Lamentations",
    "Ezekiel", "Daniel", "Hosea", "Joel", "Amos", "Obadiah", "Jonah", "Micah", "Nahum", "Habakkuk", "Zephaniah",
    "Haggai", "Zechariah", "Malachi", "Matthew", "Mark", "Luke", "John", "Acts", "Romans", "1 Corinthians",
    "2 Corinthians", "Galatians", "Ephesians", "Philippians", "Colossians", "1 Thessalonians", "2 Thessalonians", "1 Timothy",
    "2 Timothy", "Titus", "Philemon", "Hebrews", "James", "1 Peter", "2 Peter", "1 John", "2 John", "3 John", "Jude",
    "Revelation")


val bookChapters_two = mapOf("Genesis" to 50, "Exodus" to 40, "Leviticus" to 27, "Numbers" to 36, "Deuteronomy" to 34, "Joshua" to 24, "Judges" to 21,
    "Ruth" to 4, "1 Samuel" to 31, "2 Samuel" to 24, "1 Kings" to 22, "2 Kings" to 25, "1 Chronicles" to 29, "2 Chronicles" to 36, "Ezra" to 10, "Nehemiah" to 13,
    "Esther" to 10, "Job" to 42, "Psalms" to 150, "Proverbs" to 31, "Ecclesiastes" to 12, "Song of Solomon" to 8, "Isaiah" to 66, "Jeremiah" to 52, "Lamentations" to 5,
    "Ezekiel" to 48, "Daniel" to 12, "Hosea" to 14, "Joel" to 3, "Amos" to 9, "Obadiah" to 1, "Jonah" to 4, "Micah" to 7, "Nahum" to 3, "Habakkuk" to 3, "Zephaniah" to 3,
    "Haggai" to 2, "Zechariah" to 14, "Malachi" to 4, "Matthew" to 28, "Mark" to 16, "Luke" to 24, "John" to 21, "Acts" to 28, "Romans" to 16, "1 Corinthians" to 16,
    "2 Corinthians" to 13, "Galatians" to 6, "Ephesians" to 6, "Philippians" to 4, "Colossians" to 4, "1 Thessalonians" to 5, "2 Thessalonians" to 3, "1 Timothy" to 6,
    "2 Timothy" to 4, "Titus" to 3, "Philemon" to 1, "Hebrews" to 13, "James" to 5, "1 Peter" to 5, "2 Peter" to 3, "1 John" to 5, "2 John" to 1, "3 John" to 1, "Jude" to 1,
    "Revelation" to 22)


val id_map = hashMapOf<String, Int>()


class BookNamesFragment : Fragment(), BookAdapter.ItemClickListener {


    private var _binding: FragmentBookNamesBinding? = null
    private val binding get() = _binding!!
    lateinit var book_name : String
    private val ARG_OBJECT = "object"
    private val bookModel : Book by activityViewModels()

    private val viewModel: ItemViewModel by activityViewModels()

 //   private lateinit var data : sendbookchapter

    val SHARED_PREFS : String = "sharedPrefs"
    var bookid = null
    var counter = 0
    var chapter_data : ArrayList<Int>? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBookNamesBinding.inflate(inflater, container, false)

        val book_name = viewModel.getBookButtontText()
        viewModel.selectItem(book_name.toString())
        
        val recyclerBookView : RecyclerView = _binding!!.recyclerBookView
        recyclerBookView.layoutManager = LinearLayoutManager(activity)
        val bookadapter = BookAdapter(this, booklist)
        bookadapter.setClickListener(this)
        val book_index = booklist.indexOf(book_name)
        bookadapter.position_ = book_index
        recyclerBookView.adapter = bookadapter
        recyclerBookView.scrollToPosition(book_index.toInt())
        

       // val o_testament = _binding?.oldTestament
        //val viewGroup: ViewGroup = binding.oldTestament
    //    val first_layout = _binding?.firstLayout
      //  o_testament?.orientation = LinearLayout.VERTICAL


//        for (i in ot_booklist.indices){
//            val text = ot_booklist[i]
//
//            val button : Button = Button(activity)
//
//            button.text = text
//            button.setTextColor(Color.parseColor("#000000"))
//            button.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13F)
//            button.isAllCaps = true
//           // button.setGravity(Gravity.CENTER_HORIZONTAL)
//            button.textAlignment = View.TEXT_ALIGNMENT_VIEW_START
//            button.id = i+1
//
//            id_map.put(text,i+1)
//
//            if (text == book_name){
//                val typeface = ResourcesCompat.getFont(requireContext(), R.font.roboto_bold)
//                button.typeface = typeface
//            }
//            else {
//                val typeface = ResourcesCompat.getFont(requireContext(), R.font.roboto_regular)
//                button.typeface = typeface
//            }
//
//            button.setBackgroundColor(Color.parseColor("#FFFFFF"))
//
//            button.setOnClickListener {
//
//                onClick(text.toString(),it.id)
//
//            }
//
//            o_testament?.addView(button)

 //       }
        return binding.root
    }


    override fun onItemClick(view: View?, id_: Int) {
        book_name = booklist[id_]
        viewModel.selectItem(book_name)

        val typeface = ResourcesCompat.getFont(requireContext(), R.font.roboto_bold)
        val new_button : Button? = view?.findViewById(id_)
        new_button?.typeface = typeface

        updateData(book_name)

        val viewpager: ViewPager2? = activity?.findViewById(R.id.vpPager)
        viewpager?.currentItem = 1

    }

    fun updateData(book: String){

        val typeface = ResourcesCompat.getFont(requireContext(), R.font.roboto_regular)
        val book_name = viewModel.getBookButtontText()

        val intent = activity?.intent
        intent?.putExtra("book_text",book)
        val button : Button? = id_map.get(book_name)?.let { view?.findViewById(it) }
        button?.typeface = typeface
        val adapter: RecyclerView.Adapter<ChapterAdapter.ViewHolder>?
        val book_data : String

        if (bookwithnumbers.containsKey(book)) {
            book_data = bookwithnumbers.get(book).toString()
        }

        else if (book == "Song of Solomon".toString()){
                book_data = "Song_of_Solomon"
        }

        else{
                book_data = book
        }


        adapter = viewModel.getChapterAdapter()
        chapter_data = viewModel.getChapterData()


        if (book_name != book && adapter != null && chapter_data != null){
//stop
            if (bookChapters_two.get(book_name)!! > bookChapters_two.get(book)!!) {
                val endIndex: Int = bookChapters_two.get(book_name)!!
                val startIndex : Int = bookChapters_two.get(book)!!
                chapter_data!!.subList(startIndex,endIndex).clear()

                adapter.notifyItemRangeRemoved(startIndex,endIndex)
                val view_Holder = adapter.getItemViewType(0)
                val typeface = ResourcesCompat.getFont(context!!, R.font.inter_bold)
              //  view_Holder.

             //   adapter.

                viewModel.selectChapterAdapter(adapter)
                viewModel.selectChapterData(chapter_data!!)
                }

            else {

                val startIndex: Int = bookChapters_two.get(book_name)!!
                val endIndex: Int = bookChapters_two.get(book)!!
                val item: ArrayList<Int> = ArrayList()

                for (i in startIndex+1..endIndex) {
                    item.add(i)
                }
//stop
                chapter_data!!.addAll(startIndex, item)
                adapter.notifyItemRangeInserted(startIndex, item.size)

                viewModel.selectChapterAdapter(adapter)
                viewModel.selectChapterData(chapter_data!!)

            }

            val c_adapter = viewModel.getverseAdapter()
            val verse_data = viewModel.getVerseData()

            if (c_adapter != null && verse_data != null) {

                val book_chapter_verse_bf = bookModel.selectChapterData(book_data)
                val verse_bf = book_chapter_verse_bf?.get("1")
//stop
                val old_verse_bf = viewModel.selectedVerse()?.toInt()

                if (old_verse_bf?.toInt()!! > verse_bf!!) {
                    val end_index_v : Int = old_verse_bf
                    val start_index_v : Int = verse_bf
                    verse_data.subList(start_index_v,end_index_v).clear()
                    c_adapter.notifyItemRangeRemoved(start_index_v,end_index_v)
                    viewModel.selectverseAdapter(c_adapter)
                    viewModel.selectVerseData(verse_data)
                    viewModel.selectVerse(verse_bf.toString())

                }

                else {
                    if (old_verse_bf.toInt() < verse_bf) {
                        val start_index_v : Int = old_verse_bf
                        val end_index_v : Int = verse_bf
                        val item : ArrayList<Int> = ArrayList()

                        for (i in start_index_v+1..end_index_v){
                            item.add(i)
                        }

                        verse_data.addAll(start_index_v,item)
                        c_adapter.notifyItemRangeInserted(start_index_v,item.size)

                        viewModel.selectverseAdapter(c_adapter)
                        viewModel.selectVerseData(verse_data)
                        viewModel.selectVerse(verse_bf.toString())

                    }
                }
            }
        }

        viewModel.setBookButtonText(book)
        counter +=1

    }



}
