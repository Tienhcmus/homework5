package com.example.recyclerview.fragment

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.icu.util.ValueIterator
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recyclerview.*
import com.example.recyclerview.dataroom.RoomDatabase
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.playing_fragment.*
import kotlinx.android.synthetic.main.rating_fragment.*
import java.util.*
import java.util.function.Predicate
import kotlin.Comparator
import kotlin.collections.ArrayList

class FragmentRating : Fragment(){
    private lateinit var db: RoomDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        db = RoomDatabase.invoke(activity as Context)
        setHasOptionsMenu(true)
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return   inflater.inflate(R.layout.rating_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rv_rating.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = MovieAdapter(context, covertJsontoListRating() as ArrayList<MovieModel>, listener)
        }
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.toolbar, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if(id == R.id.listview)
        {
            rv_rating.apply {
                layoutManager = LinearLayoutManager(activity)
                adapter = MovieAdapter(context, covertJsontoListRating() as ArrayList<MovieModel>, listener)
            }
        }
        if(id == R.id.gridview)
        {
            rv_rating.apply {
                layoutManager = GridLayoutManager(activity, 3, LinearLayoutManager.VERTICAL, false)
                adapter = MovieAdapter_Grid(context, covertJsontoListRating() as ArrayList<MovieModel>, listener2)
            }
        }
        return super.onOptionsItemSelected(item)
    }
    private val listener = object : MovieAdapter.MovieListener {
        override fun onClick(pos: Int, movie: MovieModel) {
            startProfileActivity(movie)
        }
        override fun favrite(pos: Int, movies: MovieModel) {
            val builder = AlertDialog.Builder(activity)
                .setMessage("Bạn có muốn thêm vào danh sách phim yêu thích không?")
                .setPositiveButton("YES") { _, _ ->
                    //add to Favorite movie
                    handleSubmitDatafromRating(movies)

                }
                .setNegativeButton("NO") { dialog, _ -> dialog?.dismiss() }
            val dialog = builder.create();
            dialog.show()
        }

        override fun onItemLongCLicked(pos: Int) {
            TODO("Not yet implemented")
        }
    }

    private var listener2 = object : MovieAdapter_Grid.MovieListener {
        override fun onClick(pos: Int, movie: MovieModel) {
            startProfileActivity(movie)
        }

        override fun favrite(pos: Int, movies: MovieModel) {
            val builder = AlertDialog.Builder(activity)
                .setMessage("Bạn có muốn thêm vào danh sách phim yêu thích không?")
                .setPositiveButton("YES") { _, _ ->
                    //add to Favorite movie
                    handleSubmitDatafromRating(movies)

                }
                .setNegativeButton("NO") { dialog, _ -> dialog?.dismiss() }
            val dialog = builder.create();
            dialog.show()
        }

        override fun onItemLongCLicked(pos: Int) {
            TODO("Not yet implemented")
        }
    }


    // get ModelMovie and sort top 10 movie has highest vote rating
    fun covertJsontoListRating(): List<MovieModel> {
        val gson = Gson()
        val arrayTutorialType = object : TypeToken<ArrayList<MovieModel>>() {}.type
        var data: ArrayList<MovieModel> = gson.fromJson(FragmentPlaying.JSON_ARRAY, arrayTutorialType)
        // sort descending
        data.sortWith(object : Comparator<MovieModel>{
            override fun compare(p1: MovieModel, p2: MovieModel): Int = when {
                p1.vote_average < p2.vote_average -> 1
                p1.vote_average == p2.vote_average -> 0
                else -> -1
            }
        })
        // get movies has vote rating > 7
        var ds = data.filter { it.vote_average > 7 }
        return ds
    }


    private fun startProfileActivity(movie: MovieModel) {
        val intent = Intent (activity, MovieActivity2::class.java)
        intent.putExtra("title", movie.title)
        intent.putExtra("vote_average", movie.vote_average)
        intent.putExtra("release_date", movie.release_date)
        intent.putExtra("overview", movie.overview)
        intent.putExtra("poster_path", movie.getURL())
        activity?.startActivity(intent)
    }
    // send data to room
    private fun handleSubmitDatafromRating(movie: MovieModel) {
        val movieDAO = db.favoriteDAO()
        movieDAO.insert(movie)
    }

}