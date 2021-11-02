package telegram.free.roomdatabases.Db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface NotesDao {
    @Insert
    fun addNones(notes:Notes)

    @Insert
    fun signUP(user:User)

    @Query("Select * from User WHERE email=:email AND password=:pass")
    fun loginUser(email:String,pass:String):List<User>

    @Query("Select * from Notes")
    fun getNotesData():LiveData<MutableList<Notes>>

    @Delete
    fun delete(notes: Notes)

    @Update
    fun update(notes: Notes)

    @Transaction
    @Query("SELECT * FROM user")
    fun getMyNotes():MutableList<UserNotes>


   /* @Transaction
    @Query("SELECT * FROM Notes")
    fun getDogsAndOwners(): List<DogAndOwner>*/


}