package com.android.redpencil_completeapp.ui

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.android.redpencil_completeapp.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var messageEditText : EditText = findViewById(R.id.messageEditText)
        val msgPhotoPickerImageView : ImageView = findViewById(R.id.photoPickerButton)
        val sendBtn : Button = findViewById(R.id.sendButton)

        val msgPhotoPicker : ActivityResultLauncher<String> =
            registerForActivityResult(ActivityResultContracts.GetContent(),
                ActivityResultCallback<Uri>(){
                //TODO Push it to storage. Call on for it.
            })

        msgPhotoPickerImageView.setOnClickListener(View.OnClickListener {
            msgPhotoPicker.launch("image/*")
        })
    }
}