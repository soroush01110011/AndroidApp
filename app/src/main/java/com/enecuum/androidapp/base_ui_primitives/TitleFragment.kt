package com.enecuum.androidapp.base_ui_primitives

import android.os.Handler
import android.os.Looper
import com.arellomobile.mvp.MvpAppCompatFragment

/**
 * Created by oleg on 23.01.18.
 */
abstract class TitleFragment : MvpAppCompatFragment() {
    abstract fun getTitle() : String
}