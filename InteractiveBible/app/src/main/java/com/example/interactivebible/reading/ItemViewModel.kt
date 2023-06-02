package com.example.interactivebible.reading

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import io.realm.mongodb.mongo.MongoCollection
import org.bson.Document

var adapter : RecyclerView.Adapter<ChapterAdapter.ViewHolder>? = null
var verseadapter: RecyclerView.Adapter<VerseAdapter.ViewHolder>? = null

var dataset: ArrayList<Int>? = null
var versedataset: ArrayList<Int>? = null
var collection: MongoCollection<Document>? = null
var verse: String? = null
var chapter: String? = null
var book : String? =  null
var book_button_text : String? = null
var verseview : View? = null

class ItemViewModel : ViewModel() {

    fun setVerseView(view: View?){
        verseview = view
    }

    fun getVerseView() : View? {
        if (verseview == null){
            return null
        }
        return verseview
    }

    fun setBookButtonText(item: String?){
        book_button_text = item
    }

    fun getBookButtontText(): String {
        return book_button_text.toString()
    }

    fun selectChapterData(item: ArrayList<Int>){
        dataset = item
    }

    fun getChapterData(): ArrayList<Int>? {
        if (dataset == null){
            return null
        }
        return dataset!!
    }

    fun selectVerseData(item: ArrayList<Int>){
        versedataset = item
    }

    fun getVerseData(): ArrayList<Int>? {
        if (versedataset == null){
            return null
        }
        return versedataset!!
    }


    fun selectedItem():String{
        return book.toString()
    }


    fun getChapter(): String? {
        if (chapter == null){
            return null
        }
        return chapter.toString()
    }

    fun selectChapter(item: String) {
        chapter = item
    }

    fun selectedVerse():String?{
        if (verse == null){
            return null
        }
        return verse.toString()
    }

    fun selectVerse(item: String) {
        verse = item
    }

    fun selectItem(item: String) {
        book = item
    }

    fun selectChapterAdapter(item: RecyclerView.Adapter<ChapterAdapter.ViewHolder>?) {
        adapter = item
    }

    fun getChapterAdapter(): RecyclerView.Adapter<ChapterAdapter.ViewHolder>? {
        if (adapter == null) {
            return null
        }
        return adapter!!
    }

    fun selectverseAdapter(item: RecyclerView.Adapter<VerseAdapter.ViewHolder>?) {
        verseadapter = item
    }

    fun getverseAdapter(): RecyclerView.Adapter<VerseAdapter.ViewHolder>? {
        if (verseadapter == null) {
            return null
        }
        return verseadapter!!
    }

    fun insertMongo(item: MongoCollection<Document>?) {
        collection = item
    }

    fun getCollection(): MongoCollection<Document>? {
        if (collection == null) {
            return null
        }
        return collection
    }

}