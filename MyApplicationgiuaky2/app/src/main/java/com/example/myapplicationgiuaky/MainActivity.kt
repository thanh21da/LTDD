package com.example.myapplicationgiuaky

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext


import android.content.Intent


import android.text.TextUtils
import com.example.myapplicationgiuaky.ADAPTER.USER_LIST_ADAPTER
import com.example.myapplicationgiuaky.database.USER_DATABASE
import com.example.myapplicationgiuaky.Model.user

import com.example.myapplicationgiuaky.R
import com.example.myapplicationgiuaky.NEW_USER


class MainActivity : AppCompatActivity() , CoroutineScope, View.OnClickListener {

    private var userDB: USER_DATABASE? = null
    private var adapter: USER_LIST_ADAPTER? = null

    private lateinit var mJob: Job
    override val coroutineContext: CoroutineContext
        get() = mJob + Dispatchers.Main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mJob = Job()

        userDB = USER_DATABASE.getDatabase(this)
        adapter = USER_LIST_ADAPTER(MainActivity@this, userDB!!)

        recycler_notes.adapter = adapter
        recycler_notes.layoutManager = LinearLayoutManager(this)

        button_new_user.setOnClickListener(this)
        button_find.setOnClickListener(this)
    }

    override fun onResume() {
        super.onResume()

        getAllNotes()
    }

    override fun onDestroy() {
        super.onDestroy()

        mJob.cancel()
    }

    override fun onClick(v: View?) {
        when(v) {
            button_new_user -> {
                val newUserIntent = Intent(this, NEW_USER::class.java)
                startActivity(newUserIntent)
            }

            button_find -> {
                findNote()
            }
        }
    }

    // Get all notes
    fun getAllNotes() {
        launch {
            val user: List<user>? = userDB?.userDao()?.getAllNotes()
            if (user != null) {
                adapter?.setNotes(user)
            }
        }
    }

    fun findNote() = launch {
        val strFind = edittext_find.text.toString()
        if (!TextUtils.isEmpty(strFind)) {
            // Find if the text is not empty
            val user: user? = userDB?.userDao()?.findNoteByTitle(strFind)
            if (user != null) {
                val notes: List<user> = mutableListOf(user)
                adapter?.setNotes(notes)
            }
        } else {
            // Else get all notes
            getAllNotes()
        }
    }
}