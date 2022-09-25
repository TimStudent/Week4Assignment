package com.example.week4assignment.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.week4assignment.databinding.SingleItemBinding
import com.example.week4assignment.model.room.TrailerData

class TrailersAdapter(
    private var trailerDataList: MutableList<TrailerData> = mutableListOf(),
    private val onTrailerClickListener: (TrailerData) -> Unit
) : RecyclerView.Adapter<NewViewHolder>(){
    private val limit = 1

    override fun getItemCount(): Int {
        if(trailerDataList.size > limit){
            return limit
        }
        else{
            return trailerDataList.size
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewViewHolder {
        val binding = SingleItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewViewHolder, position: Int) {
        holder.bind(trailerDataList[position], onTrailerClickListener)
    }

    fun update(newData: List<TrailerData>){
        trailerDataList.clear()
        trailerDataList.addAll(newData)
        notifyDataSetChanged()
    }

}
class NewViewHolder(val binding: SingleItemBinding) : RecyclerView.ViewHolder(binding.root){
    fun bind(trailer: TrailerData, onTrailerClickListener: (TrailerData) -> Unit) {
        binding.play.text = trailer.site
        binding.imageView.setOnClickListener {

            onTrailerClickListener.invoke(trailer)
        }
        onTrailerClickListener.invoke(trailer)
    }
}