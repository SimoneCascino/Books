package it.simonecascino.books.viewModels

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import android.content.Context
import it.simonecascino.books.data.AppDatabase
import it.simonecascino.books.data.entities.Book

class BookListModel: ViewModel(){


    private var books: LiveData<List<Book>>? = null

    fun getBooks(context: Context, searched: String? = null): LiveData<List<Book>>?{

        if (searched != null)
            books = AppDatabase.getDatabase(context).bookDao().getBooksByString(searched)

        else if (books == null)
            books = AppDatabase.getDatabase(context).bookDao().getBooks()

        return books

    }

}