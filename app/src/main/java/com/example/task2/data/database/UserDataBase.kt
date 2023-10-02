package com.example.task2.data.database


import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.task2.data.dao.UserDao
import com.example.task2.data.entity.UserEntity

@Database(entities = [UserEntity::class], version = 1, exportSchema = false)
abstract class UserDataBase :RoomDatabase() {
    abstract fun userDao():UserDao
}