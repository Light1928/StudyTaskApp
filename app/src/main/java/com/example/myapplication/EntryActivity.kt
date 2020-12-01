package com.example.myapplication

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.format.DateFormat
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import io.realm.Realm
import io.realm.kotlin.createObject
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.activity_entry.*
import java.lang.IllegalArgumentException
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class EntryActivity : AppCompatActivity(), TimeAlertDialog.Listener
    ,DatePickerFragment.OnDateSelectedListener
    ,TimePickerFragment.OnTimeSelectedListener{


    //realm用の変数
    private lateinit var realm: Realm

    override fun onSelected(year: Int, month: Int, date: Int){
        val c = Calendar.getInstance()
        c.set(year,month,date)
        DateText.text = DateFormat.format("yyyy/MM/dd",c)
    }

    override fun onSelected(hourOfDay: Int, minute: Int){
        TimerText.text = "%1$02d:%2$02d".format(hourOfDay, minute)
    }

    override fun getUp(){
        finish()
    }

    override fun snooze() {
       val calendar = Calendar.getInstance()
        calendar.timeInMillis = System.currentTimeMillis()
        calendar.add(Calendar.MINUTE,5)
        setAlarmManager(calendar)
        finish()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        realm = Realm.getDefaultInstance()

//        EntryButton.setOnClickListener{view: View ->
//            realm.executeTransaction{db: Realm ->
//                val maxId = db.where<Task>().max("id")
//                val nextId = (maxId?.toLong() ?: 0L) + 1
//                val task =db.createObject<Task>(nextId)
//                val date = DateText.text.toString().toDate("yyyy/MM/dd")
//                if(date != null) task.date = date
//                task.title = TitleText.text.toString()
//                task.time = TimerText.text.toString()
//
//            }
//        }




        if(intent?.getBooleanExtra("onReceive",false) == true) {
            val dialog = TimeAlertDialog()
            dialog.show(supportFragmentManager,"alert_dialog")
        }

        setContentView(R.layout.activity_entry)


        EntryButton.setOnClickListener {view: View ->
            realm.executeTransaction{db: Realm ->
                val maxId = db.where<Task>().max("id")
                val nextId = (maxId?.toLong() ?: 0L) + 1
                val task =db.createObject<Task>(nextId)
                val date = DateText.text.toString().toDate("yyyy/MM/dd")
                if(date != null) task.date = date
                task.title = TitleText.text.toString()
                task.time = TimerText.text.toString()



                //次回、時刻も表示できるようにする

            }
//            val snackbar = Snackbar.make(view,"追加しました",Snackbar.LENGTH_SHORT)
//                .setAction("戻る"){finish()}
//                .setActionTextColor(Color.YELLOW)
//                .show()




            val date = "${DateText.text} ${TimerText.text}".toDate()
            when {
                date != null -> {
                    val calendar = Calendar.getInstance()
                    calendar.time = date
                    setAlarmManager(calendar)
                    Toast.makeText(
                        this,"アラームをセットしました",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                else -> {
                    Toast.makeText(
                        this,"全て入力してください",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

        }


        CancelButton.setOnClickListener {
//            cancelAlarmManager()
            finish()
        }

        DateText.setOnClickListener{
            val dialog = DatePickerFragment()
            dialog.show(supportFragmentManager,"date_dialog")
        }
        TimerText.setOnClickListener{
            val dialog = TimePickerFragment()
            dialog.show(supportFragmentManager,"time_dialog")
        }



    }




    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }



    private fun setAlarmManager(calendar: Calendar) {
        val am = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(this,AlarmBroadcastReceiver::class.java)
        val pending = PendingIntent.getBroadcast(this,0,intent,0)
        when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP -> {
                val info = AlarmManager.AlarmClockInfo(
                    calendar.timeInMillis,null)
                am.setAlarmClock(info,pending)
            }

            Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT -> {
                am.setExact(AlarmManager.RTC_WAKEUP,calendar.timeInMillis,pending)
            }
            else -> {
                am.set(AlarmManager.RTC_WAKEUP,calendar.timeInMillis,pending)
            }
        }
    }

    private fun cancelAlarmManager(){
        val am = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(this,AlarmBroadcastReceiver::class.java)
        val pending = PendingIntent.getBroadcast(this,0,intent,0)
        am.cancel(pending)
    }

    private fun String.toDate(pattern: String = "yyyy/MM/dd HH:mm"):Date? {
        return try {
            SimpleDateFormat(pattern).parse(this)
        }catch(e:IllegalArgumentException){
            return null
        }catch(e: ParseException){
            return null
        }
    }

}