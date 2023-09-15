package com.example.workoutapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.workoutapp.databinding.ItemHistoryDataBinding

class HistoryAdapter(private val items: ArrayList<String>):
                      //private val updateListener: (id:Int) -> Unit,
                      //private val deleteListener: (id:Int) -> Unit
    RecyclerView.Adapter<HistoryAdapter.ViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemHistoryDataBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val date: String = items[position]
        holder.tvOrder.text = (position+1).toString()
        holder.tvDate.text = date
    }
    class ViewHolder(binding: ItemHistoryDataBinding) : RecyclerView.ViewHolder(binding.root) {
        val tvOrder = binding.tvOrder
        val tvDate = binding.tvDateOfWorkOut
    }

    override fun getItemCount(): Int {
        return items.size
    }


}