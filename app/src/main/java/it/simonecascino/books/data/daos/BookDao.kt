package it.simonecascino.books.data.daos

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import it.simonecascino.books.data.entities.Book
import it.simonecascino.books.data.entities.SimpleBook

@Dao
interface BookDao{

    @Query("SELECT id, title, subtitle, authors, thumbnail FROM books")
    fun getSimpleBooks(): LiveData<List<SimpleBook>>

    @Query("SELECT id, title, subtitle, authors, thumbnail FROM books Where title like '%'||:searched||'%'")
    fun getSimpleBooksByString(searched: String): LiveData<List<SimpleBook>>

    @Query("SELECT * FROM books WHERE id = :id")
    fun getBookById(id: String): LiveData<Book>

    @Query("SELECT * FROM books WHERE id = :id")
    fun getBookById2(id: String): Book

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertBooks(books: List<Book>): List<Long>

}