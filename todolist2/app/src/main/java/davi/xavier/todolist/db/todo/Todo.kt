package davi.xavier.todolist.db.todo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Todo(
    @ColumnInfo(name = "text") var text: String? = null,
    @PrimaryKey(autoGenerate = true) var id: Int = 0
) {

}
