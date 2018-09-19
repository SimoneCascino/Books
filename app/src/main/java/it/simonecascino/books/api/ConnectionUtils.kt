package it.simonecascino.books.api

import android.util.Log
import java.io.BufferedWriter
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL

internal const  val BASE_URL = "https://www.googleapis.com/books/v1/volumes" //?q=quilting

internal const val TAG = "connection"
internal const val BODY = "body"

internal const val POST = "POST"
internal const val GET = "GET"
internal const val PUT = "PUT"
internal const val DELETE = "DELETE"

internal const val RESULT_CODE = "result_code"

object ConnectionUtils{

    fun buildConnection(url: URL, headers: ArrayList<Pair<String, String>>? = null, method: String = GET, body: String? = null): HttpURLConnection{

        val connection = url.openConnection() as HttpURLConnection

        connection.requestMethod = method
        connection.connectTimeout = 10000

        Log.d(TAG, url.toString())

        if (headers != null) {

            for (header in headers){

                connection.setRequestProperty(header.first,
                        header.second)

            }

        }

        if(method != GET && body != null){

            connection.doOutput = true
            connection.setChunkedStreamingMode(0)

            val outputStream = connection.outputStream
            val bufferedWriter = BufferedWriter(OutputStreamWriter(outputStream, "UTF-8"))

            bufferedWriter.write(body)
            bufferedWriter.flush()
            bufferedWriter.close()
            outputStream.close()

        }

        connection.connect()

        return connection

    }

}