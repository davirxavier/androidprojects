package davi.xavier.todolist.db.todo

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase

class FirebaseTodoDao : TodoDao {
    private val database: DatabaseReference = DatabaseInstance.getInstance()
    private val firebaseAuth: FirebaseAuth = Firebase.auth
    
    override fun getAll(): LiveData<List<Todo>> {
        checkLogged()
        
        val liveData = MutableLiveData<List<Todo>>()
        val currentUserId = firebaseAuth.currentUser!!.uid

        val listener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val todos: MutableList<Todo> = mutableListOf()
                for (ds in dataSnapshot.children) {
                    val todo = ds.getValue(Todo::class.java)
                    if (todo != null) {
                        todos.add(todo)
                    }
                }
                
                liveData.postValue(todos)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.w("GET TODOS", "load:onCancelled", databaseError.toException())
            }
        }
        database.child("usersTodo").child(currentUserId).addValueEventListener(listener)
        return liveData
    }

    override fun getAllByCategoryId(id: Int): LiveData<List<Todo>> {
        checkLogged()

        val liveData = MutableLiveData<List<Todo>>()
        val currentUserId = firebaseAuth.currentUser!!.uid

        val listener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val todos: MutableList<Todo> = mutableListOf()
                for (ds in dataSnapshot.children) {
                    val todo = ds.getValue(Todo::class.java)
                    if (todo != null && todo.category == id) {
                        todos.add(todo)
                    }
                }

                liveData.postValue(todos)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.w("GET TODOS", "load:onCancelled", databaseError.toException())
            }
        }
        database.child("usersTodo").child(currentUserId).addValueEventListener(listener)
        return liveData
    }

    override suspend fun insert(todo: Todo) {
        checkLogged()
        val currentUserId = firebaseAuth.currentUser!!.uid
        
        val key: String? = database.child("usersTodo").child(currentUserId).push().key
        if (key != null) {
            val map: MutableMap<String, Any> = HashMap()
            map[key] = todo
            todo.uid = key
            database.child("usersTodo").child(currentUserId).updateChildren(map)
        }
    }

    override suspend fun delete(todo: Todo) {
        checkLogged()
        val currentUserId = firebaseAuth.currentUser!!.uid

        todo.uid?.let { database.child("usersTodo").child(currentUserId).child(it).removeValue() }
    }
    
    private fun checkLogged() {
        if (firebaseAuth.currentUser == null) {
            throw IllegalStateException("Estado de usuário inválido, favor fazer login.")
        }
    }
}
