package com.example.task2.ui.activity

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.whenStarted
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.task2.R
import com.example.task2.data.entity.UserEntity
import com.example.task2.databinding.ActivityMainBinding
import com.example.task2.ui.adaptor.MainAdaptor
import com.example.task2.utils.ApiState
import com.example.task2.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject
import kotlin.reflect.jvm.internal.impl.incremental.components.Position

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val mainViewModel by viewModels<MainViewModel>()
    lateinit var binding: ActivityMainBinding
    var list = mutableListOf<UserEntity>()

    @Inject
    lateinit var mainAdaptor: MainAdaptor
    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvStart.apply {
            this.adapter = mainAdaptor
            this.layoutManager = LinearLayoutManager(this@MainActivity);
            this.itemAnimator = DefaultItemAnimator()
        }



        binding.ivReloadApi.setOnClickListener {
            mainViewModel.deleteAll()
            list.clear()
            mainAdaptor.notifyDataSetChanged()
            mainViewModel.getUserList()
        }
        if(mainViewModel.count()<=0){
            mainViewModel.getUserList()
        }

        lifecycleScope.launch {
            whenStarted {
                mainViewModel.mGetUserListState.collect{
                    when(it){
                        is ApiState.Empty->{

                        }
                        is ApiState.Loading->{
                             binding.pbLoader.visibility = View.VISIBLE
                        }
                        is ApiState.Failure->{
                            binding.pbLoader.visibility = View.GONE
                           if(it.isNetworkError){
                             Toast.makeText(this@MainActivity,"Please connect you netowork",Toast.LENGTH_SHORT).show()
                           }else{
                               Toast.makeText(this@MainActivity,it.msg.message.toString(),Toast.LENGTH_SHORT).show()
                           }
                        }
                        is ApiState.Success->{
                           binding.pbLoader.visibility = View.GONE
                           val dataListAdd = mutableListOf<UserEntity>()
                           it.data.forEach {
                               dataListAdd.add(UserEntity(resId = it?.id?:0, title = it?.title?:"", body = it?.body?:"", userId = it?.userId?:0 ))
                           }
                           mainViewModel.insert(dataListAdd)
                           mainAdaptor.setList(dataListAdd)
                        }

                    }
                }
            }
        }

        mainViewModel.getList.observe(this, Observer {
            list = it as MutableList<UserEntity>
            if(list.size>0){
                mainAdaptor.setList(list)
            }else{
                mainViewModel.getUserList()
            }
        })

        mainAdaptor.deleteItem = { userEntity:UserEntity,position:Int ->
            alert(userEntity.id,position)
        }

    }

    fun alert(id:Int,position:Int){
        val builder:AlertDialog.Builder =AlertDialog.Builder(this)
        builder.setTitle("Delete Item").setMessage("Are you sure")
            .setPositiveButton("Okay"){_,_->
                val userDetails = mainViewModel.getUserById(id.toLong())
                userDetails?.let { mainViewModel.delete(it) }
                list.removeAt(position)
                mainAdaptor?.notifyDataSetChanged()
            }
            .setNegativeButton("Cancel"){_,_->

            }
       builder.show()
    }
}