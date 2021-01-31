package com.example.myapplication

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import android.widget.DatePicker
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment
import io.realm.Realm
import java.lang.IllegalArgumentException
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class TimeAlertDialog : DialogFragment(){


    interface  Listener {
        fun getUp()
        fun snooze()
    }
    private var listener: Listener? = null

    override fun onAttach(context: Context) {

        super.onAttach(context)
        when(context){
            is Listener -> listener = context
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
            val builder = AlertDialog.Builder(requireActivity())
            builder.setMessage("時間になりました！")
        val format1 = SimpleDateFormat("yyyy/MM/dd")
        val format2 = SimpleDateFormat("HH:mm")
        val date = Date()

        val time = Date()
        var realm = Realm.getDefaultInstance()

            builder.setPositiveButton("始める"){dialog,which ->
                listener?.getUp()



            }
        builder.setNegativeButton("あと５分延長") {dialog,which ->
            listener?.snooze()
        }

            return builder.create()
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


class DatePickerFragment : DialogFragment(),
        DatePickerDialog.OnDateSetListener{

    interface OnDateSelectedListener{
        fun onSelected(year: Int,month: Int,date:Int)
    }

    private var listener: OnDateSelectedListener? = null
    override fun onAttach(context: Context) {
        super.onAttach(context)
        when(context){
            is OnDateSelectedListener -> listener = context
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val date = c.get(Calendar.DAY_OF_MONTH)
            return DatePickerDialog(requireActivity(),this,year,month,date)
    }


    override fun onDateSet(p0: DatePicker?, year: Int, month: Int, dayOfMonth: Int){
        listener?.onSelected(year,month,dayOfMonth)
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


class TimePickerFragment : DialogFragment(),
        TimePickerDialog.OnTimeSetListener{

    interface OnTimeSelectedListener{
        fun onSelected(hourOfDay: Int,minute:Int)
    }

    private var listener: OnTimeSelectedListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        when(context){
            is OnTimeSelectedListener -> listener = context
        }
    }


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val c = Calendar.getInstance()
        val hour = c.get(Calendar.HOUR_OF_DAY)
        val minute = c.get(Calendar.MINUTE)
        return TimePickerDialog(context,this,hour,minute,true)
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int){
        listener?.onSelected(hourOfDay, minute)
    }

}