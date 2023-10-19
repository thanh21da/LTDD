package com.example.myapplicationgiuaky

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myapplicationgiuaky.R
import com.example.myapplicationgiuaky.database.USER_DATABASE
import com.example.myapplicationgiuaky.Model.user

import kotlinx.android.synthetic.main.activity_new_user.*
import kotlinx.android.synthetic.main.user_itemm.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class NEW_USER : AppCompatActivity(), CoroutineScope {

    private var userDB: USER_DATABASE ?= null

    private lateinit var mJob: Job
    override val coroutineContext: CoroutineContext
        get() = mJob + Dispatchers.Main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_user)

        mJob = Job()

        userDB = USER_DATABASE.getDatabase(this)

        button_save.setOnClickListener {
            launch {
                val strUsername: String = add_username.text.toString()
                val strPassword: String = add_password.text.toString()
                val strEmail: String = add_email.text.toString()
                val strPhone: String = add_phone.text.toString()

                userDB?.userDao()?.insert(user(username = strUsername, password = strPassword, email = strEmail, phone = strPhone))

                finish()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        mJob.cancel()
    }
}
