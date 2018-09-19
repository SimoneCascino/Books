package it.simonecascino.books.api

import android.arch.lifecycle.LiveData
import androidx.work.Data
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import androidx.work.WorkStatus
import it.simonecascino.books.api.workers.BookListWorker

object ApiLauncher{

    fun requestBooks(word: String): LiveData<WorkStatus>{

        val data = Data.Builder()
                .putString(BookListWorker.KEY_SEARCHED, word)
                .build()

        val bookListWork = OneTimeWorkRequest.Builder(BookListWorker::class.java).setInputData(data).build()
        WorkManager.getInstance().enqueue(bookListWork)

        return WorkManager.getInstance().getStatusById(bookListWork.id)

    }

}