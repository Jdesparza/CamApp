package com.example.camapp

import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import com.example.camapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    val request_code_take_photo = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpListener()
    }

    private fun setUpListener() {
        binding.btnAddphoto.setOnClickListener {
            dispatchTakePictureIntent()
        }
    }

    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePicture ->
                startActivityForResult(takePicture, request_code_take_photo)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == request_code_take_photo && resultCode == RESULT_OK) {
            data?.extras?.let { bundle ->
                val imageBitmap = bundle.get("data") as Bitmap
                binding.shapeableImageView.setImageBitmap(imageBitmap)
            }
        }
    }
}