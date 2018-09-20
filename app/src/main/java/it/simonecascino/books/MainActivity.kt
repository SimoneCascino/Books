package it.simonecascino.books

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.support.v7.widget.SearchView
import android.view.Menu
import it.simonecascino.books.api.ApiLauncher
import it.simonecascino.books.fragments.BookListFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), BookListFragment.OnBookClickListener{

    private var menu: Menu? = null

    private val handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)

        val bookListFragment = if (savedInstanceState == null) BookListFragment()
            else supportFragmentManager.getFragment(savedInstanceState, BookListFragment.TAG)

        bookListFragment?.let { supportFragmentManager.beginTransaction().replace(R.id.container, it).commit() }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.menu_search, menu)

        this.menu = menu

        val searchView = menu?.findItem(R.id.actionSearch)?.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                handler.removeCallbacksAndMessages(null)

                handler.postDelayed({

                    (supportFragmentManager.findFragmentById(R.id.container) as BookListFragment).setSearched(
                            if(newText.isNullOrBlank())null
                            else newText
                    )

                    if(newText!=null && !newText.isEmpty())
                        ApiLauncher.requestBooks(newText)

                }, 400)

                return true
            }
        })

        return true
    }

    override fun onBookClicked(id: String, color: Int) {

        startActivity(Intent(this, DetailActivity::class.java)
                .putExtra(KEY_ID, id)
                .putExtra(KEY_COLOR, color)
        )

    }

}
