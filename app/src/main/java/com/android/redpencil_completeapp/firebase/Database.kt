package com.android.redpencil_completeapp.firebase

import android.net.Uri
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.android.redpencil_completeapp.adapter.MessageAdapter
import com.android.redpencil_completeapp.models.Message
import com.android.redpencil_completeapp.ui.MainActivity
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask

class Database() {
private val firebaseDatabase : FirebaseDatabase
private val messagesDatabaseReference : DatabaseReference
private val firebaseStorage : FirebaseStorage
private val msgImageReference : StorageReference

//Initializer Block.
    init {
        firebaseDatabase = FirebaseDatabase.getInstance()
        messagesDatabaseReference = firebaseDatabase.reference.child("Messages")
        firebaseStorage = FirebaseStorage.getInstance()
        msgImageReference = firebaseStorage.reference.child("Chat_Photos")
    }

    public fun addMessage(message : Message) : Unit {
        messagesDatabaseReference.push().setValue(message)
    }

    public fun addPhoto(
        uriSelectedImage: Uri,
        timeOfMessage: String,
        progressBar: ProgressBar,
        userName: String
    ) {
        //Upload File to Storage
        val photoRef : StorageReference = msgImageReference.child(uriSelectedImage.lastPathSegment!!)
        val uploadTask : UploadTask = photoRef.putFile(uriSelectedImage)

        //Get Download Url
        val urlTask : Task<Uri> = uploadTask.continueWithTask { task ->
            if (!task.isSuccessful) {
                task.exception?.let {
                    throw it
                }
            }
            photoRef.downloadUrl
        }.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val downloadUri = task.result
                val message = Message(null, userName, downloadUri.toString(), timeOfMessage)
                messagesDatabaseReference.push().setValue(message)
            }
            else {
                //Handle Failures
            }
        }
        progressBar.visibility = View.GONE
    }

    public fun readMessage(
        messageList: ArrayList<Message>,
        messageAdapter: MessageAdapter,
        progressBar: ProgressBar,
        recyclerView: RecyclerView
    ) : Unit {

        val msgChildEventListener : ChildEventListener = object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                //!! Null safety operator.
                var msg : Message = snapshot.getValue(Message::class.java)!!
                messageList.add(msg)
                messageAdapter.notifyItemInserted(messageList.size - 1)

                progressBar.visibility = View.GONE
                recyclerView.scrollToPosition(messageList.size - 1)
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                TODO("Not yet implemented")
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
//                TODO("Not yet implemented")
//                messageAdapter.notifyDataSetChanged()
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                TODO("Not yet implemented")
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        }

        messagesDatabaseReference.addChildEventListener(msgChildEventListener)
    }

    public fun deleteMessage(
        viewClickedId: Int,
        messageAdapter: MessageAdapter,
        messageList: ArrayList<Message>
    ) {

        if (messageList[viewClickedId].messageText == null) {

            /**
             * SEE WHY IS THIS NOT WORKING
             */

            val storageRef : StorageReference  = firebaseStorage.getReferenceFromUrl(messageList[viewClickedId].photoUrl!!)

            storageRef.delete().addOnSuccessListener { OnSuccessListener<Void> {} }

            val query : Query = messagesDatabaseReference.orderByChild("time").equalTo(messageList[viewClickedId].time!!)

            query.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (qSnapshot in snapshot.children ) {
                        qSnapshot.ref.removeValue()

                        Toast.makeText(messageAdapter.getContext(), "Deleted", Toast.LENGTH_SHORT).show()
                    }

                    messageList.removeAt(viewClickedId)
                    messageAdapter.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })
        }
        else {
            val query : Query = messagesDatabaseReference.orderByChild("messageText").equalTo(messageList[viewClickedId].messageText!!)

            query.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (qSnapshot in snapshot.children ) {
                        qSnapshot.ref.removeValue()
                    }

                    messageList.removeAt(viewClickedId)
                    messageAdapter.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })
        }
    }

}