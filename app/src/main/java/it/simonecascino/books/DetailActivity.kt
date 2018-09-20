package it.simonecascino.books

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.MenuItem
import com.bumptech.glide.Glide
import it.simonecascino.books.data.entities.Book
import it.simonecascino.books.fragments.BookDetailFragment
import it.simonecascino.books.viewModels.DetailModel
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val title = intent.getStringExtra(Book.TITLE)
        val id = intent.getStringExtra(Book.ID)
        val thumbnail = intent.getStringExtra(Book.THUMBNAIL)

        this.title = title

        Glide.with(this).load(thumbnail).into(imageView)

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
