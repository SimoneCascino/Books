package it.simonecascino.books.api

import android.net.Uri
import android.util.Log
import java.io.BufferedWriter
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL

internal const val TAG = "connection"
internal const val BODY = "body"

internal const val POST = "POST"
internal const val GET = "GET"
internal const val PUT = "PUT"
internal const val DELETE = "DELETE"

internal const val RESULT_CODE = "result_code"

private const val BASE_URL = "http://192.168.54.177:4000/api"

object ConnectionUtils{

    fun buildConnection(path: String, headers: ArrayList<Pair<String, String>>? = null, method: String = POST, body: String? = null): HttpURLConnection{

        val url = URL(Uri.parse(BASE_URL).buildUpon().appendPath(path).build().toString())
        val connection = url.openConnection() as HttpURLConnection

        connection.requestMethod = method
        connection.connectTimeout = 10000

        Log.d(TAG, url.toString())

        if (headers != null) {

            for (header in headers){

                connection.setRequestProperty(header.first,
                        header.second)

                Log.d(TAG, "$path header -> $header")

            }

        }

        if(method != GET && body != null){

            Log.d(TAG, "$path body -> $body")

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