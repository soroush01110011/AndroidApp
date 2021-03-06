package com.enecuumwallet.androidapp.presentation.presenter.new_account

import android.content.DialogInterface
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.enecuumwallet.androidapp.R
import com.enecuumwallet.androidapp.application.EnecuumApplication
import com.enecuumwallet.androidapp.events.*
import com.enecuumwallet.androidapp.navigation.ScreenType
import com.enecuumwallet.androidapp.presentation.view.new_account.NewAccountView
import com.enecuumwallet.androidapp.persistent_data.PersistentStorage
import com.enecuumwallet.androidapp.utils.EventBusUtils
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

@InjectViewState
class NewAccountPresenter : MvpPresenter<NewAccountView>(), DialogInterface.OnClickListener {

    private var pin : String = ""
    private var firstPin : String = ""
    private var isKeyBackedUp = false
    private var isSeedBackedUp = false
    private var currentPage = 0

    override fun onClick(dialog: DialogInterface?, which: Int) {
        when(which) {
            DialogInterface.BUTTON_POSITIVE -> {
                if(currentPage < 2) {
                    viewState.moveNext()
                    currentPage++
                }
                else
                    openNextScreen()
            }
        }
    }

    fun onCreate() {
        EventBusUtils.register(this)
        currentPage = 0
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBusUtils.unregister(this)
    }


    fun onNextClick(currentScreen : Int) {
        when(currentScreen) {
            0 -> {
                firstPin = pin
                viewState.moveNext()
            }
            1 -> {
                if(firstPin != pin) {
                    EnecuumApplication.cicerone().router.showSystemMessage(
                            EnecuumApplication.applicationContext().getString(R.string.pin_not_equals))
                    return
                } else {
                    PersistentStorage.setPin(pin)
                    openNextScreen()
                    //viewState.moveNext()
                }
            }
            2 -> {
                if (isKeyBackedUp) {
                    viewState.moveNext()
                    openNextScreen()
                } else {
                    viewState.displaySkipDialog()
                    return
                }
            }


            /*3 -> {
                if (isSeedBackedUp) {
                    openNextScreen()
                } else {
                    viewState.displaySkipDialog()
                    return
                }
            }*/

        }
        currentPage++
    }

    private fun openNextScreen() {
        PersistentStorage.setPin(pin)
        EnecuumApplication.navigateToActivity(ScreenType.RegistrationFinished)
    }

    @Subscribe
    fun onChangeButtonState(event: ChangeButtonState) {
        viewState.changeButtonState(event.enable)
    }

    @Subscribe
    fun onPinBackupFinished(event: PinBackupFinished) {
        isKeyBackedUp = true
    }

    @Subscribe
    fun onSeedBackupFinished(event: SeedBackupFinished) {
        isSeedBackedUp = true
    }

    @Subscribe
    fun onDonePressed(event: DonePressed) {
        onNextClick(currentPage)
    }

    @Subscribe
    fun onPinChanged(event: PinChanged) {
        pin = event.value
    }

    fun onBackPressed() {
        PersistentStorage.deleteAddress()
        PersistentStorage.deletePin()
    }
}
