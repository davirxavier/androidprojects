package davi.xavier.todolist.db.todo

import androidx.lifecycle.LiveData

interface TodoDao {
    fun getAll(): LiveData<List<Todo>>
    fun getAllByCategoryId(id: Int): LiveData<List<Todo>>
    suspend fun insert(todo: Todo)
    suspend fun delete(todo: Todo)
}
