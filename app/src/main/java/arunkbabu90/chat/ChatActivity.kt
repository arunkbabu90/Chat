package arunkbabu90.chat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_chat.*

class ChatActivity : AppCompatActivity(), ChildEventListener {
    private lateinit var chatRoot: DatabaseReference
    private lateinit var chatAdapter: ChatAdapter
    private val messages: ArrayList<Message> = ArrayList()
    private var userId = ""
    private var roomName = ""

    companion object {
        const val ROOM_NAME_EXTRA_KEY = "key_room_name_extra"
        const val USER_ID_EXTRA_KEY = "key_user_id_extra"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        userId = intent.getStringExtra(USER_ID_EXTRA_KEY) ?: ""
        roomName = intent.getStringExtra(ROOM_NAME_EXTRA_KEY) ?: ""

        tv_roomTitle.text = roomName

        chatAdapter = ChatAdapter(messages, userId)
        rv_chat.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rv_chat.adapter = chatAdapter

        chatRoot = Firebase.database.reference.child(roomName)
        chatRoot.addChildEventListener(this)

        btn_send.setOnClickListener {
            val message: String = et_message.text.toString()
            val msgMap = hashMapOf(
                Constants.FIELD_KEY_NAME to userId,
                Constants.FIELD_KEY_MESSAGE to message,
                "timestamp" to ServerValue.TIMESTAMP
            )
            chatRoot.push().updateChildren(msgMap)
        }
    }

    private fun loadMessages(snapshot: DataSnapshot) {
        val message = snapshot.getValue(Message::class.java) ?: Message()
        messages.add(message)

        chatAdapter.notifyDataSetChanged()
        rv_chat.smoothScrollToPosition(messages.size)
    }

    override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
        loadMessages(snapshot)
    }

    override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) { }

    override fun onChildRemoved(snapshot: DataSnapshot) { }

    override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) { }

    override fun onCancelled(error: DatabaseError) { }
}