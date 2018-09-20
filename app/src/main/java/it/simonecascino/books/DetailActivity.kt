package it.simonecascino.books

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.bumptech.glide.Glide
import it.simonecascino.books.viewModels.DetailModel
import kotlinx.android.synthetic.main.activity_detail.*

const val KEY_ID = "id"
const val KEY_COLOR = "color"

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val color = intent.getIntExtra(KEY_COLOR, 0xffffff)
        val id = intent.getStringExtra(KEY_ID)

        val model = ViewModelProviders.of(this).get(DetailModel::class.java)

        model.getBook(this, id)?.observe(this, Observer { book ->

            Glide.with(this).load(book?.thumbnail).into(imageView)

            supportActionBar?.title = book?.title

        })



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


    }

}
