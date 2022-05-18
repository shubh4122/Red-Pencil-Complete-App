package com.android.redpencil_completeapp.models

import java.io.Serializable

//null initialization is a way to solve empty constructor necessity
data class Message(
    var messageText : String = "",
    var senderName : String = "",
    var photoUrl : String = "",
    var time : String = "")
//See using : Serializable