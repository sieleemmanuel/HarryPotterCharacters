package com.sielehub.harrypottercharacters.data.api

import retrofit2.Response
import retrofit2.http.GET
import com.sielehub.harrypottercharacters.data.model.Character

interface ApiService {
    @GET("characters")
    suspend fun getCharacters(): Response<List<Character>>
}