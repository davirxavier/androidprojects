package davi.xavier.todolist.db.todo

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class TodoViewModel(todoDao: TodoDao): ViewModel() {
    private val dao = todoDao
    
    private val todos: LiveData<List<Todo>> by lazy {
        dao.getAll()
    }
    
    fun newTodo(text: String) {
        viewModelScope.launch { dao.insert(Todo(text)) }
    }
    
    fun deleteTodo(id: Int) {
        viewModelScope.launch { 
            dao.delete(Todo("", id)) 
        }
    }
    
    fun getTodoList(): LiveData<List<Todo>> {
        return todos
    }
}
