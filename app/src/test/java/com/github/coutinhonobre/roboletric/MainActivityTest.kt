package com.github.coutinhonobre.roboletric

import android.widget.Button
import android.widget.TextView
import io.mockk.every
import io.mockk.mockkConstructor
import io.mockk.mockkObject
import io.mockk.mockkStatic
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.shadows.ShadowLooper

@RunWith(RobolectricTestRunner::class)
@Config(application = TestApp::class)
class MainActivityTest {

    @Before
    fun setUp() {
//        mockkObject(TextProvider)
    }

    @Test
    fun `testa o texto inicial da TextView`() {
        val activity = Robolectric.buildActivity(MainActivity::class.java).create().get()

        val textView = activity.findViewById<TextView>(R.id.textView)
        assertEquals("Texto inicial", textView.text.toString())
    }

    @Test
    fun `testa o clique do botão e a atualização do texto da TextView`() {
        val activity = Robolectric.buildActivity(MainActivity::class.java).create().start().resume().get()
        mockkConstructor(TextGeneric::class)
        every { anyConstructed<TextGeneric>().getText() } returns "Texto Generico2"

        val textView = activity.findViewById<TextView>(R.id.textView)
        val textView2 = activity.findViewById<TextView>(R.id.textView2)
        val button = activity.findViewById<Button>(R.id.button)

        button.performClick()

        ShadowLooper.runUiThreadTasksIncludingDelayedTasks()

        assertEquals("Texto atualizado", textView.text.toString())
        assertEquals("Texto Generico2", textView2.text.toString())
    }
}
