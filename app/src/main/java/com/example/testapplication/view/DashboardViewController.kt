package com.example.testapplication.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import com.example.testapplication.R
import com.example.testapplication.adapter.JokeAdapter
import com.example.testapplication.base.BaseActivity
import com.example.testapplication.databinding.ActivityMainBinding
import com.example.testapplication.interfaces.ConnectivityReceiverListener
import com.example.testapplication.receivers.NetworkConnectionReceiver.Companion.connectivityReceiverListener
import com.example.testapplication.util.*
import com.example.testapplication.viewBinding
import com.example.testapplication.viewModel.DashboardViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DashboardViewController : BaseActivity(), ConnectivityReceiverListener {

    private val binding: ActivityMainBinding by viewBinding(ActivityMainBinding::inflate)
    private val viewModel: DashboardViewModel by viewModels()
    private lateinit var networkError: Snackbar
    private var jokesData: MutableList<String> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        jokesData = viewModel.getJokeList()
        if (jokesData.isEmpty()) viewModel.getJoke()
        viewModel.startTimerCountDown()

        binding.recyclerView.apply {
            addItemDecoration(DividerItemDecoration(context, VERTICAL))
            adapter = JokeAdapter(jokesData)
        }

        viewModel.jokeData.observe(this, jokeDataObserver)
    }

    private val jokeDataObserver = EventObserver<Resource<String>> { result ->
        result.loading {

        }.success { newJoke ->
            newJoke?.let {
                jokesData.add(it)
                if (jokesData.size > 10) {
                    if (viewModel.mPreferenceHelper.getIsExistingUser()) {
                        val toAdd = jokesData.takeLast(10).toMutableList()
                        (binding.recyclerView.adapter as JokeAdapter).addAll(toAdd)
                    } else {
                        jokesData.removeAt(0)
                        (binding.recyclerView.adapter as JokeAdapter)
                            .notifyItemRangeChanged(0, jokesData.size - 1)
                    }
                } else (binding.recyclerView.adapter as JokeAdapter).notifyItemInserted(jokesData.size - 1)
            }
        }.fail { _, _ ->

        }
    }

    override fun onNetworkConnectionChanged(isConnected: Boolean?) {
        if (::networkError.isInitialized.not())
            networkError = Snackbar.make(
                binding.root,
                getString(R.string.no_internet),
                Snackbar.LENGTH_INDEFINITE
            )
        if (isConnected == false)
            networkError.show()
        else networkError.dismiss()
    }

    override fun onResume() {
        super.onResume()
        connectivityReceiverListener = this
    }
}