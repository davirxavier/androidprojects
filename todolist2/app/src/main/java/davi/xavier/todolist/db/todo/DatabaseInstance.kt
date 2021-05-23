package davi.xavier.todolist.db.todo

import android.content.Context
import androidx.room.Room

object DatabaseInstance {
    
    private var database: AppDatabase? = null
    
    fun getInstance(context: Context): AppDatabase {
        if (database == null) {
            database = Room.databaseBuilder(context, AppDatabase::class.java, "todolist2.db").build()
        }
        
        return database as AppDatabase
    }
}
