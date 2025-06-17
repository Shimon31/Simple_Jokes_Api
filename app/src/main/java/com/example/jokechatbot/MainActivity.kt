package org.geeksforgeeks.demo

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.jokechatbot.Model
import com.example.jokechatbot.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.json.JSONException

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var button: FloatingActionButton
    private lateinit var editText: EditText

    private lateinit var list: ArrayList<Model>
    private lateinit var adapter: Adapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        button = findViewById(R.id.sendButton)
        editText = findViewById(R.id.messageEditText)

        list = ArrayList()
        adapter = Adapter(list)

        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = this@MainActivity.adapter
        }

        button.setOnClickListener {
            val userMessage = editText.text.toString().trim()

            if (userMessage.isEmpty()) {
                Toast.makeText(this, "Please enter your message..", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            sendMessage(userMessage)
            editText.setText("")
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun sendMessage(userMsg: String) {
        list.add(Model(userMsg, USER_KEY))
        adapter.notifyDataSetChanged()

        val url = "https://official-joke-api.appspot.com/random_joke"

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                try {
                    val setup = response.getString("setup")
                    val punchline = response.getString("punchline")
                    val joke = "$setup\n$punchline"

                    list.add(Model(joke, BOT_KEY))
                    adapter.notifyDataSetChanged()
                } catch (e: JSONException) {
                    e.printStackTrace()
                    list.add(Model("No response", BOT_KEY))
                    adapter.notifyDataSetChanged()
                }
            },
            {
                list.add(Model("Sorry, no response found", BOT_KEY))
                adapter.notifyDataSetChanged()
                Toast.makeText(this, "No response from the bot..", Toast.LENGTH_SHORT).show()
            }
        )

        Volley.newRequestQueue(this).add(jsonObjectRequest)
    }

    companion object {
        const val USER_KEY = "user"
        const val BOT_KEY = "bot"
    }
}
