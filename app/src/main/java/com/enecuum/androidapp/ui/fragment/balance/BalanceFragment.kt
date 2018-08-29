package com.enecuum.androidapp.ui.fragment.balance

import android.app.ProgressDialog
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.support.v4.content.LocalBroadcastManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.arellomobile.mvp.presenter.InjectPresenter
import com.enecuum.androidapp.R
import com.enecuum.androidapp.persistent_data.PersistentStorage
import com.enecuum.androidapp.presentation.presenter.balance.BalancePresenter
import com.enecuum.androidapp.presentation.view.balance.BalanceView
import com.enecuum.androidapp.ui.base_ui_primitives.NoBackFragment
import com.enecuum.androidapp.utils.TransactionsHistoryRenderer
import com.enecuum.androidapp.utils.Utils
import com.jakewharton.rxbinding2.view.RxView
import kotlinx.android.synthetic.main.fragment_balance.*
import java.util.concurrent.TimeUnit

class BalanceFragment : NoBackFragment(), BalanceView {

    companion object {
        const val FORMAT = "%s %.8f"
        const val TAG = "BalanceFragment"

        fun newInstance(): BalanceFragment {
            val fragment: BalanceFragment = BalanceFragment()
            val args: Bundle = Bundle()
            fragment.arguments = args
            return fragment
        }
    }

    @InjectPresenter
    lateinit var presenter: BalancePresenter

    lateinit var pd: ProgressDialog;

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_balance, container, false)
    }

    val startMiningReciever = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            presenter.onMiningToggle();
        }
    }
    private val RESTART_ACTION: String = "restart_action"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        context?.registerReceiver(startMiningReciever, IntentFilter(RESTART_ACTION))
        pd = ProgressDialog(view.context)

        pd.setMessage("Connecting...")
        presenter.onCreate()

        RxView.clicks(start)
                .debounce(500, TimeUnit.MILLISECONDS)
                .subscribe {
                    presenter.onMiningToggle();
                }

        if (PersistentStorage.getAutoMiningStart()) {
            Handler(Looper.getMainLooper()).postDelayed({
                presenter.onMiningToggle();
                Toast.makeText(view.context, "Restoring after crash", Toast.LENGTH_LONG).show()
                LocalBroadcastManager.getInstance(context!!).sendBroadcast(Intent(RESTART_ACTION))
                PersistentStorage.setAutoMiningStart(false)
            }, 10000);
        }

//        //////REMOVE THIS ONLY, FOR CRASH TESTING
//        Handler().postDelayed({
//            Utils.crashMe()
//        }, 30000)

        tokens.setOnClickListener({
            presenter.onTokensClick()
        })
        progressBar.getIndeterminateDrawable().setColorFilter(
                getResources().getColor(R.color.turquoise_blue_three),
                android.graphics.PorterDuff.Mode.SRC_IN);

//        presenter.onCreate()
        setHasOptionsMenu(true)
        TransactionsHistoryRenderer.configurePanelListener(slidingLayout, panelHint)
    }

    override fun onDestroyView() {
        context?.unregisterReceiver(startMiningReciever)
        super.onDestroyView()
    }

    override fun displayCurrencyRates(enq2Usd: Double, enq2Btc: Double) {
        enqBtc.text = String.format("%f ENQ/BTC", enq2Btc)
        enqUsd.text = String.format("%f ENQ/USD", enq2Usd)
    }

    override fun displayBalances(enq: Double, enqPlus: Double) {
        enqBalance.text = String.format("ENQ %.8f", enq)
        enqPlusBalance.text = String.format("ENQ+ %.8f", enqPlus)
    }

    override fun displayPoints(pointsValue: Double) {
        points.text = String.format(FORMAT, getString(R.string.points), pointsValue)
    }

    override fun displayMicroblocks(count: Int) {
        minedText.text = "You has mined: $count  ENQ";
    }

    override fun displayKarma(karmaValue: Double) {
        karma.text = String.format(FORMAT, getString(R.string.karma), karmaValue)
    }

    override fun displayTeamSize(teamSize: Int) {
        minedText.post { minedText.text = if (teamSize > 0) "Joining, team size is: $teamSize" else ""; }
    }

    override fun displayTransactionsHistory(transactionsList: List<String>) {
        TransactionsHistoryRenderer.displayTransactionsInRecyclerView(transactionsList, transactionsHistory)
    }

    override fun getTitle(): String {
        if (activity == null)
            return ""
        return activity!!.getString(R.string.my_wallet)
    }

    override fun setBalance(balance: Int?) {
        enqBalance.text = "ENQ " + balance?.toString();
    }

    override fun showProgress() {
        progressBar.visibility = View.VISIBLE

    }

    override fun hideProgress() {
        progressBar.visibility = View.INVISIBLE
    }

    override fun changeButtonState(isStart: Boolean) {
        if (isStart) {
            start.text = getText(R.string.start_mining)
        } else {
            start.text = getText(R.string.stop_mining)
        }
    }

    override fun updateProgressMessage(str: String) {
        pd.setMessage(str)
    }


    override fun showLoading() {
        pd.show()
    }

    override fun showConnectionError() {
        Handler(Looper.getMainLooper()).post {
            Toast.makeText(context, R.string.connection_error, Toast.LENGTH_LONG).show()
        }

    }

    override fun hideLoading() {
        if (pd.isShowing) {
            pd.dismiss()
        }
    }

    override fun onResume() {
        super.onResume()
        menu?.clear()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }


}
