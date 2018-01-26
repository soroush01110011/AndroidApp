package com.enecuum.androidapp.utils

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText

/**
 * Created by oleg on 23.01.18.
 */
object KeyboardUtils {
    fun showKeyboard(context: Context?, editText: EditText) {
        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            editText.requestFocus()
            val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT)
        }, 200)
    }

    fun hideKeyboard(context: Context?, view: View?) {
        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view?.windowToken, 0)
        }, 200)
    }

    fun createMoveCursorToEndFocusListener() : View.OnFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
        if(v != null && hasFocus) {
            val editText = v as EditText
            if(editText.text.isNotEmpty()) {
                editText.post({
                    editText.setSelection(editText.length())
                })
            }
        }
    }
}