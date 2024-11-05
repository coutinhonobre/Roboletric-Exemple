package com.github.coutinhonobre.roboletric

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.github.coutinhonobre.roboletric.componets.ShowBottomSheetDialog
import com.github.coutinhonobre.roboletric.databinding.ActivityMainBinding
import com.github.coutinhonobre.roboletric.dialog.CustomDialogFragment


class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val viewModel by lazy {
        MainViewModelFactory().let { factory ->
            ViewModelProvider(this, factory).get(MainViewModel::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        viewModel.text.observe(this) { newText ->
            binding.textView.text = newText
            binding.textView2.text = TextGeneric().getText()
        }

        with(binding) {
            button.setOnClickListener {
                viewModel.updateText()
            }

            showDialog.setOnClickListener {
                val dialog = CustomDialogFragment.newInstance()
                dialog.show(supportFragmentManager, "CustomDialog")
            }

            showSheet.setOnClickListener {
                ShowBottomSheetDialog().build(this@MainActivity)
            }
        }
    }
}