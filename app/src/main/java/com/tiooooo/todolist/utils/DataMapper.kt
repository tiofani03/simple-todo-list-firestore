package com.tiooooo.todolist.utils

import com.tiooooo.todolist.model.Todo

fun Todo.mapped(): Map<String, *> = mapOf(
    "id" to this.uid,
    "description" to this.description,
    "time" to this.time,
)
