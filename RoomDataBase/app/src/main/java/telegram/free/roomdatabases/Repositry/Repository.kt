package telegram.free.roomdatabases.Repositry

import android.content.Context
import androidx.lifecycle.LiveData
import telegram.free.roomdatabases.Db.Notes
import telegram.free.roomdatabases.Db.NotesDatabase

class Repository(context: Context) {
    var db: NotesDatabase
    var list: LiveData<MutableList<Notes>>

    init {
        db = NotesDatabase(context)
        list = db.getNotesDao().getNotesData()
    }

    fun getAllNotes(): LiveData<MutableList<Notes>> {
        return list;
    }

    fun insertNotes(notes: Notes) {
        db.getNotesDao().addNones(notes)
    }

    fun deleteNotes(notes: Notes) {
        db.getNotesDao().delete(notes)
    }

    fun editNotes(notes: Notes) {
        db.getNotesDao().update(notes)
    }
}