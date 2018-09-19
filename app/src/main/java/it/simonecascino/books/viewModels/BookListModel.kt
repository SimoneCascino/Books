package it.simonecascino.books.viewModels

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import android.content.Context
import android.support.v4.app.FragmentActivity
import android.support.v7.app.AppCompatActivity
import it.simonecascino.books.data.AppDatabase
import it.simonecascino.books.data.entities.Book

class BookListModel: ViewModel(){

    var searched: String? = null

    private var books: LiveData<List<Book>>? = null

    fun getBooks(activity: FragmentActivity): LiveData<List<Book>>?{

        books?.removeObservers(activity)

        if (searched != null)
            books = AppDatabase.getDatabase(activity).bookDao().getBooksByString(searched!!)

        else books = AppDatabase.getDatabase(activity).bookDao().getBooks()



        return books

    }

}