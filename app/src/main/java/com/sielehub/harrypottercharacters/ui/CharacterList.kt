package com.sielehub.harrypottercharacters.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.sielehub.harrypottercharacters.adapters.CharactersAdapter
import com.sielehub.harrypottercharacters.databinding.FragmentCharacterListBinding
import com.sielehub.harrypottercharacters.util.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CharacterList : Fragment() {
    private  val viewModel: MainViewModel by activityViewModels()
    private lateinit var binding: FragmentCharacterListBinding
    private lateinit var charactersAdapter: CharactersAdapter
    private lateinit var characterList:List<com.sielehub.harrypottercharacters.data.model.Character>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCharacterListBinding.inflate(inflater)
        charactersAdapter = CharactersAdapter { character ->
            findNavController()
                .navigate(CharacterListDirections.actionCharacterListToCharacterDetail(character))
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            rvCharacters.apply {
                adapter = charactersAdapter
                layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            }
            svCharacters.apply {
                setOnQueryTextListener(object :android.widget.SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String): Boolean {
                    val searchResults = viewModel.searchCharacter(characterList, query)
                    charactersAdapter.submitList(null)
                    charactersAdapter.submitList(searchResults)
                    return false
                }

                override fun onQueryTextChange(newText: String): Boolean {
                    val searchResults = viewModel.searchCharacter(characterList, newText)
                    charactersAdapter.submitList(null)
                    charactersAdapter.submitList(searchResults)
                    return true
                }

            })
                setOnCloseListener {
                    this.clearFocus()
                    root.requestFocus()
                    return@setOnCloseListener true
                }
            }

        lifecycleScope.launch {
            viewModel.characters.collectLatest {characterState ->
                when(characterState){
                    is Resource.Loading ->{
                        pbLoading.isVisible = true
                    }
                    is Resource.Success ->{
                        pbLoading.isVisible = false
                        charactersAdapter.submitList(characterState.data)
                        characterList = characterState.data!!
                    }
                    is Resource.Error ->{
                        pbLoading.isVisible = false
                        Snackbar.make(root, characterState.message!!, Snackbar.LENGTH_INDEFINITE)
                            .setAction("Retry"){
                                viewModel.getCharacters()
                            }
                            .show()
                    }
                }
            }


            }

        }
     }
    }
