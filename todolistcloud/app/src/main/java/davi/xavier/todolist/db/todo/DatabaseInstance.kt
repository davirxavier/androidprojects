package davi.xavier.todolist.db.todo

import android.content.Context
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

object DatabaseInstance {
    
    private val database: DatabaseReference by lazy {
        Firebase.database.reference
    }
    
    @Deprecated("Use getInstance()")
    fun getInstance(context: Context): DatabaseReference {
        return database
    }
    
    fun getInstance(): DatabaseReference {
        return database
    }
}
