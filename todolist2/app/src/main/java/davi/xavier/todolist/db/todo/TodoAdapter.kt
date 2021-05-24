package davi.xavier.todolist.db.todo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import davi.xavier.todolist.R

open class TodoAdapter() : RecyclerView.Adapter<TodoViewHolder>() {
    var deleteCallback: (pos: Int) -> Unit = {}
    private var todos: MutableList<String> = mutableListOf()

    fun add(text: String) {
        todos.add(text)
        notifyItemInserted(todos.size)
    }

    fun delete(i: Int) {
        if (i < todos.size && i >= 0)
        {
            todos.removeAt(i)
            notifyItemRemoved(i)
            notifyItemRangeChanged(i, todos.size)
        }
    }

    fun setAll(newTodos: List<String>) {
        val result = DiffUtil.calculateDiff(StringDiffChecker(this.todos, newTodos))
        todos.clear()
        todos.addAll(newTodos)

        result.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_element, parent, false)

        return TodoViewHolder(view)
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        holder.textView.text = todos[position]
        holder.deleteButton.setOnClickListener {
            deleteCallback(holder.bindingAdapterPosition)
        }
    }

    override fun getItemCount(): Int {
        return todos.size
    }

}
