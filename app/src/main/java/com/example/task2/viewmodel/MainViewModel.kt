package com.example.task2.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.task2.data.entity.UserEntity
import com.example.task2.data.repository.MainRepository
import com.example.task2.data.resposnse.ResponseItem
import com.example.task2.utils.ApiState
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(val mainRepository: MainRepository):ViewModel() {
    private val getUserListState : MutableStateFlow<ApiState<List<ResponseItem?>>> = MutableStateFlow(ApiState.Empty)
    val mGetUserListState :StateFlow<ApiState<List<ResponseItem?>>> = getUserListState

    fun getUserList() = viewModelScope.launch {
        getUserListState.value= ApiState.Loading

        mainRepository.getUserListFromApi().catch {
            e->
            e.printStackTrace()
            getUserListState.value = ApiState.Failure(false,e)
        }.collect{
            getUserListState.value= ApiState.Success(it)
        }
    }

    fun insert(userList:List<UserEntity>){
        viewModelScope.launch {
            mainRepository.insertAll(userList)
        }
    }

    fun update(user:UserEntity){
        viewModelScope.launch {
            mainRepository.update(user)
        }
    }

    fun delete(user:UserEntity){
        viewModelScope.launch {
            mainRepository.delete(user)
        }
    }

    fun count():Int{
        return mainRepository.getCount()
    }

    fun deleteAll(){
        viewModelScope.launch {
            mainRepository.allDelete()
        }
    }

    val getList:LiveData<List<UserEntity>> get() =
        mainRepository.getList.flowOn(Dispatchers.Main).asLiveData(context = viewModelScope.coroutineContext)

    fun getUserById(id:Long):UserEntity ? = mainRepository.getUserById(id)


}