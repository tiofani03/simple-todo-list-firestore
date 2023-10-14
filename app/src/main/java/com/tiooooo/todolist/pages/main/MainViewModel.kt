package com.tiooooo.todolist.pages.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tiooooo.todolist.data.firebase.todoService.TodoService
import com.tiooooo.todolist.model.Todo
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val _todoList = MutableLiveData<List<Todo>>()
    val todoList = _todoList

    fun getTodoList() = viewModelScope.launch {
        TodoService.getAllData {
            _todoList.postValue(it)
        }
    }

}
