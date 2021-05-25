package davi.xavier.todolist.db.category

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CategoryViewModel : ViewModel() {
    private val categories = MutableLiveData<List<Category>>(mutableListOf(
        Category("Todos", 0),
        Category("Entretenimento", 1),
        Category("Assistir", 2),
        Category("Tarefa", 3),
        Category("Faculdade", 4),
        Category("Estudar", 5)
    ))
    
    fun getAll(): LiveData<List<Category>> {
        return categories
    }
}
