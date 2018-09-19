package it.simonecascino.books.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import it.simonecascino.books.R
import it.simonecascino.books.data.entities.Book

class BookAdapter(var books: List<Book>?): RecyclerView.Adapter<BookAdapter.BookHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookHolder {
        return BookHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_book2, parent, false))
    }

    override fun getItemCount(): Int {
        return books?.count() ?: 0
    }

    override fun onBindViewHolder(holder: BookHolder, position: Int) {

        val book = books!![position]

        Glide.with(holder.itemView).load(book.thumbnail).apply(RequestOptions().centerCrop()).into(holder.imageView)

        holder.titleView.text = book.title

    }

    fun update(books: List<Book>?){
        this.books = books
        notifyDataSetChanged()
    }


    class BookHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        val imageView = itemView.findViewById<ImageView>(R.id.imageView)
        val titleView = itemView.findViewById<TextView>(R.id.titleView)

    }

}