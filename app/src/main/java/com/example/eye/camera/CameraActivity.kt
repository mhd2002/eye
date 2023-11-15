package com.example.eye.camera

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import com.example.eye.databinding.ActivityCameraBinding
import java.io.File
import java.text.SimpleDateFormat
import androidx.camera.view.PreviewView
import com.example.eye.activities.AddUserActivity
import com.example.eye.R
import java.io.ByteArrayOutputStream
import java.io.FileOutputStream
import java.io.IOException
import java.util.Locale
import java.util.concurrent.Executors
import java.util.concurrent.Executor

class CameraActivity : AppCompatActivity() {
    private lateinit var imageCapture: ImageCapture
    private lateinit var outputDirectory: File
    private lateinit var cameraView: PreviewView
    lateinit var binding: ActivityCameraBinding
    private var cameraExecutor: Executor = Executors.newSingleThreadExecutor()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCameraBinding.inflate(layoutInflater)
        setContentView(binding.root)


        outputDirectory = getOutputDirectory()
        cameraExecutor = Executors.newSingleThreadExecutor()

        try {
            startCamera()

        } catch (e: Exception) {
            println(e.message)
        }

        // Capture button click
        binding.captureButton.setOnClickListener {
            if (binding.captureButton.text == "عکس گرفتن") {
                binding.imageView.visibility = View.VISIBLE
                binding.captureButton.text = "دوباره"
                takePhoto()

            } else if (binding.captureButton.text == "دوباره") {

                binding.captureButton.text = "عکس گرفتن"
                binding.imageView.visibility = View.INVISIBLE
                takePhoto()
            }


        }
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)

        cameraProviderFuture.addListener({
            val cameraProvider = cameraProviderFuture.get()

            val preview = Preview.Builder().build()
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            cameraView = findViewById(R.id.cameraView)

            preview.setSurfaceProvider(cameraView.surfaceProvider)

            imageCapture = ImageCapture.Builder().build()

            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageCapture)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }, ContextCompat.getMainExecutor(this))
    }

    private fun takePhoto() {

        val imageCapture = imageCapture

        val photoFile = File(
            outputDirectory,
            SimpleDateFormat(
                "yyyy-MM-dd-HH-mm-ss-SSS",
                Locale.US
            ).format(System.currentTimeMillis()) + ".jpg"
        )

        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()


        imageCapture.takePicture(
            outputOptions,
            cameraExecutor,
            object : ImageCapture.OnImageSavedCallback {
                override fun onImageSaved(output: ImageCapture.OutputFileResults) {

                    runOnUiThread {


                        binding.imageView.setImageURI(output.savedUri)
                        binding.btSave.visibility = View.VISIBLE

                        binding.btSave.setOnClickListener {

                            val bitmap = BitmapFactory.decodeFile(photoFile.absolutePath)
                            val byteArrayOutputStream = ByteArrayOutputStream()
                            bitmap.compress(Bitmap.CompressFormat.PNG, 50, byteArrayOutputStream)
                            val byteArray = byteArrayOutputStream.toByteArray()

                            val file = File(this@CameraActivity.cacheDir, "large_data.dat")

                            try {
                                val outputStream = FileOutputStream(file)
                                outputStream.write(byteArray)
                                outputStream.close()
                            } catch (e: IOException) {
                                e.printStackTrace()
                            }

                            val intent = Intent(this@CameraActivity, AddUserActivity::class.java)
                            intent.putExtra("ok", file.absolutePath.toString())

                            setResult(Activity.RESULT_OK, intent)
                            finish()
                        }

                    }

                }

                override fun onError(exception: ImageCaptureException) {
                    exception.printStackTrace()
                }
            }
        )
    }

    private fun getOutputDirectory(): File {
        val mediaDir = externalMediaDirs.firstOrNull()?.let {
            File(it, resources.getString(R.string.app_name)).apply { mkdirs() }
        }
        return if (mediaDir != null && mediaDir.exists())
            mediaDir else filesDir
    }
}
