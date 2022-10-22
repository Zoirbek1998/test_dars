package dev.future.testapp

import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import dev.future.testapp.databinding.ActivityCameraAndGalareyaBinding
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.security.Permission
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.*
import java.util.jar.Manifest

class CameraAndGalareyaActivity : AppCompatActivity() {
    lateinit var binding: ActivityCameraAndGalareyaBinding

    var photoUri : Uri? = null
    var imagePath =""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCameraAndGalareyaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.image.setOnClickListener {
            showImagePickBottomSheetDialog()
        }


    }

    private fun showImagePickBottomSheetDialog() {
        val bottomSheetDialog = BottomSheetDialog(this)
        bottomSheetDialog.setContentView(R.layout.bottom_dialog)

        bottomSheetDialog.findViewById<TextView>(R.id.dialog_gal)?.setOnClickListener {
            getImageGallery.launch("image/*")
            bottomSheetDialog.dismiss()
        }

        bottomSheetDialog.findViewById<TextView>(R.id.dialog_camera)?.setOnClickListener {
            checkPermission()
            bottomSheetDialog.dismiss()
        }

        bottomSheetDialog.show()
    }

    fun checkPermission(){
            Dexter.withContext(this)
                .withPermission(android.Manifest.permission.CAMERA)
                .withListener(object : PermissionListener{
                    override fun onPermissionGranted(p0: PermissionGrantedResponse?) {
                        photoUri =  FileProvider.getUriForFile(
                            this@CameraAndGalareyaActivity,
                            BuildConfig.APPLICATION_ID,
                            createImageFile()
                        )
                        getImageFromCamera.launch(photoUri)
                    }

                    override fun onPermissionDenied(p0: PermissionDeniedResponse?) {

                    }

                    override fun onPermissionRationaleShouldBeShown(
                        p0: PermissionRequest?,
                        p1: PermissionToken?
                    ) {
                       p1!!.continuePermissionRequest()
                    }

                }).check()
    }

    private fun createImageFile(): File {
        val format = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val externalFilesDir = this.getExternalFilesDir(Environment.DIRECTORY_PICTURES)   //tashqi file driktori
        return File.createTempFile("JPEG_$format",".jpg",externalFilesDir).apply {}
    }

    private val getImageFromCamera = registerForActivityResult(ActivityResultContracts.TakePicture()){
        if (it){
            binding.image.setImageURI(photoUri)
            val inputStream = this.contentResolver?.openInputStream(photoUri!!)  //Oqim kiritish
            val file = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                File(this.filesDir,"${LocalDateTime.now()}.jpg")
            }else{
                File(this.filesDir,"${Date().time}.jpg")
            }

            val fileOutputStream = FileOutputStream(file)
            inputStream?.copyTo(fileOutputStream)
            inputStream?.close()
            fileOutputStream.close()
            imagePath = file.absolutePath
        }
    }

    private val getImageGallery =registerForActivityResult(ActivityResultContracts.GetContent()){
        it ?: return@registerForActivityResult
        binding.image.setImageURI(it)
        val inputStream = this.contentResolver?.openInputStream(it)  //Oqim kiritish
        val file = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            File(this.filesDir,"${LocalDateTime.now()}.jpg")
        }else{
            File(this.filesDir,"${Date().time}.jpg")
        }

        val fileOutputStream = FileOutputStream(file)
        inputStream?.copyTo(fileOutputStream)
        inputStream?.close()
        fileOutputStream.close()
        imagePath = file.absolutePath
    }
}