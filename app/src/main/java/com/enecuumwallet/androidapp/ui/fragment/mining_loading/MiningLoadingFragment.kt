package com.enecuumwallet.androidapp.ui.fragment.mining_loading

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.enecuumwallet.androidapp.R
import com.enecuumwallet.androidapp.presentation.view.mining_loading.MiningLoadingView
import com.enecuumwallet.androidapp.presentation.presenter.mining_loading.MiningLoadingPresenter

import com.arellomobile.mvp.presenter.InjectPresenter
import com.enecuumwallet.androidapp.ui.fragment.loading.LoadingFragment

class MiningLoadingFragment : LoadingFragment(), MiningLoadingView {
    companion object {
        const val TAG = "MiningLoadingFragment"

        fun newInstance(): MiningLoadingFragment {
            val fragment: MiningLoadingFragment = MiningLoadingFragment()
            val args: Bundle = Bundle()
            fragment.arguments = args
            return fragment
        }
    }

    @InjectPresenter
    lateinit var presenter: MiningLoadingPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_mining_loading, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.onCreate()
    }
}
