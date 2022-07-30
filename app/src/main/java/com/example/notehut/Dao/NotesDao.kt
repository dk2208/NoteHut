package com.example.notehut.Dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.notehut.Model.Notes

@Dao()
interface NotesDao {

    @Query("SELECT * FROM Notes")  // Custom read query from table
    fun getNotes():LiveData<List<Notes>>  // this fun returns Notes

    @Query("SELECT * FROM Notes WHERE priority = 3 ")  // Custom read query from table for priority high
    fun getHighNotes():LiveData<List<Notes>>

    @Query("SELECT * FROM Notes WHERE priority = 2 ")  // Custom read query from table for priority medium
    fun getMediumNotes():LiveData<List<Notes>>

    @Query("SELECT * FROM Notes WHERE priority = 1 ")  // Custom read query from table for priority low
    fun getLowNotes():LiveData<List<Notes>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNotes(notes: Notes)

    @Query("DELETE FROM Notes WHERE id=:id")
    fun deleteNotes(id:Int)

    @Update
    fun updateNotes(notes:Notes)
}