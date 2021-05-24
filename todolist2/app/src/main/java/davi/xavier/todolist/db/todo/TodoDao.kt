package davi.xavier.todolist.db.todo

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TodoDao {
    @Query("SELECT * FROM todo")
    fun getAll(): LiveData<List<Todo>>
    
    @Query("SELECT * FROM todo WHERE category = :id")
    fun getAllByCategoryId(id: Int): LiveData<List<Todo>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(todo: Todo)
    
    @Delete
    suspend fun delete(todo: Todo)
}
