package com.example.testapplication.view

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testapplication.R
import com.example.testapplication.base.BaseActivity
import com.example.testapplication.databinding.ActivityMainBinding
import com.example.testapplication.interfaces.ConnectivityReceiverListener
import com.example.testapplication.models.PageData
import com.example.testapplication.util.EventObserver
import com.example.testapplication.util.Resource
import com.example.testapplication.util.decorator.FirstRowDecorator
import com.example.testapplication.util.fail
import com.example.testapplication.util.loading
import com.example.testapplication.util.success
import com.example.testapplication.viewBinding
import com.example.testapplication.viewModel.DashboardViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DashboardViewController : BaseActivity(), ConnectivityReceiverListener {

    private val binding: ActivityMainBinding by viewBinding(ActivityMainBinding::inflate)
    private val viewModel: DashboardViewModel by viewModels()
    private lateinit var networkError: Snackbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        updateActionBarContent(getString(R.string.romantic_comedy))

        viewModel.pageContentData.observe(this, pageContentObserver)
        viewModel.loadPageContent()
        binding.recyclerView.apply {
            adapter = viewModel.adapter
            val defaultMargin = resources.getDimension(R.dimen.margin_default)
            val firstRowMargin = resources.getDimension(R.dimen.margin_first_row)
            val decoration = FirstRowDecorator(firstRowMargin, defaultMargin)
            addItemDecoration(decoration)
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    val layoutManager = recyclerView.layoutManager as GridLayoutManager
                    val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()
                    val totalItemCount = layoutManager.itemCount
                    if (lastVisibleItemPosition >= totalItemCount - 1)
                        viewModel.loadPageContent()
                }
            })
        }
    }

    private val pageContentObserver = EventObserver<Resource<PageData>> { result ->
        result.loading {

        }.success { pageContentData ->
            val currentLoadedPageNumber = pageContentData?.pageNum?.toInt() ?: 0
            updateActionBarContent(pageContentData?.title)
            if (viewModel.currentPage == currentLoadedPageNumber)
                viewModel.adapter.addAllItems(pageContentData?.contentList ?: mutableListOf())
        }.fail { _, _ ->

        }
    }

    private fun updateActionBarContent(title: String?) = lifecycleScope.launch {
        supportActionBar?.title = title
    }

    override val connectivityReceiverListener: ConnectivityReceiverListener get() = this

    override fun onNetworkConnectionChanged(isConnected: Boolean?) {
        // Handle network connection change here
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

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.search_menu, menu)
        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem?.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                // Handle query submission here
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let { viewModel.adapter.filterItems(newText) }
                return true
            }
        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_search -> {
                item.actionView as SearchView
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}