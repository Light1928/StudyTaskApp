package com.example.myapplication

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class AlarmBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val mainIntent = Intent(context, EntryActivity::class.java)
            .putExtra("onReceie",true)
            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(mainIntent)
        Toast.makeText(context,"アラームを受信しました",Toast.LENGTH_SHORT)
            .show()

    }
}
