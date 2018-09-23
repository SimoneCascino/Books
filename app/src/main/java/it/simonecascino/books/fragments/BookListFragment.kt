package it.simonecascino.books.fragments

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager

import it.simonecascino.books.R
import it.simonecascino.books.adapters.BookAdapter
import it.simonecascino.books.custom.ItemOffsetDecoration
import it.simonecascino.books.utils.Commons
import it.simonecascino.books.viewModels.BookListModel
import kotlinx.android.synthetic.main.fragment_book_list.*

/**
 * this fragment manage and display a list of books. At the very first usage, the list is empty. Start a search for see books.
 * If the search field is empty, you'll see all books saved in the database.
 */
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

        //I use InputMethodManager for hide keyboard when the list is dragged.
        imm = activity!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        //choose a different LayoutManager depending from device orientation
        bookList.layoutManager = if(Commons.isInLandscape(activity!!))StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            else LinearLayoutManager(activity)

        bookList.setHasFixedSize(true)

        //this help with spacing between items
        bookList.addItemDecoration(ItemOffsetDecoration(activity!!, R.dimen.standard_margin_padding_reduced))

        bookList.addOnScrollListener(object: RecyclerView.OnScrollListener() {

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                //hide the keyboard when drag the list
                if(newState == RecyclerView.SCROLL_STATE_DRAGGING && imm.isAcceptingText)
                    Commons.hideKeyboard(activity!!, bookList)

            }

        })

        setSearched(ViewModelProviders.of(activity!!).get(BookListModel::class.java).searched)

    }

    fun setSearched(searched: String?){

        //ViewModel manage data for this fragment
        val model = ViewModelProviders.of(activity!!).get(BookListModel::class.java)

        //I remove and attach (later) a new observer, because this method is called when the search text change
        model.removeObserver(activity!!)

        Log.d("test_searched", "searched is $searched")

        model.searched = searched

        val liveBooks = model.getBooks(activity!!)

        //obtain the list of books
        liveBooks?.observe(activity!!, Observer { books ->

            Log.d("test_searched", books?.size.toString())

            if(bookList.adapter == null){
                val adapter = BookAdapter(books, searched)

                bookList.adapter = adapter

                adapter.addCallback { id, title, thumbnail, authors ->

                    listener?.onBookClicked(id, title, thumbnail, authors)

                }

            }

            else (bookList.adapter as BookAdapter).update(books, searched)

        })
    }


    //callback for handling click on the list. I could use lambdas, but in this case I prefer implement
    //this interface on the parent activity
    interface OnBookClickListener {

        fun onBookClicked(id: String, title: String, thumbnail: String, authors: String)

    }

}
