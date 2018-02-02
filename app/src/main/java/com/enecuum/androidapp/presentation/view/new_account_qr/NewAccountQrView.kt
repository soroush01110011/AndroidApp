package com.enecuum.androidapp.presentation.view.new_account_qr

import android.content.Intent
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.enecuum.androidapp.ui.base_ui_primitives.FileOpener

interface NewAccountQrView : FileOpener {
    fun showQrCode(key: String)
}
