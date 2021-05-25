package davi.xavier.todolist.db.todo

import androidx.recyclerview.widget.DiffUtil

class StringDiffChecker(
    private val old: List<String>,
    private val new: List<String>
    ) : DiffUtil.Callback() {
    
    override fun getOldListSize(): Int {
        return old.size
    }

    override fun getNewListSize(): Int {
        return new.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return old[oldItemPosition] == new[newItemPosition]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return old[oldItemPosition] == new[newItemPosition]
    }
}
