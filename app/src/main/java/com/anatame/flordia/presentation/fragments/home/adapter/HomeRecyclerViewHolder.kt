package com.anatame.flordia.presentation.fragments.home.adapter

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.anatame.flordia.databinding.HomeItemOneBinding
import com.anatame.flordia.databinding.HomeItemThreeBinding
import com.anatame.flordia.databinding.HomeItemTwoBinding

sealed class HomeRecyclerViewHolder(binding: ViewBinding): RecyclerView.ViewHolder(binding.root) {

    class HomeViewHolderTypeOne(private val binding: HomeItemOneBinding): HomeRecyclerViewHolder(binding){
        fun bind(data: HomeRecyclerViewItem.HomeItemTypeOne){
            binding.textView.text = data.text
        }
    }
    class HomeViewHolderTypeTwo(private val binding: HomeItemTwoBinding): HomeRecyclerViewHolder(binding){
        fun bind(data: HomeRecyclerViewItem.HomeItemTypeTwo){
            binding.textView.text = data.text
        }
    }
    class HomeViewHolderTypeThree(private val binding: HomeItemThreeBinding): HomeRecyclerViewHolder(binding){
        fun bind(data: HomeRecyclerViewItem.HomeItemTypeThree){
            binding.textView.text = data.text
        }
    }

}