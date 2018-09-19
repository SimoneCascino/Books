package it.simonecascino.books.data.daos

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import it.simonecascino.books.data.entities.Book

@Dao
interface BookDao{

    @Query("SELECT * FROM books")
    fun getBooks(): LiveData<List<Book>>

    @Query("SELECT * FROM books Where title like '%'||:searched||'%'")
    fun getBooksByString(searched: String): LiveData<List<Book>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertBooks(books: List<Book>): List<Long>

}