package com.example.myapplication

import android.content.Intent
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.realm.Realm
import io.realm.RealmObject.deleteFromRealm
import io.realm.RealmQuery
import io.realm.RealmResults
import io.realm.Sort
import io.realm.kotlin.where
import io.realm.log.RealmLog.remove
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    private lateinit var adapter: TaskAdapter
    private val realm: Realm by lazy {
        Realm.getDefaultInstance()
    }
    private val swipeToDismissTouchHelper by lazy {
        getSwipeToDismissTouchHelper(adapter)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)

//スワイプ

        setContentView(R.layout.activity_main)
        val taskList = readAll()
        TestButton.setOnClickListener { onTestButtonTapped(it) }

        // realm = Realm.getDefaultInstance()
        //  list.layoutManager = LinearLayoutManager(this)
        //表示処理？
        //val task = realm.where<Task>().findAll()
        adapter = TaskAdapter(this, taskList, true)


        list.setHasFixedSize(true)
        list.layoutManager = LinearLayoutManager(this)
        list.adapter = adapter
        swipeToDismissTouchHelper.attachToRecyclerView(list)

    }
    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }

    fun delete(id: String) {
        realm.executeTransaction {
            val task = realm.where(Task::class.java).equalTo("id", id).findFirst()
                ?: return@executeTransaction
            task.deleteFromRealm()
        }
    }

    fun delete(task: Task) {
        realm.executeTransaction {
            task.deleteFromRealm()
        }
    }



    fun update(id: String, content: Int) {
        realm.executeTransaction {
            val task = realm.where(Task::class.java).equalTo("id", id).findAll()
                ?: return@executeTransaction

        }
    }

    fun update(task: Task, content: Int) {
        realm.executeTransaction {
            task.id = content
        }
    }


    fun readAll(): RealmResults<Task> {
        return realm.where(Task::class.java).findAll()
    }


    fun onTestButtonTapped(view: View?) {
        val intent = Intent(this, EntryActivity::class.java)
        startActivity(intent)
    }


    fun getSwipeToDismissTouchHelper(adapter: TaskAdapter) =
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.ACTION_STATE_IDLE,
            ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {


                //連番とリストの配置が同じなら削除できるようにした
              realm.executeTransaction {
                    val task = realm.where(Task::class.java).equalTo("id",viewHolder.adapterPosition).findFirst()
                        ?: return@executeTransaction
                    task.deleteFromRealm()
                }

                adapter.notifyItemRemoved(viewHolder.adapterPosition)
                Toast.makeText(applicationContext, "を削除しました", Toast.LENGTH_SHORT).show()

            }

            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {
                super.onChildDraw(
                    c,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive
                )
                val itemView = viewHolder.itemView
                val background = ColorDrawable(Color.RED)


                //アイコンよう
//                val deleteIcon = AppCompatResources.getDrawable(
//                    this@MainActivity,
//                    R.drawable.ic_baseline_delete_36
//                )
//                val iconMarginVertical =
//                    (viewHolder.itemView.height - deleteIcon!!.intrinsicHeight) / 2
//
//                deleteIcon.setBounds(
//                    itemView.left + iconMarginVertical,
//                    itemView.top + iconMarginVertical,
//                    itemView.left + iconMarginVertical + deleteIcon.intrinsicWidth,
//                    itemView.bottom - iconMarginVertical
//                )
                background.setBounds(
                    itemView.left,
                    itemView.top,
                    itemView.right + dX.toInt(),
                    itemView.bottom
                )
                background.draw(c)
//                deleteIcon.draw(c)
            }
        })
}
//    fun delete(id: Long) {
//        realm.executeTransaction {
//            val task = realm.where(Task::class.java).equalTo("id", id).findFirst()
//                ?: return@executeTransaction
//            task.deleteFromRealm()
//        }
//    }
//
//    fun delete(task: Task) {
//        realm.executeTransaction {
//            task.deleteFromRealm()
//        }
//    }
//
//    fun readAll(): RealmResults<Task> {
//        return realm.where(Task::class.java).findAll()
//    }
//
//
//    fun onTestButtonTapped(view: View?) {
//        val intent = Intent(this, EntryActivity::class.java)
//        startActivity(intent)
//    }
//
//    override fun onDestroy() {
//        super.onDestroy()
//        realm.close()
//    }
//
//
//    fun getSwipeToDismissTouchHelper(adapter: TaskAdapter) =
//        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
//            ItemTouchHelper.ACTION_STATE_IDLE,
//            ItemTouchHelper.RIGHT
//        ) {
//            override fun onMove(
//                recyclerView: RecyclerView,
//                viewHolder: RecyclerView.ViewHolder,
//                target: RecyclerView.ViewHolder
//            ): Boolean {
//                return false
//            }
//
//            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
//
//                val id = intent?.getLongExtra("id", -1L)
//                realm.executeTransaction {
//                    val task = realm.where(Task::class.java).equalTo("id", id).findFirst()
//                        ?: return@executeTransaction
//                    task.deleteFromRealm()
//                }
//                Toast.makeText(applicationContext, "を削除しました", Toast.LENGTH_SHORT).show()
//
//            }
//
//            override fun onChildDraw(
//                c: Canvas,
//                recyclerView: RecyclerView,
//                viewHolder: RecyclerView.ViewHolder,
//                dX: Float,
//                dY: Float,
//                actionState: Int,
//                isCurrentlyActive: Boolean
//            ) {
//                super.onChildDraw(
//                    c,
//                    recyclerView,
//                    viewHolder,
//                    dX,
//                    dY,
//                    actionState,
//                    isCurrentlyActive
//                )
//                val itemView = viewHolder.itemView
//                val background = ColorDrawable(Color.RED)
//
//
//                //アイコンよう
////                val deleteIcon = AppCompatResources.getDrawable(
////                    this@MainActivity,
////                    R.drawable.ic_baseline_delete_36
////                )
////                val iconMarginVertical =
////                    (viewHolder.itemView.height - deleteIcon!!.intrinsicHeight) / 2
////
////                deleteIcon.setBounds(
////                    itemView.left + iconMarginVertical,
////                    itemView.top + iconMarginVertical,
////                    itemView.left + iconMarginVertical + deleteIcon.intrinsicWidth,
////                    itemView.bottom - iconMarginVertical
////                )
//                background.setBounds(
//                    itemView.left,
//                    itemView.top,
//                    itemView.right + dX.toInt(),
//                    itemView.bottom
//                )
//                background.draw(c)
////                deleteIcon.draw(c)
//            }













