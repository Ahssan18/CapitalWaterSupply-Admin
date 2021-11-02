package telegram.free.roomdatabases.Interface

import telegram.free.roomdatabases.Db.Notes

interface DeleteInterface {
    fun deleteItem(notes: Notes)
}