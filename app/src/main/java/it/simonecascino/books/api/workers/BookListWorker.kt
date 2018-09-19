package it.simonecascino.books.api.workers

import android.net.Uri
import android.util.Log
import androidx.work.Data
import androidx.work.Worker
import it.simonecascino.books.api.BASE_URL
import it.simonecascino.books.api.ConnectionUtils
import it.simonecascino.books.api.RESULT_CODE
import it.simonecascino.books.api.TAG
import it.simonecascino.books.utils.manageResult
import it.simonecascino.books.utils.readAsString
import java.net.URL

class BookListWorker: Worker() {

    companion object {

        const val KEY_SEARCHED = "searched"

    }

    override fun doWork(): Result {

        val searched = inputData.getString(KEY_SEARCHED)

        val url = URL(Uri.parse(BASE_URL).buildUpon().appendQueryParameter("q", searched).build().toString())

        return ConnectionUtils.buildConnection(url).manageResult { success, responseCode, inputStream ->

            val result = inputStream.readAsString()

            Log.d(TAG, "responseCode -> $responseCode")
            Log.d(TAG, "result -> $result")

            if(success) {


                Result.SUCCESS

            }

            else {
                outputData = Data.Builder().putInt(RESULT_CODE, responseCode).build()
                Result.FAILURE
            }
        }

    }

}