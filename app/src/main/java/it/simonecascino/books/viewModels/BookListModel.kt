package it.simonecascino.books.viewModels

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import android.support.v4.app.FragmentActivity
import android.util.Log
import it.simonecascino.books.data.AppDatabase
import it.simonecascino.books.data.entities.Book

class BookListModel: ViewModel(){

    private var hasChanged = false

    var searched: String? = null
    set(value) {

        if(field != value)
            hasChanged = true

        field = value

    }

    private var books: LiveData<List<Book>>? = null

    fun removeObserver(activity: FragmentActivity){
        books?.removeObservers(activity)
    }

    fun getBooks(activity: FragmentActivity): LiveData<List<Book>>?{

        if(books == null || (searched == null && hasChanged)) {
            books = AppDatabase.getDatabase(activity).bookDao().getBooks()
            Log.d("test_search", "base")
        }

        else if(searched != null && hasChanged) {
            books = AppDatabase.getDatabase(activity).bookDao().getBooksByString(searched!!)
            Log.d("test_search", "filter: $searched")
        }

        hasChanged = false

        return books

    }

}