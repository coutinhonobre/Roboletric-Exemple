package com.github.coutinhonobre.roboletric

import android.app.Application
import org.robolectric.annotation.Config

class TestApp : Application() {
    override fun onCreate() {
        super.onCreate()
        setup("Texto atualizado")
    }

    fun setup(
        text: String
    ) {
        TextProvider.text = text
    }
}
