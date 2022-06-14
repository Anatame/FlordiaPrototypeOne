package com.anatame.flordia.presentation.widgets.flordia_player.more_controls

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.anatame.flordia.presentation.widgets.flordia_player.TrackData
import com.anatame.flordia.databinding.PlayerControlsMoreQualityBinding

class MoreControlsAdapter(
    private val currentBitrate: Int?
) : RecyclerView.Adapter<MoreControlsAdapter.MoreControlItemsViewHolder>() {

    inner class MoreControlItemsViewHolder(val binding: PlayerControlsMoreQualityBinding): RecyclerView.ViewHolder(binding.root)

    private val differCallback = object : DiffUtil.ItemCallback<TrackData>() {
        override fun areItemsTheSame(oldItem: TrackData, newItem: TrackData): Boolean {
            return oldItem.bitrate == newItem.bitrate
        }

        override fun areContentsTheSame(oldItem: TrackData, newItem: TrackData): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoreControlItemsViewHolder {
        val binding = PlayerControlsMoreQualityBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return MoreControlItemsViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private var onItemClickListener: ((TrackData) -> Unit)? = null

    override fun onBindViewHolder(holder: MoreControlItemsViewHolder, position: Int) {
        val trackData = differ.currentList[position]
        holder.binding.apply {
            tvQuality.text = trackData.quality
            container.setOnClickListener {
                onItemClickListener?.let{it(trackData)}
                ivCheckmark.visibility = View.VISIBLE
            }
            if(trackData.bitrate == currentBitrate)
                ivCheckmark.visibility = View.VISIBLE
            else
                ivCheckmark.visibility = View.INVISIBLE
        }
    }

    fun setOnItemClickListener(listener: (TrackData) -> Unit) {
        onItemClickListener = listener
    }

}


