package com.example.zuizzsaeed_images

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var imageView: ImageView
    private lateinit var editText: EditText
    private lateinit var sharedPreferences: SharedPreferences

    private val imageArray = arrayOf(
        R.drawable.batman,
        R.drawable.voldemort,
        R.drawable.spiderman // Add your image resource IDs here
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

        imageView = findViewById(R.id.imageView)
        editText = findViewById(R.id.editText)

        val button: Button = findViewById(R.id.button)
        button.setOnClickListener {
            imageView?.let {
                val randomIndex = Random.nextInt(imageArray.size)
                val resourceId = imageArray[randomIndex]
                it.setImageResource(resourceId)

                // Save the image resource ID to SharedPreferences
                sharedPreferences.edit().putInt("imageResourceId", resourceId).apply()
            }

            // Save the text from EditText to SharedPreferences
            sharedPreferences.edit().putString("editTextValue", editText.text.toString()).apply()
        }

        // Restore saved state on onCreate()
        restoreSavedState()
    }

    override fun onDestroy() {
        super.onDestroy()
        // Save the text from EditText to SharedPreferences when activity is destroyed
        sharedPreferences.edit().putString("editTextValue", editText.text.toString()).apply()
    }

    private fun restoreSavedState() {
        // Restore image resource ID from SharedPreferences
        val savedImageResourceId = sharedPreferences.getInt("imageResourceId", 0)
        if (savedImageResourceId != 0) {
            imageView.setImageResource(savedImageResourceId)
        }

        // Restore text from SharedPreferences
        val savedText = sharedPreferences.getString("editTextValue", "")
        editText.setText(savedText)
    }
}
