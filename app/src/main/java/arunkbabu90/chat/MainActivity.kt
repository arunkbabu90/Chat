package arunkbabu90.chat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), ValueEventListener {
    private lateinit var root: DatabaseReference
    private lateinit var roomAdapter: RoomAdapter
    private val rooms: ArrayList<String> = ArrayList()
    private var id = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        root = Firebase.database.reference.root
        root.orderByKey().addValueEventListener(this)

        // Ask for the user id
        askForUserName()

        roomAdapter =  RoomAdapter(rooms) { roomName -> startChat(roomName) }
        val lm = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true)
        lm.stackFromEnd = true
        rv_rooms.layoutManager = lm
        rv_rooms.adapter = roomAdapter

        btn_create_room.setOnClickListener {
            val roomName = et_room_name.text.toString().trim()
            val roomMap = hashMapOf<String, Any>(
                roomName to ""
            )
            root.updateChildren(roomMap)
        }
    }

    private fun startChat(roomName: String) {
        val i = Intent(this, ChatActivity::class.java)
        i.putExtra(ChatActivity.ROOM_NAME_EXTRA_KEY, roomName)
        i.putExtra(ChatActivity.USER_ID_EXTRA_KEY, id)
        startActivity(i)
    }

    private fun askForUserName() {
        val inputField = EditText(this)
        with(AlertDialog.Builder(this)) {
            setTitle("Enter Name:")
            setView(inputField)
            setPositiveButton("OK") { _, _ -> id = inputField.text.toString()}
            setNegativeButton("Cancel") { dialog, _ -> dialog.dismiss() }
            show()
        }
    }

    override fun onDataChange(snapshot: DataSnapshot) {
        val roomNames: ArrayList<String> = ArrayList()
        snapshot.children.iterator().forEach { dataSnapshot ->
            roomNames.add((dataSnapshot).key ?: "")
        }

        rooms.clear()
        rooms.addAll(roomNames)
        roomAdapter.notifyDataSetChanged()
    }

    override fun onCancelled(error: DatabaseError) { }
}