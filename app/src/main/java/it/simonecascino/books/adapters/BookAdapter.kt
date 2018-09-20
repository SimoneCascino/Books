package it.simonecascino.books.adapters

import android.graphics.Bitmap
import android.support.v7.graphics.Palette
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
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

class BookAdapter(var books: List<SimpleBook>?): RecyclerView.Adapter<BookAdapter.BookHolder>() {

    val colorCache = HashMap<Int, Int>()

    lateinit var callback: (String, Int) -> (Unit)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookHolder {
        return BookHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_book, parent, false))
    }

    override fun getItemCount(): Int {
        return books?.count() ?: 0
    }

    override fun onBindViewHolder(holder: BookHolder, position: Int) {

        val book = books!![position]

        val glideRequest = Glide.with(holder.itemView)
                .asBitmap()
                .load(book.thumbnail)

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

        holder.titleView.text = book.title

        if(book.subtitle!=null) {
            holder.subtitleView.visibility = View.VISIBLE
            holder.subtitleView.text = book.subtitle
        }

        else holder.subtitleView.visibility = View.GONE

        holder.authorsView.text = book.authors

    }

    fun update(books: List<SimpleBook>?){
        this.books = books
        colorCache.clear()
        notifyDataSetChanged()
    }

    fun addCallback(callback: (String, Int) -> (Unit)){
        this.callback = callback
    }

    inner class BookHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        val imageView = itemView.findViewById<ImageView>(R.id.imageView)
        val titleView = itemView.findViewById<TextView>(R.id.titleView)
        val subtitleView = itemView.findViewById<TextView>(R.id.subtitleView)
        val authorsView = itemView.findViewById<TextView>(R.id.authorsView)
        val rootView = itemView.findViewById<CardView>(R.id.rootLayout)

        init {

            rootView.setOnClickListener {

                if(::callback.isInitialized)
                    callback(books!![adapterPosition].id,
                            colorCache.get(adapterPosition)!!)
            }

        }
    }

}