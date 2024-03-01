package com.example.pokemonappchallenge.ui.view.recyclerview

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pokemonappchallenge.R
import com.example.pokemonappchallenge.data.models.Pokemon
import com.example.pokemonappchallenge.databinding.ItemPokemonBinding
import com.example.pokemonappchallenge.ui.model.Generation


class PokeRecyclerView(
    val context: Context,
    private val list: List<Pokemon>,
    private val filters: MutableList<Generation> = mutableListOf(),
    private val listener: OnEmptyListener
): RecyclerView.Adapter<PokeRecyclerView.ViewHolder>() {

    private var filteredList = list.toMutableList()

    fun applyFilter(newFilter: Generation){
        filters.add(newFilter)
        val newList = mutableListOf<Pokemon>()
        for (filter in filters){
            for (i in filter.start..filter.end){
                list.getOrNull(i)?.let { pokemon->
                    newList.add(pokemon)
                }
            }
        }
        filteredList.clear()
        filteredList.addAll(newList)
        notifyDataSetChanged()
    }

    fun deleteFilter(newFilter: Generation){
        filters.remove(newFilter)
        val newList = mutableListOf<Pokemon>()
        for (filter in filters){
            for (i in filter.start..filter.end){
                list.getOrNull(i)?.let { pokemon->
                    newList.add(pokemon)
                }
            }
        }
        if (filters.isNotEmpty()) {
            filteredList.clear()
            filteredList.addAll(newList)
        } else {
            filteredList = list.toMutableList()
        }
        notifyDataSetChanged()

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemPokemonBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        listener.showEmptyLayout(filteredList.isEmpty())
        return filteredList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(filteredList[position], position)
    }

    inner class ViewHolder(private val view: ItemPokemonBinding): RecyclerView.ViewHolder(view.root){
        @SuppressLint("UseCompatLoadingForDrawables")
        fun onBind(data: Pokemon, position: Int){

            val layoutParams = view.cardContainer.layoutParams as ViewGroup.MarginLayoutParams
            if ((position + 1) % 2== 0){
                layoutParams.setMargins(8, 16, 0, 0)
            } else {
                layoutParams.setMargins(0, 16, 8, 0)
            }

            view.cardContainer.layoutParams = layoutParams

            Glide
                .with(context)
                .load(setImageUri(pokeIndex = data.id))
                .placeholder(context.getDrawable(R.drawable.notification_icon))
                .centerCrop()
                .into(view.pokeImage)


            view.pokemonName.text = data.name.replaceFirstChar { it.uppercase() }


        }

        private fun setImageUri(pokeIndex: Int): String{
            return "$BASE_IMAGE_URL/${pokeIndex}.png"
        }

    }

    companion object {
        const val BASE_IMAGE_URL = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/"
    }

    interface OnEmptyListener {
        fun showEmptyLayout(show: Boolean)
    }
}