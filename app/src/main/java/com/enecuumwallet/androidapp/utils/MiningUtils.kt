package com.enecuumwallet.androidapp.utils

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.enecuumwallet.androidapp.R
import com.enecuumwallet.androidapp.application.EnecuumApplication
import com.enecuumwallet.androidapp.navigation.ScreenType
import com.enecuumwallet.androidapp.persistent_data.PersistentStorage
import com.enecuumwallet.androidapp.presentation.view.BaseMiningView

/**
 * Created by oleg on 26.01.18.
 */
object MiningUtils {

    private fun handleMiningButtonClick(viewState: BaseMiningView) {
        val prevValue = PersistentStorage.isMiningInProgress()
        val nextValue = !prevValue
        PersistentStorage.setMiningInProgress(nextValue)
        viewState.setupMiningPanel()
    }

    fun refreshMiningPanel(miningIcon: ImageView, miningStatus: TextView) {
        if(PersistentStorage.isMiningInProgress()) {
            miningIcon.setImageResource(R.drawable.stop)
            miningStatus.setText(R.string.mining_is_working)
        } else {
            miningIcon.setImageResource(R.drawable.play)
            miningStatus.setText(R.string.mining_has_ended)
        }
    }

    fun setupMiningPanel(miningStatusChanger: View, miningPanel: View, viewState: BaseMiningView) {
        viewState.setupMiningPanel()
        miningStatusChanger.setOnClickListener {
            handleMiningButtonClick(viewState)
        }
        miningPanel.setOnClickListener {
            EnecuumApplication.navigateToActivity(ScreenType.Mining)
        }
    }
}