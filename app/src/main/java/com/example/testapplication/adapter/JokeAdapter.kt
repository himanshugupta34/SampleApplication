package com.example.testapplication.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.testapplication.databinding.RowPostBinding

class JokeAdapter(
    private var data: MutableList<String>
) : RecyclerView.Adapter<JokeAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            RowPostBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun addAll(toAdd: MutableList<String>) {
        this.data = toAdd
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding.jokeData = data[position]
    }

    inner class MyViewHolder(val binding: RowPostBinding) :
        RecyclerView.ViewHolder(binding.root)
}