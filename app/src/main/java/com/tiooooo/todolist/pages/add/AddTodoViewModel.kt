package com.tiooooo.todolist.pages.add

import androidx.lifecycle.ViewModel
import com.tiooooo.todolist.data.firebase.Firestore
import com.tiooooo.todolist.data.firebase.todoService.TodoService
import com.tiooooo.todolist.model.Todo
import com.tiooooo.todolist.utils.FirebaseConstants

class AddTodoViewModel : ViewModel() {
    fun insert(
        description: String,
        isSuccess: (Boolean) -> Unit,
    ) {
        val uid = Firestore.instance.collection(FirebaseConstants.TODO_DOC).document().id
        val todo = Todo(uid, uid, description)

        TodoService.insert(todo, uid) {
            isSuccess(it)
        }
    }

    fun update(
        uid: String,
        todo: Todo,
        isSuccess: (Boolean) -> Unit,
    ) {
        TodoService.update(uid, todo) { isSuccess(it) }
    }

    fun delete(
        uid: String,
        isSuccess: (Boolean) -> Unit,
    ) {
        TodoService.delete(uid) { isSuccess(it) }
    }
}
