package it.simonecascino.books

import android.arch.lifecycle.Observer
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.work.State
import it.simonecascino.books.api.ApiLauncher
import it.simonecascino.books.data.entities.Book
import it.simonecascino.books.fragments.BookListFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), BookListFragment.OnBookClickListener{

    private val handler = Handler()
    private var searchViewItem: MenuItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)

        //thanks to this FloatingActionButton, is possible to show keyboard and start typing in a
        //more convenient way to click on the magnifying glass on the right top corner
        fab.setOnClickListener {

            //when the keyboard rise up, I expand the AppBarLayout, in order to show the text field
            appBarLayout.setExpanded(true)

            //show keyboard and give focus to the SearchView
            searchViewItem?.collapseActionView()
            searchViewItem?.expandActionView()

        }

        val bookListFragment = if (savedInstanceState == null) BookListFragment()
            else supportFragmentManager.getFragment(savedInstanceState, BookListFragment.TAG)

        bookListFragment?.let { supportFragmentManager.beginTransaction().replace(R.id.container, it).commit() }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.menu_search, menu)

        searchViewItem = menu?.findItem(R.id.actionSearch)

        (searchViewItem?.actionView as SearchView).setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            //I prefer to use directly the following methods, in order to search while typing
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                //With this handler I apply a lag of 0.4 seconds, in order to prevent some useless server calls
                handler.removeCallbacksAndMessages(null)

                handler.postDelayed({

                    manageQuery(newText)

                }, 400)

                return true
            }
        })

        return true
    }

    override fun onBookClicked(id: String, title: String, thumbnail: String, authors: String) {

        startActivity(Intent(this, DetailActivity::class.java)
                .putExtra(Book.ID, id)
                .putExtra(Book.TITLE, title)
                .putExtra(Book.THUMBNAIL, thumbnail)
                .putExtra(Book.AUTHORS, authors)
        )

    }

    //in this method I perform the server calls, also, thanks to the database, if I found some books that match
    //the query, I'll show them immediately
    private fun manageQuery(newText: String?){

        (supportFragmentManager.findFragmentById(R.id.container) as BookListFragment).setSearched(
                if(newText.isNullOrBlank())null
                else newText
        )

        if(newText!=null && !newText.isEmpty()) {

            ApiLauncher.requestBooks(newText).observe(this, Observer { workStatus ->

                //connection (or others) error handling
                if (workStatus?.state?.isFinished == true && workStatus.state != State.SUCCEEDED)
                    handlingNetworkError()

            })

        }

    }

    //A very basic error handling. When you click on the OK button of the snackbar,
    //the query text of the searchview is changing, in order to simulate a new typing action (with the same text)
    private fun handlingNetworkError(){
        Snackbar.make(container, R.string.error_api, Snackbar.LENGTH_INDEFINITE).setAction("Ok", View.OnClickListener {

            val searchView = searchViewItem?.actionView as SearchView

            val query = searchView.query

            searchView.setQuery("", false)
            searchView.setQuery(query, false)

        }).show()
    }
}
