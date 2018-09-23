package it.simonecascino.books

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import it.simonecascino.books.data.entities.Book
import it.simonecascino.books.fragments.BookDetailFragment
import kotlinx.android.synthetic.main.activity_detail.*

/**
 * This activity handle the detail of a single book
 */
class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        supportPostponeEnterTransition()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val title = intent.getStringExtra(Book.TITLE)
        val id = intent.getStringExtra(Book.ID)
        val thumbnail = intent.getStringExtra(Book.THUMBNAIL)
        val authors = intent.getStringExtra(Book.AUTHORS)

        this.title = title

        authorsView.text = authors
        titleView.text = title

        Glide.with(this).load(thumbnail).listener(object: RequestListener<Drawable>{
            override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                supportStartPostponedEnterTransition()
                return false
            }

            override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                supportStartPostponedEnterTransition()
                return false
            }

        }).into(imageView)

        val detailFragment = if(savedInstanceState==null)BookDetailFragment.newInstance(id)
            else supportFragmentManager.getFragment(savedInstanceState, BookDetailFragment.TAG)

        supportFragmentManager.beginTransaction().replace(R.id.container, detailFragment!!).commit()

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        if(item?.itemId ?: 0 == android.R.id.home){
            onBackPressed()
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)

        supportFragmentManager.findFragmentById(R.id.container)?.let { fragment ->
            supportFragmentManager.putFragment(outState!!, BookDetailFragment.TAG, fragment)
        }

    }

}