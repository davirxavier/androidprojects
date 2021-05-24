package davi.xavier.todolist.db.category

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Category(
    @ColumnInfo(name = "name") val name: String? = null,
    @PrimaryKey(autoGenerate = true) val id: Int = 0
) {
}
