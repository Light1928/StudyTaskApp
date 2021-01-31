package com.example.myapplication

import android.content.Context
import android.text.format.DateFormat
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import io.realm.OrderedRealmCollection
import io.realm.RealmRecyclerViewAdapter
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.list_item.view.*
import org.w3c.dom.Text

class TaskAdapter(
   private val context: Context,
    private var taskList: OrderedRealmCollection<Task>?,
  // private var listener: OnItemSwipeListener,// ---------追加----------
    private val autoUpdate: Boolean
) :
RealmRecyclerViewAdapter<Task,TaskAdapter.ViewHolder>(taskList,autoUpdate){

    override fun getItemCount(): Int = taskList?.size ?: 0


    init {
        setHasStableIds(true)
    }


    class ViewHolder(cell: View) : RecyclerView.ViewHolder(cell) {
        val title: TextView = cell.findViewById(R.id.contentTextView)
        val date:  TextView = cell.findViewById(R.id.dateTextView)
        val id:  TextView = cell.findViewById(R.id.idTextView)

    }
//
//
    override fun onCreateViewHolder( parent: ViewGroup, viewType: Int):
            TaskAdapter.ViewHolder{

        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.list_item,parent,false)

        return ViewHolder(view)


    }


    override fun onBindViewHolder( holder: TaskAdapter.ViewHolder,position: Int){

        val task: Task? = taskList?.get(position)
    //デバッグ用
println(task)
    println("******************************AAAAAAAAAAA***"+position)
    val id = task?.id
    //println("*****"+id)

        holder.title.text = task?.title
        holder.date.text = DateFormat.format("yyyy/MM/dd HH:mm", task?.date)
        holder.id.text = task?.id


    }












}




