package it.simonecascino.books.fragments


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import it.simonecascino.books.R
import it.simonecascino.books.data.entities.Book
import it.simonecascino.books.viewModels.DetailModel
import kotlinx.android.synthetic.main.fragment_book_detail.*
import java.text.SimpleDateFormat
import java.util.*

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

            textOrGone(book?.subtitle, subtitleView)
            textOrGone(book?.description, descriptionView, descriptionLabel)
            textOrGone(book?.publisher, publisherView, publisherLabel)
            textOrGone(parseDate(book?.publishedDate), dateView, dateLabel)
            textOrGone(if(book?.pageCount == 0)null else book?.pageCount.toString(), pageCountView, pageCountLabel)
            textOrGone(book?.categories, categoryView, categoryLabel)

        })


    }

    private fun textOrGone(text: String?, view: TextView, label: View? = null){

        if(text != null && !text.isEmpty())
            view.text = text

        else {
            view.visibility = View.GONE

            label?.let { label.visibility = View.GONE }
        }

    }

    private fun parseDate(dateStr: String?): String?{

        if(dateStr == null)
            return null

       if(dateStr.count() == 4)
           return dateStr

        else{

           val sdfFormat1 = SimpleDateFormat("yyyy-MM", Locale.getDefault())
           val sdfISO8601 = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
           val sdfFormat2 = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX", Locale.getDefault())

           val readablePattern = "MM/yyyy"

           try{

               val date = sdfFormat1.parse(dateStr)

               sdfFormat1.applyPattern(readablePattern)

               return sdfFormat1.format(date)

           }catch (e: Exception){
               e.printStackTrace()
           }

           try{

               val date = sdfFormat2.parse(dateStr)

               sdfFormat2.applyPattern(readablePattern)

               return sdfFormat2.format(date)

           }catch (e: Exception){
               e.printStackTrace()
           }

           try{

               val date = sdfISO8601.parse(dateStr)

               sdfISO8601.applyPattern(readablePattern)

               return sdfISO8601.format(date)

           }catch (e: Exception){
               e.printStackTrace()
           }

       }

        return dateStr

    }

}
