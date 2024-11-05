package com.github.coutinhonobre.roboletric.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import com.github.coutinhonobre.roboletric.MainActivity
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkConstructor
import io.mockk.mockkStatic
import io.mockk.unmockkAll
import io.mockk.verify
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.shadows.ShadowDialog

@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE)
class CustomDialogFragmentTest {

    private lateinit var activity: MainActivity
    private lateinit var fragmentManager: FragmentManager

    @Before
    fun setup() {
        activity = Robolectric.buildActivity(MainActivity::class.java).create().start().resume().get()
        fragmentManager = activity.supportFragmentManager
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun testDialogCreation() {
        activity = Robolectric.buildActivity(MainActivity::class.java).create().start().resume().get()
        val fragmentManager = activity.supportFragmentManager
        val dialogFragment = CustomDialogFragment.newInstance()

        dialogFragment.show(fragmentManager, "CustomDialogFragment")
        Robolectric.flushForegroundThreadScheduler()

        val dialog = ShadowDialog.getLatestDialog()
        assertNotNull("Dialog should be created and shown", dialog)
    }

    @Test
    fun testDialogTitleAndMessage() {
        // Arrange
        val dialogFragment = CustomDialogFragment.newInstance()
        dialogFragment.show(fragmentManager, "CustomDialogFragment")
        Robolectric.flushForegroundThreadScheduler()

        // Act
        val dialog = ShadowDialog.getLatestDialog() as AlertDialog?
        assertNotNull("Dialog should be shown", dialog)

        // Assert
        val dialogMessage = dialog?.findViewById<TextView>(android.R.id.message)

        assertEquals("Este é o conteúdo do diálogo.", dialogMessage?.text)
    }

    @Test
    fun testPositiveButtonDismiss() {
        // Arrange
        val dialogFragment = CustomDialogFragment.newInstance()
        dialogFragment.show(fragmentManager, "CustomDialogFragment")
        Robolectric.flushForegroundThreadScheduler()

        val dialog = ShadowDialog.getLatestDialog() as AlertDialog?
        assertNotNull("Dialog should be shown", dialog)

        val positiveButton = dialog?.getButton(AlertDialog.BUTTON_POSITIVE)
        assertNotNull("Positive button should be present", positiveButton)

        val mockDialogInterface = mockk<Dialog>(relaxed = true)
        every { mockDialogInterface.dismiss() } returns Unit
    }

    @Test
    fun testNegativeButtonDismiss() {
        // Arrange
        val dialogFragment = CustomDialogFragment.newInstance()
        dialogFragment.show(fragmentManager, "CustomDialogFragment")
        Robolectric.flushForegroundThreadScheduler()

        val dialog = ShadowDialog.getLatestDialog() as AlertDialog?
        assertNotNull("Dialog should be shown", dialog)

        val negativeButton = dialog?.getButton(AlertDialog.BUTTON_NEGATIVE)
        assertNotNull("Negative button should be present", negativeButton)
    }

    @Test
    fun testMockCustomDialogModelConstructor() {
        // Mock the constructor of CustomDialogModel
        mockkConstructor(CustomDialogModel::class)

        // Mock the behavior of the constructor's properties
        every { anyConstructed<CustomDialogModel>().title } returns "Título Mockado"
        every { anyConstructed<CustomDialogModel>().message } returns "Mensagem Mockada"
        every { anyConstructed<CustomDialogModel>().positiveButton } returns "Confirmar"
        every { anyConstructed<CustomDialogModel>().negativeButton } returns "Voltar"

        // Arrange
        val dialogFragment = CustomDialogFragment.newInstance()
        dialogFragment.show(fragmentManager, "CustomDialogFragment")
        Robolectric.flushForegroundThreadScheduler()

        // Act
        val dialog = ShadowDialog.getLatestDialog() as AlertDialog?
        assertNotNull("Dialog should be shown", dialog)

        // Assert
        // Verify that the properties were accessed and correspond to the mocked values
        verify { anyConstructed<CustomDialogModel>().title }
    }


    @Test
    fun testAlertDialogCreation() {
        // Arrange
        val customDialogModel = CustomDialogModel(
            title = "Título do Diálogo",
            message = "Este é o conteúdo do diálogo.",
            positiveButton = "OK",
            negativeButton = "Cancelar"
        )

        // Mockar o construtor de AlertDialog.Builder
        mockkConstructor(AlertDialog.Builder::class)

        // Criar um mock do AlertDialog
        val alertDialogMock = mockk<AlertDialog>(relaxed = true)

        // Definir o comportamento do mock para retornar o AlertDialog mockado
        every { anyConstructed<AlertDialog.Builder>().create() } returns alertDialogMock

        // Act
        val dialogFragment = CustomDialogFragment.newInstance()
        dialogFragment.show(fragmentManager, "CustomDialogFragment")
        Robolectric.flushForegroundThreadScheduler()

        // Assert
        verify { anyConstructed<AlertDialog.Builder>().setTitle(customDialogModel.title) }
        verify { anyConstructed<AlertDialog.Builder>().setMessage(customDialogModel.message) }
        verify { anyConstructed<AlertDialog.Builder>().setPositiveButton(customDialogModel.positiveButton, any()) }
        verify { anyConstructed<AlertDialog.Builder>().setNegativeButton(customDialogModel.negativeButton, any()) }
        verify { anyConstructed<AlertDialog.Builder>().create() }

    }




}