package com.example.recyclerview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_movie2.*

class MovieActivity2 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie2)
        supportActionBar?.hide()
        getAndDisplayData()

    }
    private fun getAndDisplayData() {
        val data = intent.extras

        if (data != null) {
            val title = data.getString("title")
            val rating = data.getFloat("vote_average")
            val release = data.getString("release_date")
            val descrition = data.getString("overview")
            val url = data.getString("poster_path")

            aa_Title2.text = title
            ratingBar3.rating = rating/2
            rating3.text = rating.toString()
            aa_Releasedate2.text = release
            aa_description2.text = descrition

            Glide.with(this)
                .load(url)
                .centerCrop()
                .placeholder(R.drawable.loading_image)
                .into(aa_ImageMovie2)
        }
    }
}


