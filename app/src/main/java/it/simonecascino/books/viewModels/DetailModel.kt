package it.simonecascino.books.viewModels

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import android.content.Context
import it.simonecascino.books.data.AppDatabase
import it.simonecascino.books.data.entities.Book

class DetailModel(): ViewModel(){

    private var book: LiveData<Book>? = null

    fun getBook(context: Context, id: String): LiveData<Book>?{

        if(book == null)
            book = AppDatabase.getDatabase(context).bookDao().getBookById(id)

        return book
    }

}