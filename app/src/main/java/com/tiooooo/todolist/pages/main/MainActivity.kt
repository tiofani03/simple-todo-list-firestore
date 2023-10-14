package com.tiooooo.todolist.pages.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.tiooooo.todolist.adapter.TodoAdapter
import com.tiooooo.todolist.databinding.ActivityMainBinding
import com.tiooooo.todolist.model.Todo
import com.tiooooo.todolist.pages.add.AddTodoActivity
import com.tiooooo.todolist.pages.detail.DetailActivity

class MainActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_MESSAGE = "extra_message"
        const val EXTRA_ID = "extra_id"
        const val EXTRA_EDIT = "extra_edit"
    }

    private lateinit var binding: ActivityMainBinding
    private val todoAdapter = TodoAdapter(handleAdapterListener())
    private var dummyData: MutableList<Todo> = mutableListOf()

    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mainViewModel.getTodoList()
        mainViewModel.todoList.observe(this) {
            binding.tvEmptyMessage.isVisible = it.isEmpty()
            todoAdapter.setData(it)
        }

        binding.rvTodo.apply {
            adapter = todoAdapter
            layoutManager =
                LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
        }

        binding.fabAdd.setOnClickListener {
            val intent = Intent(this, AddTodoActivity::class.java)
            startActivity(intent)
        }

    }

    private fun handleAdapterListener() = object : TodoAdapterListener {
        override fun onClickDetail(todo: Todo) {
            val intent = Intent(this@MainActivity, DetailActivity::class.java)
            intent.putExtra(EXTRA_ID, todo.uid)
            intent.putExtra(EXTRA_MESSAGE, todo.description)

            startActivity(intent)
        }

        override fun onClickEdit(todo: Todo) {
            val intent = Intent(this@MainActivity, AddTodoActivity::class.java)
            intent.putExtra(EXTRA_ID, todo.uid)
            intent.putExtra(EXTRA_EDIT, true)
            intent.putExtra(EXTRA_MESSAGE, todo.description)
            startActivity(intent)
        }

    }
}


interface TodoAdapterListener {
    fun onClickDetail(todo: Todo)
    fun onClickEdit(todo: Todo)
}
