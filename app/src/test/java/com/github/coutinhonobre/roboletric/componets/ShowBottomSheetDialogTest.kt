package com.github.coutinhonobre.roboletric.componets

import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.github.coutinhonobre.roboletric.MainActivity
import com.github.coutinhonobre.roboletric.R
import com.google.android.material.bottomsheet.BottomSheetDialog
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.shadows.ShadowDialog
import org.robolectric.shadows.ShadowToast

@RunWith(RobolectricTestRunner::class)
class ShowBottomSheetDialogTest {

    private lateinit var activity: AppCompatActivity
    private lateinit var showBottomSheetDialog: ShowBottomSheetDialog

    @Before
    fun setUp() {
        // Inicializa a Activity e a classe ShowBottomSheetDialog
        activity = Robolectric.buildActivity(AppCompatActivity::class.java)
            .create()
            .start()
            .resume()
            .get()

        showBottomSheetDialog = ShowBottomSheetDialog()
    }

    @Test
    fun test01() {
        // Exibe o BottomSheetDialog
        showBottomSheetDialog.build(activity)

        // Verifica se o BottomSheetDialog está sendo exibido
        val dialog = ShadowDialog.getLatestDialog() as BottomSheetDialog
        assertNotNull("O diálogo deve estar presente", dialog)
        assertTrue("O diálogo deve estar visível", dialog.isShowing)
    }

    @Test
    fun test02() {
        // Exibe o BottomSheetDialog
        showBottomSheetDialog.build(activity)
        val dialog = ShadowDialog.getLatestDialog() as BottomSheetDialog
        assertNotNull(dialog)

        // Simula o clique no botão "Ação 1"
        val action1Button = dialog.findViewById<View>(R.id.action1)
        action1Button?.performClick()

        // Verifica se o diálogo foi fechado
        assertFalse("O diálogo deve estar fechado", dialog.isShowing)

        // Verifica se o Toast com a mensagem correta foi exibido
        assertEquals("Ação 1 selecionada", ShadowToast.getTextOfLatestToast())
    }

    @Test
    fun test03() {
        // Exibe o BottomSheetDialog
        showBottomSheetDialog.build(activity)
        val dialog = ShadowDialog.getLatestDialog() as BottomSheetDialog
        assertNotNull(dialog)

        // Simula o clique no botão "Ação 2"
        val action2Button = dialog.findViewById<View>(R.id.action2)
        action2Button?.performClick()

        // Verifica se o diálogo foi fechado
        assertFalse("O diálogo deve estar fechado", dialog.isShowing)

        // Verifica se o Toast com a mensagem correta foi exibido
        assertEquals("Ação 2 selecionada", ShadowToast.getTextOfLatestToast())
    }
}
