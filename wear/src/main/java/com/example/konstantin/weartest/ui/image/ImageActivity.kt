package com.example.konstantin.weartest.ui.image

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.wearable.activity.WearableActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.example.konstantin.weartest.R
import kotlinx.android.synthetic.main.activity_image.*


class ImageActivity : WearableActivity() {
    companion object {
        private val EXTRA_IMAGE_URL = "extra_image_url"
        private val MAX_IMAGE_SCALE = 5F

        fun getIntent(context: Context, imageUrl: String) = Intent(context, ImageActivity::class.java)
            .putExtra(EXTRA_IMAGE_URL, imageUrl)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image)

        val image = intent.extras.getString(EXTRA_IMAGE_URL)

        imageView.maximumScale = MAX_IMAGE_SCALE

        buttonZoomIn.setOnClickListener {
            with(imageView) {
                val size = if (scale + 1 < maximumScale) scale + 1 else maximumScale
                setScale(size, true)
            }
        }


        buttonZoomOut.setOnClickListener {
            with(imageView) {
                val size = if (scale - 1 > minimumScale) scale - 1 else minimumScale
                setScale(size, true)
            }
        }

        Glide.with(this)
            .load(image)
            .apply(RequestOptions().override(Target.SIZE_ORIGINAL).error(R.drawable.ic_image_placeholder))
            .into(imageView)
    }
}