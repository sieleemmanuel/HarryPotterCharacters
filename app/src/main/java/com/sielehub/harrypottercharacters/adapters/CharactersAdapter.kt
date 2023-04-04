package com.sielehub.harrypottercharacters.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.sielehub.harrypottercharacters.R
import com.sielehub.harrypottercharacters.data.model.Character
import com.sielehub.harrypottercharacters.databinding.CharacterItemBinding

class CharactersAdapter(val itemClickListener:(Character)->Unit):ListAdapter<Character, CharactersAdapter.CharactersViewHolder>(DiffItemUtil) {
    object DiffItemUtil: DiffUtil.ItemCallback<Character>() {
        override fun areItemsTheSame(oldItem: Character, newItem: Character): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Character, newItem: Character): Boolean {
           return oldItem.id == newItem.id
        }

    }

    inner class CharactersViewHolder(private val binding: CharacterItemBinding) :RecyclerView.ViewHolder(binding.root){
        fun bind(character: Character) {
            binding.apply {
                tvName.text = character.name
                tvHouse.text = character.house
                imageView.load(character.image){
                    placeholder(R.drawable.ic_placeholder_image)
                    error(R.drawable.ic_broken_image)
                }
                root.setOnClickListener {
                    itemClickListener(character)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharactersViewHolder {
        val binding = CharacterItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CharactersViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CharactersViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}