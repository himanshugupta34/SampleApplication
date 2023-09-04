package com.example.testapplication

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.example.testapplication.util.PosterImages

object BindingAdapter {
    @JvmStatic
    @BindingAdapter("loadImage")
    fun loadPosterImage(view: ImageView, posterImage: String?) {
        posterImage?.let {
            val resourceId = when (it.split(".")[0]) {
                "poster1" -> PosterImages.poster1
                "poster2" -> PosterImages.poster2
                "poster3" -> PosterImages.poster3
                "poster4" -> PosterImages.poster4
                "poster5" -> PosterImages.poster5
                "poster6" -> PosterImages.poster6
                "poster7" -> PosterImages.poster7
                "poster8" -> PosterImages.poster8
                "poster9" -> PosterImages.poster9
                else -> R.drawable.placeholder_for_missing_posters
            }
            view.setImageResource(resourceId)
        } ?: run { view.setImageResource(R.drawable.placeholder_for_missing_posters) }
    }
}