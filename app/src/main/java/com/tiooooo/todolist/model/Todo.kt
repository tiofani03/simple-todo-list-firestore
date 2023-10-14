package com.tiooooo.todolist.model

import android.os.Parcelable
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId
import kotlinx.parcelize.Parcelize

@Parcelize
data class Todo(
    @DocumentId var uid: String,
    val id: String,
    val description: String,
    var time: Timestamp? = Timestamp.now(),

    ) : Parcelable {
    constructor() : this("", "", "", Timestamp.now())
}

