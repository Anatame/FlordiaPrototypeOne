package com.anatame.flordia.presentation.fragments.detail.adapter

import android.content.Context
import android.content.res.Resources
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.anatame.flordia.R
import com.anatame.flordia.databinding.ItemEpisodeBinding
import com.anatame.flordia.databinding.ItemMovieGridBinding
import com.anatame.flordia.databinding.ItemServerBinding
import com.anatame.flordia.domain.models.MovieItem
import com.anatame.flordia.presentation.widgets.flordia_web_view.dto.Episode
import com.anatame.flordia.presentation.widgets.flordia_web_view.dto.Server

class EpisodesAdapter(
    val context: Context
) : RecyclerView.Adapter<EpisodesAdapter.SearchItemViewHolder>() {

    inner class SearchItemViewHolder(val binding: ItemEpisodeBinding): RecyclerView.ViewHolder(binding.root)

    private val differCallback = object : DiffUtil.ItemCallback<Episode>() {
        override fun areItemsTheSame(oldItem: Episode, newItem: Episode): Boolean {
            return oldItem.dataId == newItem.dataId
        }

        override fun areContentsTheSame(oldItem: Episode, newItem: Episode): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchItemViewHolder {
        val binding = ItemEpisodeBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return SearchItemViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private var onItemClickListener: ((Episode) -> Unit)? = null

    override fun onBindViewHolder(holder: SearchItemViewHolder, position: Int) {
        val episode = differ.currentList[position]

        holder.binding.apply {
            tvEpisode.text = episode.name
            clContainer.setOnClickListener {
                onItemClickListener?.let { it(episode) }
            }
        }


    }

    fun setOnItemClickListener(listener: (Episode) -> Unit) {
        onItemClickListener = listener
    }

}


