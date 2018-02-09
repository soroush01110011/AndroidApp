package com.enecuum.androidapp.presentation.presenter.transaction_details

import android.os.Bundle
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.enecuum.androidapp.application.EnecuumApplication
import com.enecuum.androidapp.events.BackStackIncreased
import com.enecuum.androidapp.models.Transaction
import com.enecuum.androidapp.models.TransactionType
import com.enecuum.androidapp.navigation.FragmentType
import com.enecuum.androidapp.presentation.view.transaction_details.TransactionDetailsView
import com.enecuum.androidapp.ui.activity.transaction_details.TransactionDetailsActivity.Companion.TRANSACTION
import com.enecuum.androidapp.utils.EventBusUtils
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

@InjectViewState
class TransactionDetailsPresenter : MvpPresenter<TransactionDetailsView>() {
    private var backStackCount = 0
    private var transaction: Transaction? = null

    fun onCreate(extras: Bundle) {
        transaction = extras.getSerializable(TRANSACTION) as Transaction
        when(transaction?.transactionType) {
            TransactionType.Send -> {
                EnecuumApplication.fragmentCicerone().router.navigateTo(FragmentType.SendOptions.toString(), extras)
            }
            TransactionType.Receive -> {
                EnecuumApplication.fragmentCicerone().router.navigateTo(FragmentType.ReceiveByAddress.toString(), extras)
            }
        }
        EventBusUtils.register(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBusUtils.unregister(this)
    }

    @Subscribe
    fun onBackStackIncreased(event: BackStackIncreased) {
        backStackCount++
    }

    fun onBackPressed() {
        if(backStackCount == 0)
            EnecuumApplication.cicerone().router.exit()
        else {
            backStackCount--
            EnecuumApplication.fragmentCicerone().router.exit()
        }
    }

}
