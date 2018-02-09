package com.enecuum.androidapp.navigation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentManager
import com.enecuum.androidapp.ui.fragment.balance.BalanceFragment
import com.enecuum.androidapp.ui.fragment.mining_in_progress.MiningInProgressFragment
import com.enecuum.androidapp.ui.fragment.mining_join_team.MiningJoinTeamFragment
import com.enecuum.androidapp.ui.fragment.mining_loading.MiningLoadingFragment
import com.enecuum.androidapp.ui.fragment.mining_start.MiningStartFragment
import com.enecuum.androidapp.ui.fragment.receive_by_address.ReceiveByAddressFragment
import com.enecuum.androidapp.ui.fragment.receive_qr.ReceiveQrFragment
import com.enecuum.androidapp.ui.fragment.send_finish.SendFinishFragment
import com.enecuum.androidapp.ui.fragment.send_parameters.SendParametersFragment
import com.enecuum.androidapp.ui.fragment.settings_about_app.SettingsAboutAppFragment
import com.enecuum.androidapp.ui.fragment.settings_backup.SettingsBackupFragment
import com.enecuum.androidapp.ui.fragment.settings_main.SettingsMainFragment
import com.enecuum.androidapp.ui.fragment.settings_terms.SettingsTermsFragment
import com.enecuum.androidapp.ui.fragment.tokens_jettons.TokensAndJettonsFragment
import ru.terrakok.cicerone.android.SupportAppNavigator

/**
 * Created by oleg on 29.01.18.
 */
class FragmentNavigator(activity: FragmentActivity?,
                        fragmentManager: FragmentManager?,
                        containerId: Int) : SupportAppNavigator(activity, fragmentManager, containerId) {
    override fun createActivityIntent(context: Context?, screenKey: String?, data: Any?): Intent? {
        return null
    }

    override fun createFragment(screenKey: String?, data: Any?): Fragment? {
        if(screenKey == null)
            return null
        var safeData = data
        if(safeData == null)
            safeData = Bundle.EMPTY
        val fragmentType = FragmentType.valueOf(screenKey)
        when (fragmentType) {
            FragmentType.Balance -> return BalanceFragment.newInstance()
            FragmentType.Tokens -> return TokensAndJettonsFragment.newInstance()
            FragmentType.ReceiveByAddress -> return ReceiveByAddressFragment.newInstance(safeData as Bundle)
            FragmentType.ReceiveQr -> return ReceiveQrFragment.newInstance(safeData as Bundle)
            FragmentType.SendOptions -> return SendParametersFragment.newInstance(safeData as Bundle)
            FragmentType.SendFinish -> return SendFinishFragment.newInstance(safeData as Bundle)
            FragmentType.SettingsMain -> return SettingsMainFragment.newInstance()
            FragmentType.SettingsBackup -> return SettingsBackupFragment.newInstance()
            FragmentType.SettingsAboutApp -> return SettingsAboutAppFragment.newInstance()
            FragmentType.SettingsTerms -> return SettingsTermsFragment.newInstance()
            FragmentType.MiningStart -> return MiningStartFragment.newInstance()
            FragmentType.MiningProgress -> return MiningInProgressFragment.newInstance()
            FragmentType.MiningLoading -> return MiningLoadingFragment.newInstance()
            FragmentType.MiningJoinTeam -> return MiningJoinTeamFragment.newInstance(safeData as Bundle)
        }
    }
}