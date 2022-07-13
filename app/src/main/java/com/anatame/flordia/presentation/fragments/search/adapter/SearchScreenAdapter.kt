package com.anatame.flordia.presentation.fragments.search.adapter

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
import com.anatame.flordia.databinding.ItemMovieGridBinding
import com.anatame.flordia.models.MovieItem
import timber.log.Timber


class SearchScreenAdapter(
    val context: Context
) : RecyclerView.Adapter<SearchScreenAdapter.SearchItemViewHolder>() {

    inner class SearchItemViewHolder(val binding: ItemMovieGridBinding): RecyclerView.ViewHolder(binding.root)

    private val differCallback = object : DiffUtil.ItemCallback<MovieItem>() {
        override fun areItemsTheSame(oldItem: MovieItem, newItem: MovieItem): Boolean {
            return oldItem.thumbnail == newItem.thumbnail
        }

        override fun areContentsTheSame(oldItem: MovieItem, newItem: MovieItem): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchItemViewHolder {
        val binding = ItemMovieGridBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return SearchItemViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private var onItemClickListener: ((MovieItem, ImageView) -> Unit)? = null

    override fun onBindViewHolder(holder: SearchItemViewHolder, position: Int) {
        val searchMovieItem = differ.currentList[position]
        ViewCompat.setTransitionName(holder.binding.ivMovieThumbnail, "iv$position")
        Timber.tag("searchResponse").d(searchMovieItem.toString())



        holder.binding.apply {
            ivMovieThumbnail.load(searchMovieItem.thumbnail)

            val layoutParams: LinearLayout.LayoutParams =
                container.layoutParams as LinearLayout.LayoutParams
            layoutParams.setMargins(8.px, 16.px, 8.px, 8.px)

            container.layoutParams = layoutParams

            tvMovieName.text = searchMovieItem.title

            cvContainer.setOnClickListener {
                onItemClickListener?.let {
                    it(
                        searchMovieItem,
                        holder.binding.ivMovieThumbnail
                    )
                }
            }
        }

        setRotateAnimation(position, holder)

    }

    private var lastPosition = -1

    private fun setRotateAnimation(
        position: Int,
        holder: SearchItemViewHolder
    ) {
        if (position > lastPosition) {
            val animation = AnimationUtils.loadAnimation(context, R.anim.rv_scaleup)
            holder.itemView.startAnimation(animation)
            lastPosition = position
        }
    }
    //extension function to convert dp to px
    val Int.px: Int get() = (this * Resources.getSystem().displayMetrics.density).toInt()

    fun setOnItemClickListener(listener: (MovieItem, ImageView) -> Unit) {
        onItemClickListener = listener
    }

}


