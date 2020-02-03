package ru.job4j.todolist

import android.content.ClipData.Item
import ru.job4j.todolist.db.Task


interface IStore {
    fun add(task: Task?)

    fun size(): Int

    operator fun get(index: Int): Task?
}