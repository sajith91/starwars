package com.app.starwars.presentation.planetList

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.app.starwars.R
import com.app.starwars.presentation.planetDetail.PlanetDetailActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var  planetAdapter :PlanetAdapter

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupViews()
        fetchPlanets()
    }

    private fun setupViews() {

        planetAdapter = PlanetAdapter(PlanetItemClickListener {
            val myIntent = Intent(this, PlanetDetailActivity::class.java)
            myIntent.putExtra("PLANET", it)
            this.startActivity(myIntent)
        })

        rv_planets.adapter = planetAdapter
        rv_planets.adapter = planetAdapter.withLoadStateHeaderAndFooter(
            header = PlanetLoadingAdapter { planetAdapter.retry() },
            footer = PlanetLoadingAdapter { planetAdapter.retry() }
        )
    }

    private fun fetchPlanets() {
        lifecycleScope.launch {
            viewModel.fetchPlanets().collectLatest { pagingData ->
                planetAdapter.submitData(pagingData)
            }
        }
    }


}