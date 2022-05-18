package com.android.redpencil_completeapp.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.android.redpencil_completeapp.adapter.MessageAdapter
import com.android.redpencil_completeapp.firebase.Database
import com.android.redpencil_completeapp.models.Message

class MessageViewModel(application: Application) : AndroidViewModel(application) {
    private val msgDatabase : Database = Database()
    //See whats better practice, using init block or initializing in global field

    public fun addMessage(msg : Message) {
        msgDatabase.addMessage(msg)
    }

    public fun readMessage(msgList: ArrayList<Message>, msgAdapter: MessageAdapter) {
        msgDatabase.readMessage(msgList, msgAdapter)
    }
}