package arunkbabu90.chat

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_room.view.*

class RoomAdapter(private val rooms: ArrayList<String>,
                  private val itemClickListener: (String) -> Unit)
    : RecyclerView.Adapter<RoomAdapter.RoomViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomViewHolder {
        val view = parent.inflate(R.layout.item_room)
        return RoomViewHolder(view)
    }

    override fun onBindViewHolder(holder: RoomViewHolder, position: Int) {
        holder.bind(roomName = rooms[position], itemClickListener)
    }

    override fun getItemCount(): Int = rooms.size

    class RoomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(roomName: String, itemClickListener: (String) -> Unit) {
            itemView.itemRoom_name.text = roomName
            itemView.setOnClickListener { itemClickListener(roomName) }
        }
    }
}