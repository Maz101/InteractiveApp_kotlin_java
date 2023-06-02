package com.example.interactivebible.data

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass
import org.bson.types.ObjectId


open class NKJBible_(
    @PrimaryKey var _id: Long? = null,
    var Book: String? = null,
    var C_no: Long? = null,
    var Chapters: RealmList<NKJBible__Chapters> = RealmList()
): RealmObject()


@RealmClass(embedded = true)
open class NKJBible__Chapters(
    var _id: ObjectId? = null,
    var number: Long? = null,
    var verses: RealmList<NKJBible__Chapters_verses> = RealmList()
): RealmObject()


@RealmClass(embedded = true)
open class NKJBible__Chapters_verses(
    var _id: ObjectId? = null,
    var verse_element: String? = null,
    var verse_number: Long? = null
): RealmObject()
