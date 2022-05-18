package com.android.redpencil_completeapp.models

import java.io.Serializable

data class Message(
    var messageText : String,
    var senderName : String,
    var photoUrl : String,
    var time : String)
//See using : Serializable