package com.android.redpencil_completeapp.adapter

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.redpencil_completeapp.R
import com.android.redpencil_completeapp.models.Message
import com.android.redpencil_completeapp.ui.MainActivity
import com.bumptech.glide.Glide
import com.squareup.picasso.Picasso
import java.io.File
import java.net.URL

class MessageAdapter(private val messageList : ArrayList<Message>, private val context : Context) :
    RecyclerView.Adapter<MessageAdapter.MessageViewHolder>() {


    inner class MessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnCreateContextMenuListener {
        val senderNameTextView : TextView = itemView.findViewById(R.id.senderName)
        val messageTextView : TextView = itemView.findViewById(R.id.message)
        val timeTextView : TextView = itemView.findViewById(R.id.time)
        val messageImageView : ImageView = itemView.findViewById(R.id.messageImage)

        init {
            itemView.setOnCreateContextMenuListener(this)
        }

        override fun onCreateContextMenu(
            menu: ContextMenu?,
            v: View?,
            menuInfo: ContextMenu.ContextMenuInfo?
        ) {
//            menu?.add(adapterPosition, 101, 0, "Edit")
            menu?.add(adapterPosition, 102, 1, "Delete")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        var view : View = LayoutInflater.from(parent.context).inflate(R.layout.message_item, parent, false)
        return MessageViewHolder(view)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val currentMessage = messageList[position]

        val isPhoto : Boolean = currentMessage.photoUrl != ""

        if (isPhoto) {
            holder.messageTextView.visibility = View.GONE
            holder.messageImageView.visibility = View.VISIBLE

            Picasso.get().load(currentMessage.photoUrl).into(holder.messageImageView)
        }
        else {
            holder.messageTextView.visibility = View.VISIBLE
            holder.messageImageView.visibility = View.GONE
            holder.messageTextView.text = currentMessage.messageText
        }
        holder.senderNameTextView.text = currentMessage.senderName
        holder.timeTextView.text = currentMessage.time
    }

    override fun getItemCount(): Int = messageList.size

}