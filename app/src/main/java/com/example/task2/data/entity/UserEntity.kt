package com.example.task2.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class UserEntity (
    @PrimaryKey(autoGenerate = true)
    val id:Int=0,
    val resId:Int,
    val title:String,
    val body:String,
    val userId:Int,
)