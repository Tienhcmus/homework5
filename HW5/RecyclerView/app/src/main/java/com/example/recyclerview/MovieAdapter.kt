package com.example.recyclerview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide


class MovieAdapter(val ctx: Context, private var movies: ArrayList<MovieModel>, val listener: MovieListener ): RecyclerView.Adapter<MovieAdapter.MovieVH>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieVH {
            val layoutInflater = LayoutInflater.from(parent.context)

            return MovieVH(layoutInflater, parent)
        }

        override fun getItemCount(): Int {
            return movies.size
        }

        override fun onBindViewHolder(holder: MovieVH, position: Int) {
            val movie = movies[position]

            holder.tvTitle.text = movie.title
            holder.tvRating.rating = movie.vote_average /2
            holder.tvScore.text = movie.vote_average.toString()
            holder.tvRelease.text = movie.release_date

            Glide.with(ctx)
                .load(movie.getURL())
                .centerCrop()
                .into(holder.ivImage)
            holder.itemView.setOnClickListener{
                listener?.onClick(position, movie)
            }
            holder.ivfavorite.setOnClickListener {
                listener?.favrite(position, movie)
            }
            holder.itemView.setOnLongClickListener {
                listener.onItemLongCLicked(position)
                true
            }
        }

        class MovieVH(inflater: LayoutInflater, parent: ViewGroup) : RecyclerView.ViewHolder(inflater.inflate(R.layout.movie_item, parent, false)){
        val tvTitle = itemView.findViewById<TextView>(R.id.tv)
        val tvRating = itemView.findViewById<RatingBar>(R.id.ratingBar)
        val tvScore = itemView.findViewById<TextView>(R.id.rating)
        val tvRelease = itemView.findViewById<TextView>(R.id.date)
        val ivImage = itemView.findViewById<ImageView>(R.id.ig)
        val ivfavorite = itemView.findViewById<ImageView>(R.id.favorite)
        }
        interface MovieListener {
            fun onClick(pos: Int, movies: MovieModel)
            fun favrite(pos: Int, movies: MovieModel)
            fun onItemLongCLicked(pos: Int)
        }
    }