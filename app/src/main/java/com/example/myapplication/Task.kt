package com.example.myapplication

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.sql.Time
import java.util.*
//テーブル定義
open class Task : RealmObject() {
    @PrimaryKey open var id : String = UUID.randomUUID().toString()
    var title: String = ""
    var date: Date = Date()
    var time: String = ""
    //履歴画面時に必要
    var flag: Boolean = false

}