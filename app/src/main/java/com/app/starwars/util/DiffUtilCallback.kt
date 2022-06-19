package com.app.starwars.util

import androidx.recyclerview.widget.DiffUtil
import com.app.starwars.models.Planet

class DiffUtilCallback : DiffUtil.ItemCallback<Planet>() {
    override fun areItemsTheSame(oldItem: Planet, newItem: Planet): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: Planet, newItem: Planet): Boolean {
        return oldItem.name == newItem.name
    }

}