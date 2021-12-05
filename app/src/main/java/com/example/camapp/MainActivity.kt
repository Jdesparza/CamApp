package com.example.camapp

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.core.content.FileProvider
import com.example.camapp.databinding.ActivityMainBinding
import java.io.File
import java.io.IOException

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpListener()
    }
    val request_code_take_photo = 1

    private fun setUpListener() {
        binding.btnAddphoto.setOnClickListener {
            dispatchTakePictureIntent()
        }
    }
    var rutaImagen: String = ""
    private fun dispatchTakePictureIntent() {
        var intent:Intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (intent.resolveActivity(packageManager) != null){
            var imagenArchivo: File? = null
            try {
                imagenArchivo = crearImage()
            }catch (ex:IOException){
                Log.e("Error", ex.toString())
            }
            if (imagenArchivo != null){
                var fotoUri: Uri = FileProvider.getUriForFile(this, "com.example.camapp.fileprovider", imagenArchivo)
                intent.putExtra(MediaStore.EXTRA_OUTPUT, fotoUri)
                startActivityForResult(intent,1)
            }
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == request_code_take_photo && resultCode == RESULT_OK) {
            val imageBitmap = BitmapFactory.decodeFile(rutaImagen)
            binding.shapeableImageView.setImageBitmap(imageBitmap)
        }
    }
    private fun  crearImage(): File? {
        var nombreImagen = "foto_"
        var directorio: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        var imagen: File? =  File.createTempFile(nombreImagen, ".jpg", directorio)

        if (imagen != null) {
            rutaImagen = imagen.absolutePath
        }
        return imagen
    }
}


