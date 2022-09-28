package com.example.week4assignment.adapter

import android.content.ContentValues
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.week4assignment.databinding.SingleItemBinding
import com.example.week4assignment.model.room.DetailData

class DetailAdapter(
    private var detailDataList: MutableList<DetailData> = mutableListOf(),
    private val onDetailClickListener: (DetailData) -> Unit
) : RecyclerView.Adapter<DetailViewHolder>() {
    private val limit = 1
    override fun getItemCount(): Int {
        if (detailDataList.size>limit){
            return limit
        }
        else{
            return detailDataList.size
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailViewHolder {
        val binding = SingleItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DetailViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DetailViewHolder, position: Int) {
        holder.bind(detailDataList[position], onDetailClickListener)
        with(holder){
            with(detailDataList[position]){
                binding.cardLayout.setOnClickListener {
                    this.expand = !this.expand
                    notifyItemChanged(position)
                }
            }
        }
    }
    fun add(newData: DetailData){
        detailDataList.add(newData)
    }
    fun update (newData: List<DetailData>){
        detailDataList.clear()
        detailDataList.addAll(newData)
        notifyDataSetChanged()
    }
}
class DetailViewHolder(val binding: SingleItemBinding): RecyclerView.ViewHolder(binding.root){
    fun bind(detail: DetailData, onDetailClickListener: (DetailData) -> Unit){
        binding.categories.text = "test"
        binding.categoriesHint.text = "testing"
        binding.expandedView.visibility = if (detail.expand) View.VISIBLE else View.GONE
        binding.cardLayout.setOnClickListener {
            detail.expand = !detail.expand
        }
        binding.imageView.setOnClickListener {
            Log.d(ContentValues.TAG, detail.runtime.toString() + detail.popularity.toString() + detail.genres.toString())
            onDetailClickListener.invoke(detail)
        }
        onDetailClickListener.invoke(detail)
    }
}