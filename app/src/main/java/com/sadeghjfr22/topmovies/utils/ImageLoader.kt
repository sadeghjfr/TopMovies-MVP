package com.sadeghjfr22.topmovies.utils

import android.widget.ImageView
import coil.ImageLoader
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.sadeghjfr22.topmovies.R

object ImageLoader {

    var imageLoader : ImageLoader? = null

    fun ImageView.loadImage(url: String) {

        if (imageLoader == null){

            imageLoader = ImageLoader.Builder(context)
                .componentRegistry { add(SvgDecoder(context)) }
                .build()
        }

        val request = ImageRequest.Builder(this.context)
            .data(url)
            .target(this)
            .placeholder(R.drawable.placeholder)
            .error(R.drawable.placeholder)
            .build()

        imageLoader!!.enqueue(request)
    }
}