package arunkbabu90.chat

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_message_lt.view.*
import kotlinx.android.synthetic.main.item_message_rt.view.itemMsgRt_text
import kotlinx.android.synthetic.main.item_message_rt.view.itemMsgRt_time

class ChatAdapter(private val messages: ArrayList<Message>,
                  private val userId: String)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            Message.TYPE_YOU -> ChatViewHolderRt(parent.inflate(R.layout.item_message_rt))
            else -> ChatViewHolderLt(parent.inflate(R.layout.item_message_lt))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val msg: Message = messages[position]

        if (msg.name == userId) {
            (holder as ChatViewHolderRt).bind(msg)
        } else {
            (holder as ChatViewHolderLt).bind(msg)
        }
    }

    override fun getItemViewType(position: Int): Int {
        val uid: String = messages[position].name
        if (uid == userId) {
            return Message.TYPE_YOU
        }
        return super.getItemViewType(position)
    }

    override fun getItemCount(): Int = messages.size

    class ChatViewHolderRt(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(message: Message) {
            itemView.itemMsgRt_text.text = message.msg
        }
    }

    class ChatViewHolderLt(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(message: Message) {
            itemView.itemMsgLt_text.text = message.msg
        }
    }
}