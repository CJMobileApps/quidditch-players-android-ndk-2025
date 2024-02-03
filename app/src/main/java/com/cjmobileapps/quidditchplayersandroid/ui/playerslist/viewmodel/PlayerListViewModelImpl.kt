package com.cjmobileapps.quidditchplayersandroid.ui.playerslist.viewmodel

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.cjmobileapps.quidditchplayersandroid.util.coroutine.CoroutineDispatchers
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PlayerListViewModelImpl @Inject constructor(
    coroutineDispatchers: CoroutineDispatchers,
    savedStateHandle: SavedStateHandle
) : ViewModel(), PlayerListViewModel {

    private val houseName: String = checkNotNull(savedStateHandle["houseName"])

    init {
        Log.d("HERE_","PlayerListViewModelImpl houseName " + houseName)
    }
}
