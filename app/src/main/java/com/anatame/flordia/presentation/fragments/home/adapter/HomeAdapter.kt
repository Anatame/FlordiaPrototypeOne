package com.anatame.flordia.presentation.fragments.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.anatame.flordia.R
import com.anatame.flordia.databinding.ActivityMainBinding.inflate
import com.anatame.flordia.databinding.HomeItemOneBinding
import com.anatame.flordia.databinding.HomeItemThreeBinding
import com.anatame.flordia.databinding.HomeItemTwoBinding

class HomeAdapter : RecyclerView.Adapter<HomeRecyclerViewHolder>() {

    var items = listOf<HomeRecyclerViewItem>()
    set(value){
        field = value
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeRecyclerViewHolder {
        return when(viewType){
            R.layout.home_item_one -> {
                HomeRecyclerViewHolder.HomeViewHolderTypeOne(
                    HomeItemOneBinding.inflate(LayoutInflater.from(parent.context))
                )
            }
            R.layout.home_item_two -> {
                HomeRecyclerViewHolder.HomeViewHolderTypeTwo(
                    HomeItemTwoBinding.inflate(LayoutInflater.from(parent.context))
                )
            }
            R.layout.home_item_three -> {
                HomeRecyclerViewHolder.HomeViewHolderTypeThree(
                    HomeItemThreeBinding.inflate(LayoutInflater.from(parent.context))
                )
            }

            else -> throw IllegalArgumentException("Item doesn't match")
        }
    }

    override fun onBindViewHolder(holder: HomeRecyclerViewHolder, position: Int) {
        when(holder){
            is HomeRecyclerViewHolder.HomeViewHolderTypeOne -> holder.bind(items[position] as HomeRecyclerViewItem.HomeItemTypeOne)
            is HomeRecyclerViewHolder.HomeViewHolderTypeTwo ->  holder.bind(items[position] as HomeRecyclerViewItem.HomeItemTypeTwo)
            is HomeRecyclerViewHolder.HomeViewHolderTypeThree ->  holder.bind(items[position] as HomeRecyclerViewItem.HomeItemTypeThree)
        }
    }

    override fun getItemCount(): Int = items.size


    override fun getItemViewType(position: Int): Int {
        return when(items[position]){
            is HomeRecyclerViewItem.HomeItemTypeOne -> R.layout.home_item_one
            is HomeRecyclerViewItem.HomeItemTypeTwo -> R.layout.home_item_two
            is HomeRecyclerViewItem.HomeItemTypeThree -> R.layout.home_item_three
        }
    }
}