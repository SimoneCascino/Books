package it.simonecascino.books.data

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import it.simonecascino.books.data.daos.BookDao
import it.simonecascino.books.data.entities.Book

@Database(entities = [Book::class], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase(){

    abstract fun bookDao(): BookDao

    companion object {

        private var instance: AppDatabase? = null

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, "main_db").build()
        }

        fun getDatabase(context: Context): AppDatabase{

            return instance ?: buildDatabase(context).also {
                instance = it
            }

        }

    }

}