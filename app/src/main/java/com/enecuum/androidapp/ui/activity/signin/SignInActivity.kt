package com.enecuum.androidapp.ui.activity.signin

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.view.inputmethod.EditorInfo
import com.enecuum.androidapp.ui.base_ui_primitives.BackActivity

import com.arellomobile.mvp.presenter.InjectPresenter
import com.enecuum.androidapp.R
import com.enecuum.androidapp.presentation.view.signin.SignInView
import com.enecuum.androidapp.presentation.presenter.signin.SignInPresenter
import com.enecuum.androidapp.utils.KeyboardUtils
import com.enecuum.androidapp.utils.PinUtils
import com.enecuum.androidapp.utils.SimpleTextWatcher
import kotlinx.android.synthetic.main.activity_sign_in.*


class SignInActivity : BackActivity(), SignInView {
    companion object {
        const val TAG = "SignInActivity"
        fun getIntent(context: Context): Intent = Intent(context, SignInActivity::class.java)
    }

    @InjectPresenter
    lateinit var presenter: SignInPresenter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        KeyboardUtils.showKeyboard(this, pin1)
        pin1.addTextChangedListener(object : SimpleTextWatcher() {
            override fun afterTextChanged(s: Editable?) {
                presenter.onPinTextChanged(pin1.text.toString())
            }
        })
        next.setOnClickListener {
            presenter.onNextClick()
        }
        forgot.setOnClickListener {
            presenter.onForgotClick()
        }
        pin1.setOnEditorActionListener { v, actionId, event ->
            if(actionId == EditorInfo.IME_ACTION_DONE) {
                return@setOnEditorActionListener presenter.onDonePressed()
            }
            true
        }
    }

    override fun displayPin(length: Int) {
        PinUtils.changePinState(pin1_1, pin1_2, pin1_3, pin1_4, length)
    }

    override fun changeButtonState(enable: Boolean) {
        next.isEnabled = enable
    }
}
