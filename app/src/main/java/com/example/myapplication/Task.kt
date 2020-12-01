package com.example.myapplication

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.sql.Time
import java.util.*
//テーブル定義
open class Task : RealmObject() {
    @PrimaryKey
    var id : Int = 0
    var title: String = ""
    var date: Date = Date()
    var time: String = ""
}