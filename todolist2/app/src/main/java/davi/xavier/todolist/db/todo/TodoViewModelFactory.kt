package davi.xavier.todolist.db.todo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class TodoViewModelFactory(val todoDao: TodoDao) : ViewModelProvider.Factory {
    
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return TodoViewModel(todoDao) as T
    }
}
