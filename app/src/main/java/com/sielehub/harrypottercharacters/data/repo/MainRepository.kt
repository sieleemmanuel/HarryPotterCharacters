package com.sielehub.harrypottercharacters.data.repo

import com.sielehub.harrypottercharacters.data.api.ApiService
import com.sielehub.harrypottercharacters.util.Resource
import com.sielehub.harrypottercharacters.data.model.Character
import java.io.IOException
import javax.inject.Inject

class MainRepository @Inject constructor(private val apiService: ApiService) {
    suspend fun getCharacters(): Resource<List<Character>> {
        return try {
            val response = apiService.getCharacters()
            if (response.isSuccessful){
                Resource.Success(response.body())
            }else{
                Resource.Error("Server error occurred. Please try again later")
            }
        }catch (exception: IOException){
            Resource.Error("No internet connection. Please check and try again")
        }

    }
}