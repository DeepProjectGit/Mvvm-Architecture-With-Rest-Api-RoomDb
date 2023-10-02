package com.example.task2.data.repository

import androidx.annotation.WorkerThread
import com.example.task2.data.api_service.ApiService
import com.example.task2.data.dao.UserDao
import com.example.task2.data.entity.UserEntity
import com.example.task2.data.resposnse.ResponseItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

import javax.inject.Singleton

@Singleton
class MainRepository(val apiService: ApiService,val userDao: UserDao) {

    @WorkerThread
    suspend fun insertAll(userEntityList: List<UserEntity>) = withContext(Dispatchers.IO){
        userDao.insertAll(entities = userEntityList)
    }

    @WorkerThread
    suspend fun delete(userEntityList:UserEntity) = withContext(Dispatchers.IO){
        userDao.delete(userEntityList)
    }

    @WorkerThread
    suspend fun update(userEntityList:UserEntity) = withContext(Dispatchers.IO){
        userDao.update(userEntityList)
    }

    @WorkerThread
    suspend fun allDelete() = withContext(Dispatchers.IO){
        userDao.deleteAll()
    }

    val getList:Flow<List<UserEntity>> =userDao.getUserList()

    fun getCount():Int=  userDao.getCount()
    fun getUserById(id:Long):UserEntity? =  userDao.getItemById(id)

    fun getUserListFromApi():Flow<List<ResponseItem?>> = flow {
        emit(apiService.getUserList())
    }.flowOn(Dispatchers.IO)



}