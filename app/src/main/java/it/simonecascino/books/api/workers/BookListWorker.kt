package it.simonecascino.books.api.workers

import android.net.Uri
import android.util.Log
import androidx.work.Data
import androidx.work.Worker
import it.simonecascino.books.api.*
import it.simonecascino.books.utils.manageResult
import it.simonecascino.books.utils.readAsString
import java.net.URL

/**
 * Inside this worker there is the server call for obtain books
 */

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

                JsonHelper.Parser.parseBooks(applicationContext, result)
                Result.SUCCESS

            }

            else {
                outputData = Data.Builder().putInt(RESULT_CODE, responseCode).build()
                Result.FAILURE
            }
        }

    }

}