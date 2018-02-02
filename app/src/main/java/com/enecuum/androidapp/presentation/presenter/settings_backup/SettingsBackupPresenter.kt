package com.enecuum.androidapp.presentation.presenter.settings_backup

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.enecuum.androidapp.R
import com.enecuum.androidapp.application.EnecuumApplication
import com.enecuum.androidapp.presentation.view.settings_backup.SettingsBackupView
import com.enecuum.androidapp.ui.base_ui_primitives.FileOpeningFragment
import com.enecuum.androidapp.utils.FileSystemUtils
import com.enecuum.androidapp.utils.PermissionUtils

@InjectViewState
class SettingsBackupPresenter : MvpPresenter<SettingsBackupView>(), FileOpeningFragment.FileOpeningPresenter {
    override fun onRequestPermissionsResult(requestCode: Int, grantResults: IntArray) {
        PermissionUtils.checkPermissionsAndRunFunction({onSaveClick()}, requestCode, grantResults)
    }

    override fun onSelectedFilePaths(files: Array<out String>?) {
        if(files != null && files.isNotEmpty()) {
            EnecuumApplication.cicerone().router.showSystemMessage(
                    EnecuumApplication.applicationContext().getString(R.string.key_backup_finished)
            )
        }
    }

    fun onSaveClick() {
        FileSystemUtils.checkPermissionsAndChooseDir(viewState)
    }

    fun onCopyClick() {

    }

    fun onShareClick() {

    }
}