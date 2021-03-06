package com.enecuumwallet.androidapp.presentation.presenter.new_account_qr

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.enecuumwallet.androidapp.presentation.view.new_account_qr.NewAccountQrView
import com.enecuumwallet.androidapp.persistent_data.PersistentStorage
import com.enecuumwallet.androidapp.utils.PermissionUtils
import com.enecuumwallet.androidapp.R
import com.enecuumwallet.androidapp.application.EnecuumApplication
import com.enecuumwallet.androidapp.ui.base_ui_primitives.FileOpeningFragment
import com.enecuumwallet.androidapp.events.PinBackupFinished
import com.enecuumwallet.androidapp.utils.FileSystemUtils
import com.enecuumwallet.androidapp.utils.KeyboardUtils
import com.enecuumwallet.androidapp.utils.SystemIntentManager
import org.greenrobot.eventbus.EventBus
import java.security.*


@InjectViewState
class NewAccountQrPresenter : MvpPresenter<NewAccountQrView>(), FileOpeningFragment.FileOpeningPresenter {
    override fun onSelectedFilePaths(files: Array<out String>?) {
        if(files != null && files.isNotEmpty()) {
            PersistentStorage.setKeyPath(files[0])
            notifyBackupFinished()
            //TODO: save keypair here
        }
    }

    private var isGenerationStarted = false
    private var keys : KeyPair? = null
    private var isSendingStarted = false

    fun beginGenerateKeys() {
        if(isGenerationStarted)
            return
        isGenerationStarted = true
        //TODO: get key from blockchain

    }

    fun onSaveClick() {
        FileSystemUtils.checkPermissionsAndChooseDir(viewState)
    }

    override fun onRequestPermissionsResult(requestCode: Int, grantResults: IntArray) {
        PermissionUtils.checkPermissionsAndRunFunction({onSaveClick()}, requestCode, grantResults)
    }

    fun onCopyClick() {
        if(keys == null)
            return
        KeyboardUtils.copyToClipboard(keys?.public?.encoded.toString())
        EnecuumApplication.cicerone().router.showSystemMessage(
                EnecuumApplication.applicationContext().getString(R.string.key_copied_to_clipboard))
        notifyBackupFinished()
    }

    fun onShareClick() {
        if(keys == null)
            return
        SystemIntentManager.sendText(keys?.public?.encoded.toString())
        isSendingStarted = true
    }

    fun onResume() {
        if(!isSendingStarted)
            return
        EnecuumApplication.cicerone().router.showSystemMessage(EnecuumApplication.applicationContext().getString(R.string.sharing_key_finished))
        notifyBackupFinished()
        isSendingStarted = false
    }

    private fun notifyBackupFinished() {
        EventBus.getDefault().post(PinBackupFinished())
    }

}
