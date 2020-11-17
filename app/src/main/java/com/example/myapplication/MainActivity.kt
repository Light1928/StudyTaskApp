package com.example.myapplication

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.LinearLayoutManager
import io.realm.Realm
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.activity_entry.*
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.math.E

class MainActivity : AppCompatActivity() {
    private lateinit var realm: Realm



    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)


        setContentView(R.layout.activity_main)

        TestButton.setOnClickListener { onTestButtonTapped(it) }

        realm = Realm.getDefaultInstance()
        list.layoutManager = LinearLayoutManager(this)
        val Task = realm.where<Task>().findAll()
        val adapter = TaskAdapter(Task)
        list.adapter = adapter
    }

    fun onTestButtonTapped(view: View?) {
        val intent = Intent(this, EntryActivity::class.java)
        startActivity(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }



}
