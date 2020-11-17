package com.example.myapplication

import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import io.realm.OrderedRealmCollection
import io.realm.RealmRecyclerViewAdapter
import kotlinx.android.synthetic.*

class TaskAdapter(data: OrderedRealmCollection<Task>) :
RealmRecyclerViewAdapter<Task,TaskAdapter.ViewHolder>(data,true){


    init {
        setHasStableIds(true)
    }


    class ViewHolder(cell: View) : RecyclerView.ViewHolder(cell) {
        val title: TextView = cell.findViewById(android.R.id.text1)
        val date:  TextView = cell.findViewById(android.R.id.text2)
    }
//
//
    override fun onCreateViewHolder( parent: ViewGroup, viewType: Int):
            TaskAdapter.ViewHolder{
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(android.R.layout.simple_list_item_2,parent,false)

        return ViewHolder(view)


    }
//
    override fun onBindViewHolder( holder: TaskAdapter.ViewHolder,position: Int){
        val Task: Task? = getItem(position)
        holder.title.text = Task?.title
        holder.date.text = DateFormat.format("yyyy/mm/dd", Task?.date)
    }

    override fun getItemId(position: Int): Long{
        return getItem(position)?.id ?:0
    }


}