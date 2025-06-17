package org.geeksforgeeks.demo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.jokechatbot.Model
import com.example.jokechatbot.R


class Adapter(private val list: ArrayList<Model>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val view: View

        // below code is to switch our
        // layout type along with view holder.
        when (viewType) {
            0 -> {

                // below line we are inflating user message layout.
                view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_user_messages, parent, false)
                return UserViewHolder(view)
            }

            else -> {

                // below line we are inflating bot message layout.
                view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_bot_messages, parent, false)
                return BotViewHolder(view)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        // this method is use to set data
        // to our layout file.
        val modal: Model = list[position]
        when (modal.sender) {
            "user" -> (holder as UserViewHolder).userTV.text = modal.message
            "bot" -> (holder as BotViewHolder).botTV.text = modal.message
        }
    }

    override fun getItemCount(): Int = list.size


    override fun getItemViewType(position: Int): Int {

        // below line of code is to set position.
        return when (list[position].sender) {
            "user" -> 0
            "bot" -> 1
            else -> -1
        }
    }

    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var userTV: TextView = itemView.findViewById(R.id.idTVUser)
    }

    class BotViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var botTV: TextView = itemView.findViewById(R.id.idTVBot)
    }
}