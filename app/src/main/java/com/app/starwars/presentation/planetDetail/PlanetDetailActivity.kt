package com.app.starwars.presentation.planetDetail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.widget.Toolbar
import com.app.starwars.R
import com.app.starwars.models.Planet
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import kotlinx.android.synthetic.main.activity_planet_detail.*

class PlanetDetailActivity : AppCompatActivity() {

    lateinit var planet: Planet

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_planet_detail)

        //setup tool bar
        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        //get data from the intent
        planet = (intent.getSerializableExtra("PLANET") as? Planet)!!
        updateUi()
    }

    private fun updateUi() {
        iv_planet_image.loadImage("https://picsum.photos/300/200")
        tv_name.text = planet.name
        tv_climate.text = tv_climate.text.toString().plus(planet.climate)
        tv_orbital_period.text = tv_orbital_period.text.toString().plus(planet.orbital_period)
        tv_gravity.text =  tv_gravity.text.toString().plus(planet.gravity)
    }
}

//extension function to load image
private fun ImageView.loadImage(thumbnail: String) {
    Glide.with(this.context)
        .load(thumbnail)
        .diskCacheStrategy(DiskCacheStrategy.NONE)
        .skipMemoryCache(true)
        .into(this)
}

