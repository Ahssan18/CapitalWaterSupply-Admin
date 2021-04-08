package telegram.free.roomlibraryandroid.Fragments.RoomDatabase

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Notes(

    val title: String,
    val description: String
) {
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0

}