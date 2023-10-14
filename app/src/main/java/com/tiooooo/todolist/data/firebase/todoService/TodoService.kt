package com.tiooooo.todolist.data.firebase.todoService

import android.util.Log
import com.google.firebase.firestore.Query
import com.tiooooo.todolist.data.firebase.Firestore
import com.tiooooo.todolist.model.Todo
import com.tiooooo.todolist.utils.FirebaseConstants
import com.tiooooo.todolist.utils.mapped

object TodoService {
    private const val TAG = "TodoService"

    fun insert(
        todo: Todo,
        id: String,
        isSuccess: (Boolean) -> Unit,
    ) {
        Firestore.instance.collection(FirebaseConstants.TODO_DOC).document(id).set(todo)
            .addOnSuccessListener { isSuccess(true) }.addOnFailureListener { isSuccess(false) }
    }

    fun update(
        uid: String,
        todo: Todo,
        isSuccess: (Boolean) -> Unit,
    ) {
        Firestore.instance.collection(FirebaseConstants.TODO_DOC).document(uid)
            .update(todo.mapped())
            .addOnSuccessListener { isSuccess(true) }
            .addOnFailureListener { isSuccess(false) }
    }

    fun delete(
        uid: String,
        isSuccess: (Boolean) -> Unit,
    ) {
        Firestore.instance.collection(FirebaseConstants.TODO_DOC).document(uid).delete()
            .addOnSuccessListener { isSuccess(true) }.addOnFailureListener { isSuccess(false) }
    }

    fun getAllData(onResult: (list: List<Todo>) -> Unit) {
        Firestore.instance.collection(FirebaseConstants.TODO_DOC)
            .orderBy("time", Query.Direction.DESCENDING).addSnapshotListener { value, error ->
                if (error != null) Log.d(TAG, "getAllData Error ${error.message}")
                if (value != null && !value.isEmpty) {
                    onResult(value.toObjects(Todo::class.java))
                    Log.d(TAG, "getAllData Success")
                } else {
                    onResult(listOf())
                    if (error != null) Log.d(TAG, "getAllData Error ${error.message}")
                }
            }
    }

}
