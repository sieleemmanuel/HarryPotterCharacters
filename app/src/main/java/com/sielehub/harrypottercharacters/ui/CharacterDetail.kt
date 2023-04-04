package com.sielehub.harrypottercharacters.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import com.sielehub.harrypottercharacters.R
import com.sielehub.harrypottercharacters.databinding.FragmentCharacterDetailBinding
import com.sielehub.harrypottercharacters.data.model.Character
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class CharacterDetail : Fragment() {
    private lateinit var binding:FragmentCharacterDetailBinding
    private val args:CharacterDetailArgs by navArgs()
    private lateinit var character: Character

    @Suppress("DEPRECATION")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCharacterDetailBinding.inflate(inflater)
        character = args.character
        binding.apply {
            imageView2.load(character.image){
                placeholder(R.drawable.ic_placeholder_image)
                error(R.drawable.ic_broken_image)
            }
            if (character.alternate_names.isNotEmpty()){
                when(character.alternate_names.size){
                    3 ->{
                        tvAltName1.apply {
                            isVisible = true
                            text = character.alternate_names[0]
                        }
                        tvAltName2.apply {
                            isVisible = true
                            text = character.alternate_names[1]
                        }
                        tvAltName3.apply {
                            isVisible = true
                            text = character.alternate_names[2]
                        }

                    }
                    2 ->{
                        tvAltName1.apply {
                            isVisible = true
                            text = character.alternate_names[0]
                        }
                        tvAltName2.apply {
                            isVisible = true
                            text = character.alternate_names[1]
                        }
                        tvAltName3.isVisible = false
                    }
                    1 ->{
                        tvAltName1.apply {
                            isVisible = true
                            text = character.alternate_names[0]
                        }
                        tvAltName2.isVisible = false
                        tvAltName3.isVisible = false
                    }
                }

            }else{
                tvAltName1.visibility = View.GONE
                tvAltName2.visibility = View.GONE
                tvAltName3.visibility = View.GONE
            }

            tvHouseLabelValue.text = character.house.ifEmpty { getString(R.string.unknown_label) }
            tvGenderLabelValue.text = character.gender.capitalize(Locale.ROOT)
            tvActorLabelValue.text = character.actor.ifEmpty{getString(R.string.unknown_label)}
            tvAncestryLabelValue.text = character.ancestry.ifEmpty{getString(R.string.unknown_label)}.capitalize(
                Locale.getDefault())
            tvWoodLabelValue.text = character.wand.wood.ifEmpty{getString(R.string.unknown_label)}.capitalize(
                Locale.ROOT)
            tvCoreLabelValue.text = character.wand.core.ifEmpty{getString(R.string.unknown_label)}.capitalize(Locale.ROOT)
            tvLengthLabelValue.text = if(character.wand.length !=null) {
                character.wand.length.toString()
            } else {
                getString(R.string.null_length_value)
            }
            detailToolbar.apply {
                setNavigationOnClickListener {
                    findNavController().popBackStack()
                }
                title = character.name
            }
        }
        return binding.root
    }

}