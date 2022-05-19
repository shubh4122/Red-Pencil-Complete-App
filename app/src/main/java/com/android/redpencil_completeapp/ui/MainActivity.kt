package com.android.redpencil_completeapp.ui

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.*
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
    private lateinit var msgViewModel : MessageViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var messageEditText : EditText = findViewById(R.id.messageEditText)
        val msgPhotoPickerImageView : ImageView = findViewById(R.id.photoPickerButton)
        val sendBtn : Button = findViewById(R.id.sendButton)
        val progressBar : ProgressBar = findViewById(R.id.progressBar)
        msgViewModel = ViewModelProvider(this).get(MessageViewModel::class.java)
        messageList = ArrayList()

        setupRecyclerViewAndAdapter()

        msgViewModel.readMessage(messageList, messageAdapter, progressBar, recyclerView)

        val msgPhotoPicker : ActivityResultLauncher<String> =
            registerForActivityResult(ActivityResultContracts.GetContent(),
                ActivityResultCallback<Uri>(){
                    val sdf : SimpleDateFormat = SimpleDateFormat("HH:mm")
                    val timeOfMessage : String = sdf.format(Date())
                    msgViewModel.addPhoto(it, timeOfMessage)
            })

        msgPhotoPickerImageView.setOnClickListener(View.OnClickListener {
            msgPhotoPicker.launch("image/*")
        })


        sendBtn.setOnClickListener(View.OnClickListener {
            if (messageEditText.text.toString().isNotEmpty()) {
                val sdf : SimpleDateFormat = SimpleDateFormat("HH:mm")
                val timeOfMessage : String = sdf.format(Date())

                var msg : Message = Message(messageEditText.text.toString(), "SENDER_NAME via Auth", null, timeOfMessage)
                msgViewModel.addMessage(msg)

                messageEditText.setText("")
            }
        })
    }

    private fun setupRecyclerViewAndAdapter() {
        recyclerView = findViewById(R.id.msgRecyclerView)
        layoutManager = LinearLayoutManager(this)
        messageAdapter = MessageAdapter(messageList, this)

        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = messageAdapter
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        super.onContextItemSelected(item)
        when(item.itemId) {
//            101 -> Toast.makeText(this, "Edit "+item.groupId.toString(), Toast.LENGTH_SHORT).show()
            102 -> msgViewModel.deleteMessage(item.groupId, messageAdapter, messageList)
        }
        return true
    }
}