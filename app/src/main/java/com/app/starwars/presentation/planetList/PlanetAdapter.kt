package com.app.starwars.presentation.planetList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.app.starwars.R
import com.app.starwars.models.Planet
import com.app.starwars.util.DiffUtilCallback
import kotlinx.android.synthetic.main.item_row_planet.view.*

class PlanetAdapter(private val cl: PlanetItemClickListener) :
    PagingDataAdapter<Planet, PlanetAdapter.PlanetViewHolder>(DiffUtilCallback()) {

    override fun onBindViewHolder(holder: PlanetViewHolder, position: Int) {
        getItem(position)?.let { planet ->
            holder.bindPost(planet, cl.clickListener, position)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PlanetViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_row_planet, parent, false)
        return PlanetViewHolder(view)
    }

    class PlanetViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val climateText: TextView = itemView.tv_climate
        private val nameText: TextView = itemView.tv_name

        fun bindPost(planet: Planet, clickListener: (planet: Planet) -> Unit, position: Int) {
            itemView.setOnClickListener { clickListener(planet) }
            with(planet) {
                climateText.text = climate
                nameText.text = name
            }
        }
    }
}

data class PlanetItemClickListener(val clickListener: (planet: Planet) -> Unit)