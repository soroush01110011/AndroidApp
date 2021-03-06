package com.enecuumwallet.androidapp.ui.fragment.settings_main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.enecuumwallet.androidapp.R
import com.enecuumwallet.androidapp.presentation.view.settings_main.SettingsMainView
import com.enecuumwallet.androidapp.presentation.presenter.settings_main.SettingsMainPresenter

import com.arellomobile.mvp.presenter.InjectPresenter
import com.enecuumwallet.androidapp.ui.base_ui_primitives.NoBackFragment
import com.enecuumwallet.androidapp.utils.SettingsInfoUtils
import kotlinx.android.synthetic.main.fragment_settings_main.*

class SettingsMainFragment : NoBackFragment(), SettingsMainView {
    companion object {
        const val TAG = "SettingsMainFragment"

        fun newInstance(): SettingsMainFragment {
            val fragment: SettingsMainFragment = SettingsMainFragment()
            val args: Bundle = Bundle()
            fragment.arguments = args
            return fragment
        }
    }

    @InjectPresenter
    lateinit var presenter: SettingsMainPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_settings_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        SettingsInfoUtils.setupSettingInfo(changePin, changePinInfo, changePinDescription, {presenter.onChangePinClick()})

        SettingsInfoUtils.setupSettingInfo(backupActions, backupInfo, backupDescription, {presenter.onBackupActionClick()})

        SettingsInfoUtils.setupSettingInfo(aboutApp, aboutInfo, aboutDescription, {presenter.onAboutAppClick()})

        SettingsInfoUtils.setupSettingInfo(customBn,customNodeInfo, bootNodeDescription,{ presenter.onCustomBNAppClick() })

        SettingsInfoUtils.setupSettingInfo(myWallet,myWalletInfo, walletDescription,{ presenter.onMyWalletClick() })

        setHasOptionsMenu(true)

        tv_otp.setOnClickListener {
            presenter.onOTPclick()
        }
    }

    override fun getTitle(): String = getString(R.string.settings)

    override fun onResume() {
        super.onResume()
        menu?.clear()
    }
}
