package com.example.myapplication

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import io.realm.Realm
import io.realm.RealmResults
import java.lang.IllegalArgumentException
import java.sql.Time
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


class AlarmBroadcastReceiver : BroadcastReceiver() {
    private val realm: Realm by lazy {
        Realm.getDefaultInstance()
    }

    override fun onReceive(context: Context, intent: Intent) {
        val mainIntent = Intent(context, EntryActivity::class.java)
            .putExtra("onReceive",true)
            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(mainIntent)

    }
    fun update(id: String, content: Int) {
        realm.executeTransaction {
            var data = realm.where(Task::class.java).equalTo("time", id).findFirst()
            data!!.flag = true

        }
    }



    public fun String.toDate(pattern: String = "yyyy/MM/dd"): Date? {
        return try {
            SimpleDateFormat(pattern).parse(this)
        } catch (e: IllegalArgumentException) {
            return null
        } catch (e: ParseException) {
            return null
        }
    }
}