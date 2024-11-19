package com.github.coutinhonobre.roboletric

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class MainViewModel : ViewModel() {

    private val _text = MutableLiveData<String>("Teste")
    val text: LiveData<String> get() = _text

    fun updateText() {
        viewModelScope.launch {
            getText()
        }
    }

    private suspend fun getText() = withContext(Dispatchers.IO) {
        delay(2000)
        val texto = TextProvider.getTextRandom()
//        _text.value = texto
        _text.postValue(texto)
    }
}