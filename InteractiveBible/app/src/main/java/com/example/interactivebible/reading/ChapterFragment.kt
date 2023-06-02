package com.example.interactivebible.reading

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.interactivebible.R
import com.example.interactivebible.databinding.FragmentChapterBinding
import com.example.interactivebible.data.Book


val bookChapters = mapOf("Genesis" to 50,
    "Exodus" to 40,
    "Leviticus" to 27,
    "Numbers" to 36,
    "Deuteronomy" to 34,
    "Joshua" to 24,
    "Judges" to 21,
    "Ruth" to 4,
    "1 Samuel" to 31,
    "2 Samuel" to 24,
    "1 Kings" to 22,
    "2 Kings" to 25,
    "1 Chronicles" to 29,
    "2 Chronicles" to 36,
    "Ezra" to 10,
    "Nehemiah" to 13,
    "Esther" to 10,
    "Job" to 42,
    "Psalms" to 150,
    "Proverbs" to 31,
    "Ecclesiastes" to 12,
    "Song of Solomon" to 8,
    "Isaiah" to 66,
    "Jeremiah" to 52,
    "Lamentations" to 5,
    "Ezekiel" to 48,
    "Daniel" to 12,
    "Hosea" to 14,
    "Joel" to 3,
    "Amos" to 9,
    "Obadiah" to 1,
    "Jonah" to 4,
    "Micah" to 7,
    "Nahum" to 3,
    "Habakkuk" to 3,
    "Zephaniah" to 3,
    "Haggai" to 2,
    "Zechariah" to 14,
    "Malachi" to 4,
    "Matthew" to 28,
    "Mark" to 16,
    "Luke" to 24,
    "John" to 21,
    "Acts" to 28,
    "Romans" to 16,
    "1 Corinthians" to 16,
    "2 Corinthians" to 13,
    "Galatians" to 6,
    "Ephesians" to 6,
    "Philippians" to 4,
    "Colossians" to 4,
    "1 Thessalonians" to 5,
    "2 Thessalonians" to 3,
    "1 Timothy" to 6,
    "2 Timothy" to 4,
    "Titus" to 3,
    "Philemon" to 1,
    "Hebrews" to 13,
    "James" to 5,
    "1 Peter" to 5,
    "2 Peter" to 3,
    "1 John" to 5,
    "2 John" to 1,
    "3 John" to 1,
    "Jude" to 1,
    "Revelation" to 22)

val bookwithnumbers = mapOf("1 Samuel" to "Samuel_One",
    "2 Samuel" to "Samuel_Two",
    "1 Kings" to "Kings_One",
    "2 Kings" to "Kings_Two",
    "1 Chronicles" to "Chronicles_One",
    "2 Chronicles" to "Chronicles_Two",
    "1 Corinthians" to "Corinthians_One",
    "2 Corinthians" to "Corinthians_Two",
    "1 Thessalonians" to "Thessalonians_One",
    "2 Thessalonians" to "Thessalonians_Two",
    "1 Timothy" to "Timothy_One",
    "2 Timothy" to "Timothy_Two",
    "1 Peter" to "Peter_One",
    "2 Peter" to "Peter_Two",
    "1 John" to "John_One",
    "2 John" to "John_Two",
    "3 John" to "John_Three")


class ChapterFragment : Fragment(), ChapterAdapter.ItemClickListener {

    private var _binding: FragmentChapterBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ItemViewModel by activityViewModels()
    private val bookModel: Book by activityViewModels()
    private var chapter_number: Int = 0
    lateinit var book: String
    private var layoutManager: RecyclerView.LayoutManager? = null

    val bundle = activity?.intent?.extras
    var book_data : String = null.toString()
    val intent = activity?.intent
    var verse : Int = 0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentChapterBinding.inflate(inflater, container, false)

        val book = viewModel.selectedItem()


        var book_verse : String

        if (bookwithnumbers.containsKey(book)) {
            book_verse = bookwithnumbers.get(book).toString()
        }
        else {
            book_verse = book
        }
        if (book == "Song of Solomon".toString()){
            book_verse = "Song_of_Solomon"
        }


        val init_book_chapter_verse = bookModel.selectChapterData(book_verse)
        var chapter_ = viewModel.getChapter()
        if (chapter_ != null){
            verse = init_book_chapter_verse?.get(viewModel.getChapter())!!
        }
        else {
            verse = init_book_chapter_verse?.get("1")!!
            chapter_ = "1"
        }
        viewModel.selectVerse(verse.toString())
        var data = ArrayList<Int>()

      //  val book_name = bundle?.getString("book_text")
        if (viewModel.getChapterData() == null){
            val chapter_number = bookChapters.get(book)
            for (i in 1..chapter_number!!) {
                data.add(i)
            }

        }
        else {
            data = viewModel.getChapterData()!!
        }


        val recyclerView : RecyclerView = _binding!!.recyclerView
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = GridLayoutManager(activity, 4)
        val chapteradapter = ChapterAdapter(this, data)
        chapteradapter.setClickListener(this)
        chapteradapter.position_ = chapter_.toInt()
        chapteradapter.insertChapter(chapter_)
        recyclerView.adapter = chapteradapter
        if (chapter_ != null){
            recyclerView.scrollToPosition(chapter_.toInt())
        }

        viewModel.selectChapterAdapter(chapteradapter)
        viewModel.selectChapterData(data)
        viewModel.selectChapter(chapter_)
        viewModel.selectItem(book)


        return binding.root
    }

    interface changeFontListener {
        fun changeFont(view:View?,old_position:Int){
        }
    }



    override fun onItemClick(view: View?, position: Int) {

        val book_name = viewModel.selectedItem()
        val chapter_: Int
        var c_adapter : RecyclerView.Adapter<VerseAdapter.ViewHolder>? = null

        val fonthelp = CustomFontHelper




        if (bookwithnumbers.containsKey(book_name)) {
            book_data = bookwithnumbers.get(book_name).toString()
        }
        else if (book_name == "Song of Solomon".toString()){
            book_data = "Song_of_Solomon"
        }
        else{
            book_data = book_name
        }


        chapter_ = position + 1
       // chapterad.position_ = chapter_

      //  chapterad.insertChapter(chapter_.toString())
        val book_chapter_verse = bookModel.selectChapterData(book_data)
        viewModel.selectChapter(chapter_.toString())
        verse = book_chapter_verse?.get("$chapter_")!!
        val old_verse = viewModel.selectedVerse()?.toInt()

        c_adapter = viewModel.getverseAdapter()
        val verse_data = viewModel.getVerseData()

        if (verse_data != null){
            if (old_verse?.toInt()!! > verse) {
                val end_index_v : Int = old_verse
                val start_index_v : Int = verse
                verse_data.subList(start_index_v,end_index_v).clear()
                c_adapter?.notifyItemRangeRemoved(start_index_v,end_index_v)
                viewModel.selectverseAdapter(c_adapter)
                viewModel.selectVerseData(verse_data)

            }
            else {
                if (old_verse.toInt() < verse) {
                    val start_index_v : Int = old_verse
                    val end_index_v : Int = verse
                    val item : ArrayList<Int> = ArrayList()

                    for (i in start_index_v+1..end_index_v){
                        item.add(i)
                    }

                    verse_data.addAll(start_index_v,item)
                    c_adapter?.notifyItemRangeInserted(start_index_v,item.size)

                    viewModel.selectverseAdapter(c_adapter)
                    viewModel.selectVerseData(verse_data)

                }
                else {
                    viewModel.selectverseAdapter(c_adapter)
                    viewModel.selectVerseData(verse_data)

                }
            }

        }

        viewModel.selectVerse(verse.toString())

        val viewpager: ViewPager2? = activity?.findViewById(R.id.vpPager)
        viewpager?.currentItem = 2

    }


}