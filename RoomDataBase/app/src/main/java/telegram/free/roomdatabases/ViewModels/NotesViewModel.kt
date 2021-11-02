package telegram.free.roomdatabases.ViewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import telegram.free.roomdatabases.Db.Notes
import telegram.free.roomdatabases.Repositry.Repository

class NotesViewModel(application: Application) : AndroidViewModel(application) {
    var repository: Repository = Repository(application)
    var list = repository.getAllNotes()
    fun getAllData(): LiveData<MutableList<Notes>> {
        return list
    }

    fun insertData(notes: Notes) {
        repository.insertNotes(notes)
    }

    fun deleteNotes(notes: Notes) {
        repository.deleteNotes(notes)
    }

    fun editNotes(notes: Notes) {
        repository.editNotes(notes)
    }
}