package com.example.myapplication

import android.content.Context
import android.text.format.DateFormat
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

class TaskAdapter(
   private val context: Context,
    private var taskList: OrderedRealmCollection<Task>?,
   private var listener: OnItemSwipeListener,// ---------追加----------
    private val autoUpdate: Boolean
) :
RealmRecyclerViewAdapter<Task,TaskAdapter.ViewHolder>(taskList,autoUpdate){
//private var listener: ((Int?) -> Unit)? = null
//    fun setOnItemSwipeListener(listener:(Int?) -> Unit){
//        this.listener = listener
//    }
    override fun getItemCount(): Int = taskList?.size ?: 0


    init {
        setHasStableIds(true)
    }


    class ViewHolder(cell: View) : RecyclerView.ViewHolder(cell) {
        val title: TextView = cell.findViewById(R.id.contentTextView)
        val date:  TextView = cell.findViewById(R.id.dateTextView)

    }
//
//
    override fun onCreateViewHolder( parent: ViewGroup, viewType: Int):
            TaskAdapter.ViewHolder{

        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.list_item,parent,false)

        return ViewHolder(view)


    }

    class TaskViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val container : LinearLayout = view.container  // ---------追加----------
        //val imageView: ImageView = view.imageView
        val contentTextView: TextView = view.contentTextView
        val dateTextView: TextView = view.dateTextView
    }
//
    override fun onBindViewHolder( holder: TaskAdapter.ViewHolder,position: Int){

        val task: Task? = getItem(position)
    listener.onItemSwipe(Task())
    val id = task?.id
        holder.title.text = task?.title
        holder.date.text = DateFormat.format("yyyy/mm/dd", task?.date)
    //holder.itemView.set
    }

//    override fun getItemId(position: Int): Long{
//        return getItem(position)?.id ?:0
//    }

    interface OnItemSwipeListener {
        fun onItemSwipe(item: Task)
    }
    interface test{
        fun getId(item: Task)
    }










}




