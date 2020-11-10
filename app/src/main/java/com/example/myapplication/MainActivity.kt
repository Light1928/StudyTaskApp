package com.example.myapplication

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_entry.*
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.math.E

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        TestButton.setOnClickListener{onTestButtonTapped(it)}
    }

    fun onTestButtonTapped(view: View?){
        val intent = Intent(this,EntryActivity::class.java)
        startActivity(intent)
    }




}
