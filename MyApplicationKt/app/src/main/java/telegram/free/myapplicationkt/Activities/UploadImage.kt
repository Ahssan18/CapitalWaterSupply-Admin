package telegram.free.myapplicationkt.Activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.android.gms.auth.api.signin.internal.Storage
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.FirebaseStorage.getInstance
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import telegram.free.myapplicationkt.R
import telegram.free.myapplicationkt.Utils.Utilities
import java.util.*


class UploadImage : AppCompatActivity(), View.OnClickListener {
    lateinit var choose: Button
    lateinit var uploadImage: Button
    val PICK_IMAGE_REQUEST=1111
    lateinit var imgview: ImageView
    private var imageUri: Uri? = null
    private lateinit var  firebaseStorage:FirebaseStorage
    private lateinit var  storageRefrence:StorageReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload_image)
        initWidgets()
        clickListener()

    }

    private fun clickListener() {
        choose.setOnClickListener(this)
        uploadImage.setOnClickListener(this)
    }

    private fun initWidgets() {
        firebaseStorage=getInstance()
        storageRefrence=getInstance().reference
        choose = findViewById(R.id.btnChoose)
        uploadImage = findViewById(R.id.btnUpload)
        imgview = findViewById(R.id.imgView)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            choose.id ->
                picImage()
                uploadImage.id ->
                    uploadImages()
        }
    }

    private fun uploadImages() {
        Utilities.showprogress(this,"Uploading...")
        val ref: StorageReference =getInstance().reference.child("images_"+UUID.randomUUID().toString())
        ref.putFile(imageUri!!).addOnSuccessListener { taskSnapshot ->
            taskSnapshot.storage.downloadUrl.addOnSuccessListener {
                val imageUrl = it.toString()
                Log.e("pic_fuull_path", imageUrl)
                Utilities.hideProgress()
                Utilities.showToast(this,"Uploaded Successfully!")
            }
        }.addOnFailureListener{ e ->
                print(e.message)
            Utilities.hideProgress()
            Utilities.showToast(this,e.message.toString())
            }

    }
    val previewRequest =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK) {
                imageUri= it.data?.data
                Log.d("file_url_result",imageUri.toString())
                imgview.setImageURI(imageUri)
                // do whatever with the data in the callback
            }
        }
    private fun picImage() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        previewRequest.launch(intent)
        /*startActivityForResult(
            Intent.createChooser(
                intent,
                "Select Image from here..."
            ),
            PICK_IMAGE_REQUEST
        )*/
    }
}