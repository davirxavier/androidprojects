package davi.xavier.todolist.db.category

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import davi.xavier.todolist.R
import davi.xavier.todolist.db.todo.StringDiffChecker

class CategoryAdapter(): RecyclerView.Adapter<CategoryViewHolder>() {
    var checkedCallback: (pos: Int, button: RadioButton) -> Unit = { _: Int, _: RadioButton -> }
    var checked: Int = 0
    private var list: MutableList<String> = mutableListOf()

    fun setAll(newList: List<String>) {
        val result = DiffUtil.calculateDiff(StringDiffChecker(this.list, newList))
        list.clear()
        list.addAll(newList)

        result.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.cat_item, parent, false)

        return CategoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.radioButton.text = list[position]
        holder.radioButton.isChecked = checked == position
        holder.radioButton.setOnClickListener { checkedCallback(position, holder.radioButton) }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}
