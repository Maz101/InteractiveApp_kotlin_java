package com.example.interactivebible.reading

// Utility Packages
//import androidx.room.PrimaryKey
//import io.realm.*
//import io.realm.RealmObject
//import io.realm.mo
//import io.realm.kotlin.where

//import io.realm.RealmList
//import io.realm.annotations.RealmClass
//import io.realm.RealmConfiguration

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ClickableSpan
import android.text.style.RelativeSizeSpan
import android.text.style.SuperscriptSpan
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import app.com.kotlinapp.OnSwipeTouchListener
import com.example.interactivebible.R
import com.example.interactivebible.data.ApplicationState
import com.example.interactivebible.databinding.ActivityReadingBinding
import io.realm.mongodb.App
import io.realm.mongodb.AppConfiguration
import io.realm.mongodb.User
import io.realm.mongodb.mongo.MongoClient
import io.realm.mongodb.mongo.MongoCollection
import io.realm.mongodb.mongo.MongoDatabase
import org.bson.Document
import org.bson.codecs.configuration.CodecRegistries
import org.bson.codecs.pojo.PojoCodecProvider


//open class Bible(
//    @PrimaryKey var _id: Long? = null,
//    var Book: String? = null,
//    var Chapters: RealmList<Bible_Chapters> = RealmList()
//): RealmObject()
//
//@RealmClass(embedded = true)
//open class Bible_Chapters(
//    var _id: ObjectId? = null,
//    var number: Long? = null,
//    var verses: RealmList<Bible_Chapters_verses> = RealmList()
//): RealmObject()
//
//@RealmClass(embedded = true)
//open class Bible_Chapters_verses(
//    var _id: ObjectId? = null,
//    var verse_element: String? = null,
//    var verse_number: Long? = null
//): RealmObject()
//


//
//val books: RealmResults<Book> = backgroundThreadRealm.where<Book>().findAll()


class ReadingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityReadingBinding
    val SHARED_PREFS: String = "sharedPrefs"
    lateinit var default_book: String

    inline fun <reified T> T.TAG(): String = T::class.java.simpleName
    lateinit var bibleApp: App
    var success = false

    lateinit var mongodb: MongoDatabase
    lateinit var mongoClient: MongoClient

    private val viewModel: ItemViewModel by viewModels()
    lateinit var bible_data: Any
    var book_current: String = ""
    var chapter_current: String = ""
    lateinit var chapters: List<*>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityReadingBinding.inflate(layoutInflater)

        val default_book = "Genesis"
        val appID: String = "application-prico"

        bibleApp = App(
            AppConfiguration.Builder(appID)
                .defaultSyncErrorHandler { session, error ->
                    Log.e(TAG(), "Sync error: ${error.errorMessage}")
                }
                .build())

        bibleApp.loginAsync(io.realm.mongodb.Credentials.anonymous()) {
            if (!it.isSuccess) {
                onLoginFailed(it.error.message ?: "An error occurred.")
            } else {
                success = true
                onLoginSuccess()

            }
        }

        setContentView(binding.root)

        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.setDisplayShowTitleEnabled(false)

        val choose_book = binding.bookButton
        choose_book.setOnClickListener {
            openFragment(default_book)
        }

        updateViews(default_book = default_book, default_chapter = '1'.toString())

        val layout = binding.contentreyclerpager

        layout.setOnTouchListener(object :
            OnSwipeTouchListener(this@ReadingActivity) {

            val function_r = { chap_n: Int -> chap_n + 1}
            val function_l = { chap_n: Int -> chap_n - 1}

            override fun onSwipeRight() {
                super.onSwipeRight()

                swipeChange(viewModel,function_r)
                }

            override fun onSwipeLeft() {
                super.onSwipeLeft()

                swipeChange(viewModel,function_l)
            }
            }
        )

    }

    fun swipeChange(viewModel: ItemViewModel, function: (Int) -> Int) {
        val mongoCollection = viewModel.getCollection()
        var chap_ = viewModel.getChapter()?.toInt()
        if (chap_ != null) {
            chap_ = function(chap_)
            val query_filter = Document("Book", book_current).append("C_no", chap_)

            mongoCollection?.findOne(query_filter)
                ?.getAsync { task ->
                    if (task.isSuccess) {
                        val result = task.get()
                        val chapters_ =
                            (result.get("Chapters") as List<*>).elementAt(0) as Document
                        val verses = chapters_.get("verses") as ArrayList<Document>
                        var clickable = false
                        if (book_current.contains("Genesis") && chap_ == 2){
                            clickable = true
                        }
                        createpage(verses,clickable)


                    } else {
                        Log.e("EXAMPLE", "failed to find document with: ${task.error}")
                    }
                }

            val string_b = viewModel.getBookButtontText()

            binding.bookButton.text = "$string_b   $chap_"
            //  viewModel.setBookButtonText(string_b)
            viewModel.selectChapter(chap_.toString())

            updateViews(default_book = string_b, default_chapter = chap_.toString())
        }
    }


    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
        return super.onCreateView(name, context, attrs)

        saveData(default_book)

    }


    private fun saveData(book_text: String) {
        val sharedpreferences: SharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE)
        val editor = sharedpreferences.edit()

        editor.putString("book_text", book_text).apply()

    }


    private fun updateViews(default_book: String, default_chapter: String) {
        val sharedpreferences: SharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE)
        var string_b: String? = null
        var chap: String? = null


        if (viewModel.selectedItem() != null.toString()) {
            sharedpreferences.edit().putString("book_text", viewModel.selectedItem()).apply()
            string_b = viewModel.selectedItem()
        } else {
            if (sharedpreferences.getString("book_text", "") == "") {
                string_b = "Genesis"

            } else {
                string_b = sharedpreferences.getString("book_text", default_book)
            }
        }

        if (viewModel.getChapter() != null) {
            sharedpreferences.edit().putString("chapter", viewModel.getChapter()).apply()
            chap = viewModel.getChapter()
        } else {
            if (sharedpreferences.getString("chapter", "") == "") {
                chap = 1.toString()
            } else {
                chap = sharedpreferences.getString("chapter", default_chapter)
            }
        }

        binding.bookButton.text = "$string_b   $chap"
        viewModel.setBookButtonText(string_b)
        viewModel.selectChapter(chap.toString())
        book_current = string_b.toString()
        chapter_current = chap.toString()

    }


    private fun openFragment(default_book: String) {
        val intent = Intent(this, Reference::class.java)
        intent.putExtra("book_text", default_book)
        startActivity(intent)
    }


    private fun onLoginSuccess() {
        val user: User? = bibleApp.currentUser()
        mongoClient = user?.getMongoClient("mongodb-atlas")!!
        val pojoCodecRegistry = CodecRegistries.fromRegistries(
            AppConfiguration.DEFAULT_BSON_CODEC_REGISTRY,
            CodecRegistries.fromProviders(
                PojoCodecProvider.builder().automatic(true).build()
            )
        )

        mongodb = mongoClient.getDatabase("mydatabase")
        val mongoCollection: MongoCollection<Document>? = mongodb.getCollection("NKJBible_")
            .withCodecRegistry(pojoCodecRegistry)

        viewModel.insertMongo(mongoCollection)

        val query_filter = Document("Book", book_current).append("C_no", chapter_current.toInt())

        mongoCollection?.findOne(query_filter)
            ?.getAsync { task ->
                if (task.isSuccess) {
                    val result = task.get()
                    val chapters_ = (result.get("Chapters") as List<*>).elementAt(0) as Document
                    val verses = chapters_.get("verses") as ArrayList<Document>
                    var clickable = false
                    if (book_current.contains("Genesis") && chapter_current.toInt() == 2)
                    {
                    clickable = true
                    }

                    createpage(verses,clickable)

                } else {
                    Log.e("EXAMPLE", "failed to find document with: ${task.error}")
                }
            }
    }

    private fun onLoginFailed(errorMsg: String) {
        Log.e(TAG(), errorMsg)
        Toast.makeText(baseContext, errorMsg, Toast.LENGTH_LONG).show()
    }


    private fun createpage(verses: ArrayList<Document>, clickable: Boolean) {

        val recyclerView = binding.contentreyclerpager
        val data = processData(verses,clickable)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val pageadapter = PageAdapter(data)
        recyclerView.adapter = pageadapter
        val editBox: View? = viewModel.getVerseView()

        if (editBox != null) {
            val pos_two = recyclerView.getChildAdapterPosition(editBox)
            recyclerView.scrollToPosition(pos_two)
        }

    }

    private fun concat(s1: String, s2: String): String {
        return s1 + ' ' + s2
    }

    private fun processData(verses: ArrayList<Document>, clickable: Boolean): ArrayList<CharSequence> {

        var processedList: ArrayList<CharSequence> = arrayListOf()
        var count = 0

        for (elem in verses) {
            val string_number = elem.get("verse_number").toString()
            val string_elem = elem.get("verse_element").toString()
            val final_string = concat(string_number,string_elem)

            val string_span = SpannableStringBuilder(final_string)
            val clickableSpan : ClickableSpan = object : ClickableSpan(){
                override fun onClick(widget: View) {
                }
            }

            string_span.setSpan(RelativeSizeSpan(0.5f),string_number.indexOf(string_number),string_number.indexOf(string_number)+string_number.length,0)
            string_span.setSpan(SuperscriptSpan(),string_number.indexOf(string_number),string_number.indexOf(string_number)+string_number.length,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

            if (clickable && count == 13) {
                val hiddekel = "Hiddekel"
                val start = string_span.indexOf(hiddekel)
                val end = start + hiddekel.length
                string_span.setSpan(clickableSpan, start,end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                val string_two = string_span.substring(end,string_span.length)
            }
            else {
              //  var string_d = ApplicationState(text_data_pre = string_span)
                processedList += string_span
            }
            count ++


        }

        return processedList

    }

    /**
     * Called when a touch event is dispatched to a view. This allows listeners to
     * get a chance to respond before the target view.
     *
     * @param v The view the touch event has been dispatched to.
     * @param event The MotionEvent object containing full information about
     * the event.
     * @return True if the listener has consumed the event, false otherwise.
     */

}



