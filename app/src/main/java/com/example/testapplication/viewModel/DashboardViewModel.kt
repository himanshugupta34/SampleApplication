package com.example.testapplication.viewModel

import android.app.Application
import android.content.Context
import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.testapplication.base.BaseViewModel
import com.example.testapplication.data.preferences.PreferenceHelper
import com.example.testapplication.extension.somethingWentWrong
import com.example.testapplication.repository.IDashboardRepository
import com.example.testapplication.util.Event
import com.example.testapplication.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    @ApplicationContext private val mApplication: Context,
    private val iRepository: IDashboardRepository,
    val mPreferenceHelper: PreferenceHelper
) : BaseViewModel(mPreferenceHelper, mApplication as Application) {

    fun getJokeList(): MutableList<String> {
        return mPreferenceHelper.getJokeData()
    }

    /**
     *  Pair<String, Boolean> :
     *      Pair.First : is used for the updating count timer on to the view
     *      Pair.Second : is used if count down is finished
     */
    private lateinit var timer: CountDownTimer
    fun startTimerCountDown() {
        viewModelScope.launch {
            if (::timer.isInitialized) {
                timer.cancel()
            }
            timer = object : CountDownTimer(1000 * 60, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                }

                override fun onFinish() {
                    getJoke()
                    startTimerCountDown()
                }
            }
            timer.start()
        }
    }

    private val _jokeData: MutableLiveData<Event<Resource<String>>> =
        MutableLiveData()
    val jokeData: LiveData<Event<Resource<String>>> get() = _jokeData

    fun getJoke() = viewModelScope.launch {
        iRepository.getJoke()
            .onStart { _jokeData.value = Event(Resource.Loading()) }
            .catch { e ->
                _jokeData.value =
                    Event(Resource.Error(e.message ?: mApplication.somethingWentWrong()))
            }.collect {
                _jokeData.value = if (it.isNotEmpty()) {
                    mPreferenceHelper.saveJokeData(it)
                    Event(Resource.Success(it))
                } else Event(Resource.Error(mApplication.somethingWentWrong()))
            }
    }
}