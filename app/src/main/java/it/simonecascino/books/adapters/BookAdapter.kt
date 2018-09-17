package it.simonecascino.books.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import it.simonecascino.books.R
import it.simonecascino.books.data.entities.Book

class BookAdapter(var books: List<Book>?): RecyclerView.Adapter<BookAdapter.BookHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookHolder {
        return BookHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_book, parent, false))
    }

    override fun getItemCount(): Int {
        return books?.count() ?: 0
    }

    override fun onBindViewHolder(holder: BookHolder, position: Int) {

        var book = books!![position]



    }

    class BookHolder(itemView: View): RecyclerView.ViewHolder(itemView){



    }

}