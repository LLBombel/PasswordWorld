package com.rafalropel.passwordworld

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.rafalropel.passwordworld.databinding.ItemRowBinding

class PasswordAdapter(private val items: ArrayList<PasswordEntity>,
                      private val updateListener:(id:Int)->Unit,
                      private val deleteListener:(id:Int)->Unit
):RecyclerView.Adapter<PasswordAdapter.ViewHolder>() {






    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemRowBinding.inflate(LayoutInflater.from(parent.context),
            parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val context = holder.itemView.context
        val item = items[position]

        holder.tvName.text = item.name
        holder.tvLogin.text = item.login
        holder.tvPassword.text = item.password



        if(position % 2 == 0){
            holder.llMain.setBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.light_gray))
        }else {
            holder.llMain.setBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.white))
        }
        holder.ivEdit.setOnClickListener{
            updateListener.invoke(item.id)
        }

        holder.ivDelete.setOnClickListener{
            deleteListener.invoke(item.id)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }


    class ViewHolder(binding: ItemRowBinding): RecyclerView.ViewHolder(binding.root){
        val llMain = binding.llMain
        val tvName = binding.tvName
        val tvLogin = binding.tvLogin
        val tvPassword = binding.tvPassword
        val ivEdit = binding.ivEdit
        val ivDelete = binding.ivDelete
    }
}