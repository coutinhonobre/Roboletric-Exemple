package com.github.coutinhonobre.roboletric

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    private val _text = MutableLiveData("Texto inicial")
    val text: LiveData<String> get() = _text

    fun updateText() {
        val novoValue = TextProvider.getTextRandom()
        _text.value = novoValue
    }
}