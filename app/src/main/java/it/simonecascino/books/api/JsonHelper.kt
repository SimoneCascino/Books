package it.simonecascino.books.api

import android.content.Context
import android.util.Log
import it.simonecascino.books.data.AppDatabase
import it.simonecascino.books.data.entities.Book
import org.json.JSONObject

object JsonHelper{

    object Parser{

        fun parseBooks(context: Context, result: String){

            val json = JSONObject(result).getJSONArray("items")
            val length = json.length()

            val books = ArrayList<Book>(length)

            for(i in 0 until length)
                books.add(Converter.jsonToBook(json.getJSONObject(i)))

            val insertResult = AppDatabase.getDatabase(context).bookDao().insertBooks(books)

            for (test in insertResult)
                Log.d("test_insert", test.toString())

        }

    }

    private object Converter{

        fun jsonToBook(jBook: JSONObject): Book{

            val jInfo = jBook.getJSONObject(Book.VOLUME_INFO)

            val jAuthors = jInfo.optJSONArray(Book.AUTHORS)
            val authorsLength = jAuthors?.length() ?: 0

            val authorsBuilder = StringBuilder()

            for(i in 0 until authorsLength){

                authorsBuilder.append(jAuthors.getString(i))

                if(i < authorsLength - 1)
                    authorsBuilder.append(",")

            }

            val jCategories = jInfo.optJSONArray(Book.CATEGORIES)
            val categoriesLength = jCategories?.length() ?: 0

            val categoriesBuilder = StringBuilder()

            for(i in 0 until categoriesLength){

                categoriesBuilder.append(jCategories.getString(i))

                if(i < categoriesLength-1)
                    categoriesBuilder.append(", ")

            }


            val jImages = jInfo.getJSONObject(Book.IMAGE_LINKS)

            val id = jBook.getString(Book.ID)
            val title = jInfo.getString(Book.TITLE)
            val subtitle = jInfo.optString(Book.SUBTITLE, null)
            val authors = authorsBuilder.toString()
            val publisher = jInfo.optString(Book.PUBLISHER)
            val publishedDate = jInfo.optString(Book.PUBLISHED_DATE)
            val description = jInfo.optString(Book.DESCRIPTION)
            val pageCount = jInfo.optInt(Book.PAGE_COUNT)
            val smallThumbnail = jImages.getString(Book.SMALL_THUMBNAIL)
            val thumbnail = jImages.getString(Book.THUMBNAIL)
            val categories = categoriesBuilder.toString()

            return Book(id, title, subtitle, authors, publisher, publishedDate, description,
                    pageCount, categories, smallThumbnail, thumbnail)

        }

    }

}