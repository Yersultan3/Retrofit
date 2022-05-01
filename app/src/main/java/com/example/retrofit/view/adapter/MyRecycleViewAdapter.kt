package com.example.retrofit.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.retrofit.R
import com.example.retrofit.databinding.UserListItemBinding
import com.example.retrofit.model.database.User

class MyRecycleViewAdapter(private val usersList :List<User>):RecyclerView.Adapter<MyViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: UserListItemBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.user_list_item,parent,false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return usersList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(usersList[position])

    }


}

    class MyViewHolder(
        private val binding :UserListItemBinding
        ):RecyclerView.ViewHolder(binding.root) {

     fun bind(user : User){

        }

}