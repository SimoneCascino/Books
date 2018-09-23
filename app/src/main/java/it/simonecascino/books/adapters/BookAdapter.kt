package it.simonecascino.books.adapters

import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.Typeface
import android.support.v7.graphics.Palette
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.text.Spannable
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import it.simonecascino.books.R
import it.simonecascino.books.data.entities.SimpleBook

/**
 *  This adapter shows a list of books. This list is obtained querying a Room database. the database is filled by
 *  a server query, and the data are constantly up to date thanks to Livedata and Observer
 */
class BookAdapter(var books: List<SimpleBook>?, var searched: String?): RecyclerView.Adapter<BookAdapter.BookHolder>() {

    //In order to improve UI, I extract a color of the thumbnail using Palette library. To prevent useless operations
    //I cache colors here
    val colorCache = HashMap<Int, Int>()

    //lambda used as callback
    lateinit var callback: (String, String, String, String) -> (Unit)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookHolder {
        return BookHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_book, parent, false))
    }

    override fun getItemCount(): Int {
        return books?.count() ?: 0
    }

    override fun onBindViewHolder(holder: BookHolder, position: Int) {

        //I can assume that books list is not null here, because this method is called after getItemCount
        val book = books!![position]

        val glideRequest = Glide.with(holder.itemView)
                .asBitmap()
                .load(book.thumbnail)


        //If my color cache contains already a color in this position, I skip this part
        if(!colorCache.containsKey(position)) {

            glideRequest.addListener(object : RequestListener<Bitmap> {
                override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Bitmap>?, isFirstResource: Boolean): Boolean {
                    return false
                }

                override fun onResourceReady(resource: Bitmap?, model: Any?, target: Target<Bitmap>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {

                    resource?.let { bitmap ->

                        Palette.Builder(bitmap)
                                .addTarget(android.support.v7.graphics.Target.LIGHT_VIBRANT)
                                .generate { palette ->

                                    val color = palette?.getLightVibrantColor(0xffffff)
                                            ?: 0xffffff

                                    colorCache[holder.adapterPosition] = color

                                    holder.imageView.setBackgroundColor(color)
                                }
                    }

                    return false
                }

            })

        }

        else holder.imageView.setBackgroundColor(colorCache.get(position)!!)

        glideRequest.into(holder.imageView)

        checkQueryInText(book.title, holder.titleView)

        if(book.subtitle!=null) {
            holder.subtitleView.visibility = View.VISIBLE

            checkQueryInText(book.subtitle, holder.subtitleView)

        }

        else holder.subtitleView.visibility = View.GONE

        holder.authorsView.text = book.authors

    }

    //highlight the query text
    private fun checkQueryInText(text: String, textView: TextView){

        searched?.let { searched ->

            val index = text.toLowerCase().indexOf(searched.toLowerCase())

            if(index!=-1){

                val str = SpannableString(text)
                str.setSpan(StyleSpan(Typeface.BOLD), index, index + searched.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                str.setSpan(ForegroundColorSpan(Color.RED), index, index + searched.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

                textView.setText(str)

            }

            return

        }

        textView.text = text

    }

    fun update(books: List<SimpleBook>?, searched: String?){
        this.books = books
        this.searched = searched
        colorCache.clear()
        notifyDataSetChanged()
    }

    fun addCallback(callback: (String, String, String, String) -> (Unit)){
        this.callback = callback
    }

    inner class BookHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        val imageView = itemView.findViewById<ImageView>(R.id.imageView)
        val titleView = itemView.findViewById<TextView>(R.id.AuthorsView)
        val subtitleView = itemView.findViewById<TextView>(R.id.subtitleView)
        val authorsView = itemView.findViewById<TextView>(R.id.authorsView)
        val rootView = itemView.findViewById<CardView>(R.id.rootLayout)

        init {

            rootView.setOnClickListener {

                val book = books?.get(adapterPosition)!!

                if(::callback.isInitialized)
                    callback(book.id, book.title, book.thumbnail, book.authors)
            }

        }
    }

}