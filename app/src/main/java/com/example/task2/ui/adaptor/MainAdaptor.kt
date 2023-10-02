package com.example.task2.ui.adaptor

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.task2.R
import com.example.task2.data.entity.UserEntity
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainAdaptor @Inject constructor(@ApplicationContext val context: Context):RecyclerView.Adapter<MainAdaptor.MainViewHolder>() {
    class MainViewHolder(itemView:View):RecyclerView.ViewHolder(itemView) {
     val tvTitle :TextView
     val tvBody :TextView
     val ivDelete :ImageView
     init {
         tvTitle = itemView.findViewById(R.id.tvTitle)
         tvBody = itemView.findViewById(R.id.tvBody)
         ivDelete = itemView.findViewById(R.id.ivDelete)
     }
    }

    var deleteItem :((userEntity:UserEntity, position:Int)->Unit)?=null

    var userList = mutableListOf<UserEntity>()


    @SuppressLint("NotifyDataSetChanged")
    fun setList(userList:MutableList<UserEntity>){
        this.userList = userList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_list,parent,false)
        return MainViewHolder(view)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
          holder.tvTitle.text = userList[position].title
          holder.tvBody.text = userList[position].body
          holder.ivDelete.setOnClickListener {
              deleteItem?.invoke(userList[position],position)
          }
    }

    override fun getItemCount(): Int  = userList.size


}