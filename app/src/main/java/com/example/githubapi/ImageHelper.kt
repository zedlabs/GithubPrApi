package com.example.githubapi

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import java.text.SimpleDateFormat
import java.util.*
import java.sql.Timestamp


object Helper {
    fun loadImage(url: String?, target: ImageView) {
        Glide.with(target.context)
            .load(url)
            .transform(CenterCrop(), RoundedCorners(26))
            .into(target)
    }

    fun getDateTime(timestamp: Timestamp): String? {
        val date = Date()
        date.time = timestamp.time
        return SimpleDateFormat("dd/MM/yyyy", Locale.US).format(date)
    }
}