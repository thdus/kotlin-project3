package com.example.chapter4

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import com.example.chapter4.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.goInputActivityButton.setOnClickListener{
            val intent = Intent(this, EditActivity::class.java)
            startActivity(intent)
        }
        getDataUiUpdate()

        binding.deleteButton.setOnClickListener{
            deleteData()
        }

        binding.emergencyContactlayer.setOnClickListener{
             with(Intent(Intent.ACTION_VIEW)) {
                val phoneNumber = binding.emergencyContactValueTextView.text.toString()
                    .replace("-","")
                data = Uri.parse("tel:$phoneNumber")
                startActivity(this)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        getDataUiUpdate()
    }

    private fun getDataUiUpdate() {
        with(getSharedPreferences(USER_INFORMATION, Context.MODE_PRIVATE)) {
            binding.nameValueTextView.text = getString(NAME, "미정")
            binding.birthdateValueTextView.text = getString(BIRTHDATE, "미정")
            binding.bloodTypeValueValueTextView.text = getString(BLOOD_TYPE, "미정")
            binding.emergencyContactValueTextView.text = getString(EMERGENCY_CONTACT, "미정")
            val warning = getString(WARNING, "")

            binding.warningTextView.isVisible = warning.isNullOrEmpty().not()
            binding.warningValueTextView.isVisible = warning.isNullOrEmpty().not()
            if (!warning.isNullOrEmpty()) {
                binding.warningTextView.text = warning
            }
        }
    }

    private fun deleteData() {
        with(getSharedPreferences(USER_INFORMATION, MODE_PRIVATE).edit()) {
            clear()
            apply()
        }
        getDataUiUpdate()
        Toast.makeText(this, "초기화를 완료했습니다.", Toast.LENGTH_SHORT).show()
    }
}