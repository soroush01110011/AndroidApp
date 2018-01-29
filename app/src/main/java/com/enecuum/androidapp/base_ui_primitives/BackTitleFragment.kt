package com.enecuum.androidapp.base_ui_primitives

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.arellomobile.mvp.MvpAppCompatFragment
import com.enecuum.androidapp.application.EnecuumApplication

/**
 * Created by oleg on 23.01.18.
 */
abstract class BackTitleFragment : OrdinalTitleFragment() {
    override fun onResume() {
        super.onResume()
        setHasOptionsMenu(true)
        val supportActionBar = (activity as AppCompatActivity).supportActionBar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if(item?.itemId == android.R.id.home) {
            EnecuumApplication.fragmentCicerone().router.exit()
        }
        return super.onOptionsItemSelected(item)
    }
}