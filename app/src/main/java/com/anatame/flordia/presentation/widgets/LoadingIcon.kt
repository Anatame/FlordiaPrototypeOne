package com.anatame.flordia.presentation.widgets

import android.content.Context
import android.graphics.drawable.AnimatedVectorDrawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import com.anatame.flordia.R
import com.anatame.flordia.databinding.LoadingAnimBinding

class LoadingIcon
@JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0): LinearLayout(context, attrs, defStyleAttr) {

    private var binding: LoadingAnimBinding =
        LoadingAnimBinding.inflate(LayoutInflater.from(context), this, false)

    val d = getDrawable(context, R.drawable.animated_loader) as AnimatedVectorDrawable

    private lateinit var loadingImage: ImageView

    init {
        addView(binding.root)
        loadingImage = binding.container
        loadingImage.setImageDrawable(d)
        d.start()
    }

    fun hide(){
        loadingImage.visibility = View.GONE
        d.stop()
    }

    fun show(){
        loadingImage.visibility = View.VISIBLE
        d.start()
    }
}