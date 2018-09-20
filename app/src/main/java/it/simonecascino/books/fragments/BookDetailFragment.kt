package it.simonecascino.books.fragments


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import it.simonecascino.books.R
import it.simonecascino.books.data.entities.Book
import it.simonecascino.books.viewModels.DetailModel
import kotlinx.android.synthetic.main.fragment_book_detail.*

class BookDetailFragment : Fragment() {

    companion object{

        const val TAG = "BookDetailFragment"

        fun newInstance(id: String): BookDetailFragment{

            val fragment = BookDetailFragment()

            val args = Bundle(1)
            args.putString(Book.ID, id)

            fragment.arguments = args

            return fragment

        }

    }



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_book_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val model = ViewModelProviders.of(activity!!).get(DetailModel::class.java)

        model.getBook(activity!!, arguments!!.getString(Book.ID)!!)?.observe(activity!!, Observer{ book ->

            book?.subtitle?.let {
                subtitleView.text = it
            }



        })


    }

}
