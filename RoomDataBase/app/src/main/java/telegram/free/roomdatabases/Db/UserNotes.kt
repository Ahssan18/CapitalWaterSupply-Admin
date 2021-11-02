package telegram.free.roomdatabases.Db

import androidx.room.Embedded
import androidx.room.Relation

data class UserNotes(
    @Embedded  val user: User,
    @Relation(
        parentColumn = "userid",
        entityColumn = "notesid"
    )
    var notes: Notes

)
