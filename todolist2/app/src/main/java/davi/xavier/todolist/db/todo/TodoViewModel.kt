package davi.xavier.todolist.db.todo

import androidx.lifecycle.*
import kotlinx.coroutines.launch

class TodoViewModel(todoDao: TodoDao): ViewModel() {
    private val dao = todoDao
    
    private val todos = MutableLiveData<List<Todo>>(mutableListOf())
    init {
        val data = dao.getAll()
        data.observeForever{
            todos.postValue(it)
        }
    }
    
    fun newTodo(text: String, catId: Int) {
        viewModelScope.launch { dao.insert(Todo(text, catId)) }
    }
    
    fun deleteTodo(id: Int) {
        viewModelScope.launch { 
            dao.delete(Todo("", id)) 
        }
    }
    
    fun getTodoList(): LiveData<List<Todo>> {
        return todos
    }
    
    fun filterTodosByCat(catId: Int, lifecycleOwner: LifecycleOwner) {
        if (catId == 0)
        {
            dao.getAll().observe(lifecycleOwner, {
                todos.value = it
            })
        }
        else 
        {
            dao.getAllByCategoryId(catId).observe(lifecycleOwner, {
                todos.value = it
            })
        }
    }
}
