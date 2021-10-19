package com.wdh.secretdiary

import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.edit
import com.wdh.secretdiary.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var changePasswordButtonMode = false

    override fun onCreate(savedInstanceState: Bundle?) {

        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

        setNumberPicker()

        initOpenButton()

        initChangePasswordButton()
    }

    private fun setNumberPicker() {
        binding.numberPicker1.apply {
            minValue = 0
            maxValue = 9
        }

        binding.numberPicker2.apply {
            minValue = 0
            maxValue = 9
        }

        binding.numberPicker3.apply {
            minValue = 0
            maxValue = 9
        }
    }

    private fun initOpenButton() {

        binding.openButton.setOnClickListener {
            if (changePasswordButtonMode) {
                Toast.makeText(this, "비밀번호 변경중입니다", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }

            val passwordPreference = getSharedPreferences("password", Context.MODE_PRIVATE)
            val passwordFromUser =
                "${binding.numberPicker1.value}${binding.numberPicker2.value}${binding.numberPicker3.value}"

            if (passwordPreference.getString("password", "000").equals(passwordFromUser)) {
                startActivity(Intent(this, DiaryActivity::class.java))
            } else {
                showErrorAlertDialog()
            }
        }
    }

    private fun initChangePasswordButton() {
        binding.changeButton.setOnClickListener {
            val passwordPreference = getSharedPreferences("password", Context.MODE_PRIVATE)
            if (changePasswordButtonMode) {
                passwordPreference.edit(true) {
                    val passwordFromUser =
                        "${binding.numberPicker1.value}${binding.numberPicker2.value}${binding.numberPicker3.value}"
                    putString("password", passwordFromUser)
                }
                changePasswordButtonMode = false
                binding.changeButton.setBackgroundColor(Color.BLACK)

            } else {
                val passwordFromUser =
                    "${binding.numberPicker1.value}${binding.numberPicker2.value}${binding.numberPicker3.value}"
                if (passwordPreference.getString("password", "000").equals(passwordFromUser)) {
                    changePasswordButtonMode = true
                    Toast.makeText(this, "변경할 패스워드를 입력해주세요", Toast.LENGTH_SHORT)
                        .show()
                    binding.changeButton.setBackgroundColor(Color.RED)

                } else {
                    showErrorAlertDialog()
                }

            }
        }

    }

    private fun showErrorAlertDialog(){
        AlertDialog.Builder(this)
            .setTitle("실패!!")
            .setMessage("비밀번호가 잘못되었습니다")
            .setPositiveButton("확인") { _, _ -> }
            .create()
            .show()
    }
}