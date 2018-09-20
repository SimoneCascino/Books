package it.simonecascino.books.fragments

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager

import it.simonecascino.books.R
import it.simonecascino.books.adapters.BookAdapter
import it.simonecascino.books.utils.Commons
import it.simonecascino.books.viewModels.BookListModel
import kotlinx.android.synthetic.main.fragment_book_list.*

class BookListFragment : Fragment() {

    private lateinit var imm: InputMethodManager

    private var listener: OnBookClickListener? = null

    companion object {
        const val TAG = "BookListFragment"
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        if(context is OnBookClickListener)
            listener = context
    }

    override fun onDetach() {
        super.onDetach()

        listener = null

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_book_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        imm = activity!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        bookList.layoutManager = LinearLayoutManager(activity)
        bookList.setHasFixedSize(true)

        bookList.addOnScrollListener(object: RecyclerView.OnScrollListener() {

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                if(newState == RecyclerView.SCROLL_STATE_DRAGGING && imm.isAcceptingText)
                    Commons.hideKeyboard(activity!!, bookList)

            }

        })

        setSearched(ViewModelProviders.of(activity!!).get(BookListModel::class.java).searched)

    }

    fun setSearched(searched: String?){

        val model = ViewModelProviders.of(activity!!).get(BookListModel::class.java)

        model.removeObserver(activity!!)

        Log.d("test_searched", "searched is $searched")

        model.searched = searched

        val liveBooks = model.getBooks(activity!!)

        liveBooks?.observe(activity!!, Observer { books ->

            Log.d("test_searched", books?.size.toString())

            if(bookList.adapter == null){

                val adapter = BookAdapter(books)

                bookList.adapter = adapter

                adapter.addCallback { id, title, thumbnail ->

                    listener?.onBookClicked(id, title, thumbnail)

                }

            }

            else (bookList.adapter as BookAdapter).update(books)

        })
    }

    interface OnBookClickListener {

        fun onBookClicked(id: String, title: String, thumbnail: String)

    }

}
