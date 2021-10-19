package com.wdh.secretdiary

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.core.widget.addTextChangedListener
import com.wdh.secretdiary.databinding.ActivityDiaryBinding

class DiaryActivity : AppCompatActivity() {

    private lateinit var binding:ActivityDiaryBinding

    private val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {

        binding = ActivityDiaryBinding.inflate(layoutInflater)

        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val detailPreferences = getSharedPreferences("diary", Context.MODE_PRIVATE)

        binding.diaryEditText.setText(detailPreferences.getString("detail", ""))

        val runnable = Runnable{
            getSharedPreferences("diary", Context.MODE_PRIVATE).edit{
                putString("detail", binding.diaryEditText.text.toString())
            }
        }

        binding.diaryEditText.addTextChangedListener {
            handler.removeCallbacks(runnable)
            handler.postDelayed(runnable, 500)
        }
    }
}