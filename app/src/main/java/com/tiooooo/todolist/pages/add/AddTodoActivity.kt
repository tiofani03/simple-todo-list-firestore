package com.tiooooo.todolist.pages.add

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import com.tiooooo.todolist.databinding.ActivityAddTodoBinding
import com.tiooooo.todolist.model.Todo
import com.tiooooo.todolist.pages.main.MainActivity.Companion.EXTRA_EDIT
import com.tiooooo.todolist.pages.main.MainActivity.Companion.EXTRA_ID
import com.tiooooo.todolist.pages.main.MainActivity.Companion.EXTRA_MESSAGE

class AddTodoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddTodoBinding
    private val addTodoViewModel: AddTodoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddTodoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            setSupportActionBar(binding.toolbar)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)

            val isEdit = intent.getBooleanExtra(EXTRA_EDIT, false)

            val uid = intent.getStringExtra(EXTRA_ID)
            val message = intent.getStringExtra(EXTRA_MESSAGE).toString()

            if (isEdit) {
                supportActionBar?.title = "Edit"
                edtText.setText(message)
            }

            binding.btnDelete.isVisible = isEdit

            edtText.doAfterTextChanged {
                btnSave.isEnabled = it?.toString()?.isNotEmpty() ?: false
            }

            btnDelete.setOnClickListener {
                uid?.let {
                    addTodoViewModel.delete(uid) {
                        handleSuccessAction("${edtText.text.toString()} Berhasil dihapus", it)
                    }
                }
            }

            btnSave.setOnClickListener {
                if (isEdit) {
                    uid?.let {
                        val newTodo = Todo(uid, uid, edtText.text.toString())
                        addTodoViewModel.update(uid, newTodo) {
                            handleSuccessAction("${edtText.text.toString()} Berhasil diubah", it)
                        }
                    }
                } else {
                    addTodoViewModel.insert(edtText.text.toString()) {
                        handleSuccessAction("${edtText.text.toString()} berhasil ditambahkan", it)
                    }
                }
            }
        }
    }

    private fun handleSuccessAction(message: String, state: Boolean) {
        if (state) {
            Toast.makeText(
                this@AddTodoActivity, message, Toast.LENGTH_SHORT
            ).show()
            finish()
        } else {
            Toast.makeText(
                this@AddTodoActivity, "Terjadi kesalahan", Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }
}
