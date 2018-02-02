package com.enecuum.androidapp.utils

import android.content.Intent
import android.net.Uri
import com.enecuum.androidapp.application.EnecuumApplication

/**
 * Created by oleg on 02.02.18.
 */
object SystemIntentManager {
    fun sendText(text: String) {
        val intent = Intent()
        intent.action = Intent.ACTION_SEND
        intent.putExtra(Intent.EXTRA_TEXT, text)
        intent.type = "text/plain"
        EnecuumApplication.applicationContext().startActivity(intent)
    }

    fun openSite(text: String) {
        var realAddress = ""
        realAddress = if(!text.startsWith("http") && !text.startsWith("https"))
            "https://" + text
        else
            text
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(realAddress))
        EnecuumApplication.applicationContext().startActivity(intent)
    }
}