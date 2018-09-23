package it.simonecascino.books.utils

import androidx.work.Data
import androidx.work.Worker
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.net.HttpURLConnection
import java.nio.charset.Charset
import java.security.MessageDigest
import java.util.*

fun InputStream.readAsString(): String{

    val result = ByteArrayOutputStream()

    val buffer = ByteArray(1024)

    var length = 0

    while ({length = read(buffer);length}() != -1){
        result.write(buffer, 0, length)
    }

    close()
    result.close()

    return result.toString("UTF-8")

}

fun HttpURLConnection.manageResult(manage: (Boolean, Int, InputStream)-> Worker.Result): Worker.Result{

    val responseCode = responseCode

    //Log.d(TAG, "${Uri.parse(url.toString()).lastPathSegment} responseCode -> $responseCode")

    val returnType =  when(responseCode){
        in HttpURLConnection.HTTP_OK..HttpURLConnection.HTTP_ACCEPTED -> manage(true, responseCode, inputStream)
        else -> manage(false, responseCode, errorStream)
    }

    disconnect()

    return returnType

}
