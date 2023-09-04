package com.example.testapplication.viewModel

import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.testapplication.adapter.PageContentAdapter
import com.example.testapplication.base.BaseViewModel
import com.example.testapplication.extension.somethingWentWrong
import com.example.testapplication.models.PageData
import com.example.testapplication.repository.IDashboardRepository
import com.example.testapplication.util.Event
import com.example.testapplication.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    @ApplicationContext private val mApplication: Context,
    private val iRepository: IDashboardRepository
) : BaseViewModel(mApplication as Application) {

    private val _pageContentData: MutableLiveData<Event<Resource<PageData>>> = MutableLiveData()
    val pageContentData: LiveData<Event<Resource<PageData>>> get() = _pageContentData
    private var contentLoadingJob: Job? = null

    val adapter = PageContentAdapter()
    private val loadedPageNumbers = mutableSetOf<Int>()
    var currentPage = 0

    fun loadPageContent() = viewModelScope.launch {
        if (contentLoadingJob == null || contentLoadingJob?.isActive == false)
            contentLoadingJob = fetchPageContent(findNextPageNumberToLoad())
    }

    private fun findNextPageNumberToLoad(): Int {
        currentPage = loadedPageNumbers.maxOrNull() ?: 0
        return (++currentPage)
    }

    private fun fetchPageContent(pageNumber: Int) = viewModelScope.launch(Dispatchers.IO) {
        _pageContentData.postValue(Event(Resource.Loading()))
        val newDataChunk = iRepository.loadJsonData(mApplication, pageNumber)
        newDataChunk?.let {
            loadedPageNumbers.add(it.pageNum?.toInt() ?: 0)
            _pageContentData.postValue(Event(Resource.Success(it)))
        } ?: run {
            _pageContentData.postValue(Event(Resource.Error(mApplication.somethingWentWrong())))
        }
    }
}