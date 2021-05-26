package davi.xavier.todolist.db.todo

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
class Todo(
    var text: String? = null,
    var category: Int = -1,
    var uid: String? = null
) {

}
