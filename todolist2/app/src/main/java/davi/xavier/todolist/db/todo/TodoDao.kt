package davi.xavier.todolist.db.todo

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TodoDao {
    @Query("SELECT * FROM todo")
    fun getAll(): LiveData<List<Todo>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(todo: Todo)
    
    @Delete
    suspend fun delete(todo: Todo)
}
