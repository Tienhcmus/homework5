package com.example.recyclerview

import android.content.Context
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.recyclerview.dataroom.RoomDatabase
import com.example.recyclerview.fragment.FragmentFavorite
import com.example.recyclerview.fragment.FragmentPlaying
import com.example.recyclerview.fragment.FragmentRating
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    var Fragment_state = 1
    private lateinit var db: RoomDatabase
    private val PREFERENCE_NAME = "SETTING_FILES"
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //get Room Database instance
        db = RoomDatabase.invoke(this)
        //set item bottomnavigation true when re-open
        val navView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        //set first view is Fragment Playing
        when(getSharePreference()) {
            1 -> {
                val fragment = FragmentPlaying()
                supportFragmentManager.beginTransaction()
                    .replace(R.id.container, fragment, fragment.javaClass.getSimpleName())
                    .addToBackStack(null)
                    .commit()
                navView.menu.findItem(R.id.navigation_playing).isChecked = true
            }
            2 -> {
                val fragment = FragmentRating()
                supportFragmentManager.beginTransaction()
                    .replace(R.id.container, fragment, fragment.javaClass.getSimpleName())
                    .addToBackStack(null)
                    .commit()
                navView.menu.findItem(R.id.navigation_top_rating).isChecked = true
            }
            3 -> {
                val fragment = FragmentFavorite()
                supportFragmentManager.beginTransaction()
                    .replace(R.id.container, fragment, fragment.javaClass.getSimpleName())
                    .addToBackStack(null)
                    .commit()
                navView.menu.findItem(R.id.navigation_favorite).isChecked = true
            }
        }

        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    private val mOnNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.navigation_playing -> {
                    Fragment_state = 1
                    val fragment = FragmentPlaying()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container, fragment, fragment.javaClass.getSimpleName())
                        .addToBackStack(null)
                        .commit()
                    saveSharedPreference(Fragment_state)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_top_rating -> {
                    Fragment_state = 2
                    val fragment = FragmentRating()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container, fragment, fragment.javaClass.getSimpleName())
                        .addToBackStack(null)
                        .commit()
                    saveSharedPreference(Fragment_state)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_favorite -> {
                    Fragment_state = 3
                    val fragment = FragmentFavorite()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container, fragment, fragment.javaClass.getSimpleName())
                        .addToBackStack(null)
                        .commit()
                    saveSharedPreference(Fragment_state)
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        }
    // save state of app
    private  fun saveSharedPreference(int : Int) {
        val preference = getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
        val prefEditor = preference.edit()
        prefEditor.putInt("state", int)
        prefEditor.apply()
    }
    //get state before user kill app
    private fun getSharePreference(): Int{
        val preference = getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
        return preference.getInt("state", 2)
    }

}




