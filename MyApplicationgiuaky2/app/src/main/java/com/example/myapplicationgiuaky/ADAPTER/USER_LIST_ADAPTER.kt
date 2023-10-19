package com.example.myapplicationgiuaky.ADAPTER

import com.example.myapplicationgiuaky.Model.user

import com.example.myapplicationgiuaky.database.USER_DATABASE

import androidx.recyclerview.widget.RecyclerView


import kotlinx.android.synthetic.main.activity_new_user.*
import kotlinx.android.synthetic.main.user_itemm.view.*


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myapplicationgiuaky.R

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


class USER_LIST_ADAPTER internal constructor(context: Context, val userDB: USER_DATABASE) : RecyclerView.Adapter<USER_LIST_ADAPTER.UserViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var users = emptyList<user>()

    private val job = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + job)

    internal fun setNotes(users: List<user>) {
        this.users = users
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val itemView = inflater.inflate(R.layout.user_itemm, parent, false)
        return UserViewHolder (itemView)
    }

    override fun getItemCount(): Int {
        return users.size
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val currentNote = users[position]

        holder.usernameItemView.text = currentNote.username
        holder.passwordItemView.text = currentNote.password
        holder.emailItemView.text = currentNote.email
        holder.phoneItemView.text = currentNote.phone
        holder.deleteItemView.setOnClickListener {
            uiScope.launch {
                // Delete currentNote
                userDB?.userDao()?.delete(currentNote)

                // Get all noted again
                users = userDB?.userDao()?.getAllNotes()
                notifyDataSetChanged()
            }
        }
    }

    inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val usernameItemView = itemView.username
        val passwordItemView = itemView.password
        val emailItemView = itemView.email
        val phoneItemView = itemView.phone
        val deleteItemView = itemView.button_delete
    }
}
