package telegram.free.roomdatabases.Db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Notes (
    @ColumnInfo(name = "title") var title: String,
    @ColumnInfo(name = "content") var content: String,
    @ColumnInfo(name = "notesid") var notesid: Long
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}
