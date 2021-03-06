package com.enecuumwallet.androidapp.presentation.presenter.signin

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.enecuumwallet.androidapp.R
import com.enecuumwallet.androidapp.application.EnecuumApplication
import com.enecuumwallet.androidapp.events.DonePressed
import com.enecuumwallet.androidapp.navigation.ScreenType
import com.enecuumwallet.androidapp.persistent_data.Constants
import com.enecuumwallet.androidapp.persistent_data.PersistentStorage
import com.enecuumwallet.androidapp.presentation.view.signin.SignInView
import com.enecuumwallet.androidapp.utils.EventBusUtils
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

@InjectViewState
class SignInPresenter : MvpPresenter<SignInView>() {
    private var pin = ""
    fun onPinTextChanged(text: String) {
        pin = text
        viewState.displayPin(text.length)
        viewState.changeButtonState(text.length == Constants.PIN_COUNT)
    }

    fun onNextClick() {
        if(PersistentStorage.getPin().isEmpty()) {
            PersistentStorage.setPin(pin)
        } else {
            if(PersistentStorage.getPin() != pin) {
                EnecuumApplication.cicerone().router.showSystemMessage(
                        EnecuumApplication.applicationContext().getString(R.string.wrong_pin)
                )
                return
            }
        }
        PersistentStorage.setRegistrationFinished()
        EnecuumApplication.navigateToActivity(ScreenType.Main)
    }

    fun onForgotClick() {
        EnecuumApplication.navigateToActivity(ScreenType.ForgotPin)
    }

    fun onDonePressed(): Boolean {
        if(pin.length == Constants.PIN_COUNT) {
            onNextClick()
            return true
        }
        return false
    }

}
