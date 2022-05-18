package com.android.redpencil_completeapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.redpencil_completeapp.R
import com.android.redpencil_completeapp.models.Message

class MessageAdapter(private val messageList : ArrayList<Message>) :
    RecyclerView.Adapter<MessageAdapter.MessageViewHolder>() {
    inner class MessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val senderNameTextView : TextView = itemView.findViewById(R.id.senderName)
        val messageTextView : TextView = itemView.findViewById(R.id.message)
        val timeTextView : TextView = itemView.findViewById(R.id.time)
        val messageImageView : ImageView = itemView.findViewById(R.id.messageImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        var view : View = LayoutInflater.from(parent.context).inflate(R.layout.message_item, parent, false)
        return MessageViewHolder(view)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val currentMessage = messageList[position]
        holder.senderNameTextView.text = currentMessage.senderName
        holder.messageTextView.text = currentMessage.messageText
        holder.timeTextView.text = currentMessage.time
//        holder.messageImageView.setImageDrawable()
        //add image using glide. see
    }

    override fun getItemCount(): Int = messageList.size

}