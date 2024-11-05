package com.github.coutinhonobre.roboletric.componets

import android.app.Activity
import android.view.View
import android.widget.Toast
import com.github.coutinhonobre.roboletric.R
import com.google.android.material.bottomsheet.BottomSheetDialog


class ShowBottomSheetDialog {
    fun build(activity: Activity) {
        val bottomSheetDialog = BottomSheetDialog(activity)
        val sheetView: View = activity.getLayoutInflater().inflate(R.layout.bottom_sheet_dialog, null)
        bottomSheetDialog.setContentView(sheetView)

        sheetView.findViewById<View>(R.id.action1).setOnClickListener { v: View? ->
            // Handle Action 1
            Toast.makeText(activity, "Ação 1 selecionada", Toast.LENGTH_SHORT).show()
            bottomSheetDialog.dismiss()
        }

        sheetView.findViewById<View>(R.id.action2).setOnClickListener { v: View? ->
            // Handle Action 2
            Toast.makeText(activity, "Ação 2 selecionada", Toast.LENGTH_SHORT).show()
            bottomSheetDialog.dismiss()
        }

        bottomSheetDialog.show()
    }
}