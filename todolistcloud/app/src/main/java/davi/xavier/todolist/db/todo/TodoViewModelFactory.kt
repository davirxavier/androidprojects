package davi.xavier.todolist.db.todo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class TodoViewModelFactory() : ViewModelProvider.Factory {
    
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return TodoViewModel() as T
    }
}
