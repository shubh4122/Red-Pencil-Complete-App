package com.android.redpencil_completeapp.ui

import android.app.Application
import android.net.Uri
import android.widget.ProgressBar
import androidx.lifecycle.AndroidViewModel
import androidx.recyclerview.widget.RecyclerView
import com.android.redpencil_completeapp.adapter.MessageAdapter
import com.android.redpencil_completeapp.firebase.Database
import com.android.redpencil_completeapp.models.Message

class MessageViewModel(application: Application) : AndroidViewModel(application) {
    private val msgDatabase : Database = Database()
    //See whats better practice, using init block or initializing in global field

    public fun addMessage(msg : Message) {
        msgDatabase.addMessage(msg)
    }

    public fun addPhoto(uri: Uri, timeOfMessage: String, progressBar: ProgressBar) {
        msgDatabase.addPhoto(uri, timeOfMessage, progressBar)
    }

    public fun readMessage(
        msgList: ArrayList<Message>,
        msgAdapter: MessageAdapter,
        progressBar: ProgressBar,
        recyclerView: RecyclerView
    ) {
        msgDatabase.readMessage(msgList, msgAdapter, progressBar, recyclerView)
    }

    public fun deleteMessage(
        viewClickedId: Int,
        messageAdapter: MessageAdapter,
        messageList: ArrayList<Message>
    ) {
        msgDatabase.deleteMessage(viewClickedId, messageAdapter, messageList)
    }
}