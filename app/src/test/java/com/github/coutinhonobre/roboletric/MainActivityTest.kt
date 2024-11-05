package com.github.coutinhonobre.roboletric

import android.view.View
import android.widget.Button
import android.widget.TextView
import com.github.coutinhonobre.roboletric.componets.ShowBottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialog
import io.mockk.every
import io.mockk.mockkConstructor
import io.mockk.mockkObject
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.shadows.ShadowDialog
import org.robolectric.shadows.ShadowLooper
import org.robolectric.shadows.ShadowToast

@RunWith(RobolectricTestRunner::class)
@Config(application = TestApp::class)
class MainActivityTest {

    private lateinit var activity: MainActivity

    @Before
    fun setUp() {
        activity = Robolectric.buildActivity(MainActivity::class.java)
            .create()
            .start()
            .resume()
            .get()
    }

    @Test
    fun testInitialTextView() {
        val textView = activity.findViewById<TextView>(R.id.textView)
        assertEquals("Texto inicial", textView.text.toString())
    }

    @Test
    fun testClickTextView() {
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

    @Test
    fun testShowBottomSheetDialog() {
        // Simula o clique no botão que exibe o BottomSheetDialog
        val showSheetButton = activity.findViewById<Button>(R.id.showSheet)
        showSheetButton.performClick()

        // Verifica se o BottomSheetDialog está sendo exibido
        val dialog = ShadowDialog.getLatestDialog() as BottomSheetDialog
        assertNotNull("O diálogo deve estar presente", dialog)
        assertTrue("O diálogo deve estar visível", dialog.isShowing)

        // Testa clique na "Ação 1" e verifica o Toast e o fechamento do diálogo
        val action1Button = dialog.findViewById<View>(R.id.action1)
        action1Button?.performClick()
        assertEquals("Ação 1 selecionada", ShadowToast.getTextOfLatestToast())
        assertFalse("O diálogo deve estar fechado após clique na Ação 1", dialog.isShowing)

        // Exibe o diálogo novamente para testar a "Ação 2"
        showSheetButton.performClick()
        val dialog2 = ShadowDialog.getLatestDialog() as BottomSheetDialog
        val action2Button = dialog2.findViewById<View>(R.id.action2)
        action2Button?.performClick()
        assertEquals("Ação 2 selecionada", ShadowToast.getTextOfLatestToast())
        assertFalse("O diálogo deve estar fechado após clique na Ação 2", dialog2.isShowing)
    }
}
