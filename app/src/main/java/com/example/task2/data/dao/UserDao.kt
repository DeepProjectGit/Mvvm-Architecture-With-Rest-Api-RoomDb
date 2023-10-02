package com.example.task2.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.task2.data.entity.UserEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(entities:List<UserEntity>)

    @Query("SELECT * FROM user")
    fun getUserList(): Flow<List<UserEntity>>

    @Delete
    suspend fun  delete(userEntity: UserEntity)

    @Query("DELETE FROM user")
    suspend fun deleteAll()

    @Query("SELECT Count(*) FROM user")
    fun getCount():Int

    @Update
    suspend fun  update(userEntity: UserEntity)

    @Query("SELECT * FROM user WHERE id= :itemId")
    fun getItemById(itemId:Long):UserEntity?
}