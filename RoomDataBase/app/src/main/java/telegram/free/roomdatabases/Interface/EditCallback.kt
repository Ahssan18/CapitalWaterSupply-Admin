package telegram.free.roomdatabases.Interface

import telegram.free.roomdatabases.Db.Notes

interface EditCallback {
    fun edit(notes: Notes);
}