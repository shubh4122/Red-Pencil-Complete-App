package com.android.redpencil_completeapp.ui

import android.app.Activity
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.redpencil_completeapp.models.Message
import com.android.redpencil_completeapp.R
import com.android.redpencil_completeapp.adapter.MessageAdapter
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    //NOTE lateinit
    private lateinit var recyclerView : RecyclerView
    private lateinit var messageAdapter: MessageAdapter
    private lateinit var messageList: ArrayList<Message>
    private lateinit var layoutManager : RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var messageEditText : EditText = findViewById(R.id.messageEditText)
        val msgPhotoPickerImageView : ImageView = findViewById(R.id.photoPickerButton)
        val sendBtn : Button = findViewById(R.id.sendButton)
        val msgViewModel : MessageViewModel = ViewModelProvider(this).get(MessageViewModel::class.java)
        messageList = ArrayList()

        setupRecyclerViewAndAdapter()

        msgViewModel.readMessage(messageList, messageAdapter)

        val msgPhotoPicker : ActivityResultLauncher<String> =
            registerForActivityResult(ActivityResultContracts.GetContent(),
                ActivityResultCallback<Uri>(){
                //TODO Push it to storage. Call on for it.
            })

        msgPhotoPickerImageView.setOnClickListener(View.OnClickListener {
            msgPhotoPicker.launch("image/*")
        })

//        sendBtn.addTextChangedListener(object : TextWatcher {
//            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
//
//            override fun onTextChanged(msgText: CharSequence?, p1: Int, p2: Int, p3: Int) {
//                if (msgText.toString().trim().isNotEmpty())
//                    sendBtn.isEnabled
//                else
//                    !sendBtn.isEnabled
//            }
//
//            override fun afterTextChanged(p0: Editable?) {}
//
//        })

        sendBtn.setOnClickListener(View.OnClickListener {
            val sdf : SimpleDateFormat = SimpleDateFormat("HH:mm")
            val timeOfMessage : String = sdf.format(Date())

            var msg : Message = Message(messageEditText.text.toString(), "SENDER_NAME via Auth", "IMG PIC via storage", timeOfMessage)
            msgViewModel.addMessage(msg)

            messageEditText.setText("")
        })
    }

    private fun setupRecyclerViewAndAdapter() {
        recyclerView = findViewById(R.id.msgRecyclerView)
        layoutManager = LinearLayoutManager(this)
        messageAdapter = MessageAdapter(messageList)

        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = messageAdapter
    }
}