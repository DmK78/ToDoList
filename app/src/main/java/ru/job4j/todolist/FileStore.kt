package ru.job4j.todolist

import android.content.ClipData.Item
import android.content.Context
import ru.job4j.todolist.db.Task
import java.io.*
import java.lang.Boolean
import java.util.*


class FileStore private constructor(private val context: Context) : IStore {
    private var counter = 0
    override fun add(task: Task?) {
        val file = File(context.filesDir, counter++.toString() + ".txt")
        try {
            PrintWriter(BufferedWriter(FileWriter(file))).use { out ->
                out.println(task?.name)
                out.println(task?.created)
                out.println(task?.closed)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    override fun size(): Int {
        return context.filesDir.listFiles().size
    }

    override operator fun get(index: Int): Task? {
        val task = Task(0, "", "", "", "")
        val file = File(context.filesDir, "$index.txt")
        try {
            BufferedReader(FileReader(file)).use { `in` ->
                task.name = `in`.readLine()
                val cal = Calendar.getInstance()
                cal.timeInMillis = `in`.readLine().toLong()
                task.created = cal.toString()
//                item.setDone(Boolean.parseBoolean(`in`.readLine()))
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return task
    }

    companion object {
        private var instance: IStore? = null
        fun getInstance(context: Context): IStore? {
            if (instance == null) {
                instance = FileStore(context)
            }
            return instance
        }
    }

}