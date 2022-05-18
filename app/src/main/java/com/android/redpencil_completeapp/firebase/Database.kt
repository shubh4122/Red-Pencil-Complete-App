package com.android.redpencil_completeapp.firebase

import com.android.redpencil_completeapp.adapter.MessageAdapter
import com.android.redpencil_completeapp.models.Message
import com.google.firebase.database.*

class Database() {
private val firebaseDatabase : FirebaseDatabase
private val messagesDatabaseReference : DatabaseReference

//Initializer Block.
    init {
        firebaseDatabase = FirebaseDatabase.getInstance()
        messagesDatabaseReference = firebaseDatabase.reference.child("Messages")
    }

    public fun addMessage(message : Message) : Unit {
        messagesDatabaseReference.push().setValue(message)
    }

    public fun readMessage(messageList : ArrayList<Message>, messageAdapter : MessageAdapter) : Unit {

        val msgChildEventListener : ChildEventListener = object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                var msg : Message? = snapshot.getValue(Message::class.java)
                messageAdapter.notifyItemInserted(messageList.size - 1)
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                TODO("Not yet implemented")
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                TODO("Not yet implemented")
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
}