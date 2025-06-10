package com.leif2k.composition.presentation

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.leif2k.composition.domain.entity.Level

class GameViewModelFactory(
    private val application: Application,
    private val level: Level
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GameViewModel::class.java)) {
            return GameViewModel(application, level) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: $modelClass")
    }
}