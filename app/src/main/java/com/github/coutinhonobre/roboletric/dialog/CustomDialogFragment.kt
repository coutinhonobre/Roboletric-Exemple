package com.github.coutinhonobre.roboletric.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment

data class CustomDialogModel(
    val title: String,
    val message: String,
    val positiveButton: String,
    val negativeButton: String
)

class CustomDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val customDialogModel = CustomDialogModel(
                title = "Título do Diálogo",
                message = "Este é o conteúdo do diálogo.",
                positiveButton = "OK",
                negativeButton = "Cancelar"
            )
            AlertDialog.Builder(it)
            .setTitle(customDialogModel.title)
                .setMessage(customDialogModel.message)
                .setPositiveButton("OK") { dialog, _ ->
                    dialog.dismiss()
                }
                .setNegativeButton("Cancelar") { dialog, _ ->
                    dialog.dismiss()
                }.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    companion object {
        fun newInstance(): CustomDialogFragment {
            return CustomDialogFragment()
        }
    }
}
