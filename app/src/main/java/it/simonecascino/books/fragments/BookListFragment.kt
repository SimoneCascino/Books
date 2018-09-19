package it.simonecascino.books.fragments

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import it.simonecascino.books.R
import it.simonecascino.books.adapters.BookAdapter
import it.simonecascino.books.viewModels.BookListModel
import kotlinx.android.synthetic.main.fragment_book_list.*

class BookListFragment : Fragment() {

    companion object {
        const val TAG = "BookListFragment"
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
    }

    override fun onDetach() {
        super.onDetach()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_book_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bookList.layoutManager = LinearLayoutManager(activity)
        bookList.setHasFixedSize(true)

        configList(ViewModelProviders.of(activity!!).get(BookListModel::class.java).searched)

    }

    fun configList(searched: String? = null){

        Log.d("test_searched", "searched is $searched")

        val model = ViewModelProviders.of(activity!!).get(BookListModel::class.java)

        if(model.searched != searched)
            model.searched = searched


        model.getBooks(activity!!)?.observe(this, Observer { books ->

            if (bookList.adapter == null) {

                val adapter = BookAdapter(books)

                bookList.adapter = adapter

            } else (bookList.adapter as BookAdapter).update(books)

        })

    }

}
