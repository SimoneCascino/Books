package it.simonecascino.books.utils

import android.content.Context
import android.content.Context.INPUT_METHOD_SERVICE
import android.content.res.Configuration
import android.support.v4.content.ContextCompat.getSystemService
import android.view.View
import android.view.inputmethod.InputMethodManager


object Commons{

    fun hideKeyboard(context: Context, view: View) {
        val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0)
    }

    fun showKeyboard(context: Context, view: View) {
        val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
    }

    fun isKeyboardVisible(context: Context): Boolean{
        val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        return inputMethodManager.isAcceptingText
    }

    fun isInLandscape(context: Context): Boolean{
        return context.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
    }

}
