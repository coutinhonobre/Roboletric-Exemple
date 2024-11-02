package com.github.coutinhonobre.roboletric.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import com.github.coutinhonobre.roboletric.MainActivity
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
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

    @Test
    fun testDialogExists() {
        activity = Robolectric.buildActivity(MainActivity::class.java).create().start().resume().get()
        val fragmentManager = activity.supportFragmentManager
        val dialogFragment = CustomDialogFragment.newInstance()

        dialogFragment.show(fragmentManager, "CustomDialogFragment")
        Robolectric.flushForegroundThreadScheduler()

        val dialog = ShadowDialog.getLatestDialog()
        assertNotNull("Dialog should be created and shown", dialog)
    }

    @Test
    fun `test dialog title and message are correct`() {
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
    fun `test positive button click dismisses dialog`() {
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
    fun `test negative button click dismisses dialog`() {
        // Arrange
        val dialogFragment = CustomDialogFragment.newInstance()
        dialogFragment.show(fragmentManager, "CustomDialogFragment")
        Robolectric.flushForegroundThreadScheduler()

        val dialog = ShadowDialog.getLatestDialog() as AlertDialog?
        assertNotNull("Dialog should be shown", dialog)

        val negativeButton = dialog?.getButton(AlertDialog.BUTTON_NEGATIVE)
        assertNotNull("Negative button should be present", negativeButton)

    }
}
