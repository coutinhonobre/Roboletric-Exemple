package com.github.coutinhonobre.roboletric

import android.app.Application
import org.robolectric.annotation.Config

@Config(sdk = [28]) // Defina a versão do SDK que você deseja usar
class TestApp : Application() {
    override fun onCreate() {
        super.onCreate()

    }

    fun setup(
        text: String
    ) {
        TextProvider.text = text
    }
}
