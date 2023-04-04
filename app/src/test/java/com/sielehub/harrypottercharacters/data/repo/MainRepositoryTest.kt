package com.sielehub.harrypottercharacters.data.repo

import com.google.common.truth.Truth
import com.sielehub.harrypottercharacters.data.api.ApiService
import com.sielehub.harrypottercharacters.data.model.Character
import com.sielehub.harrypottercharacters.data.model.Wand
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import retrofit2.Response

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class MainRepositoryTest {
    @Mock
    private lateinit var apiService: ApiService
    private lateinit var repository: MainRepository

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        repository = MainRepository(apiService)
    }
    @Test
    fun `get character should return success response with characters`() {
        runTest {
            val character = Character(
                id = "c3b1f9a5-b87b-48bf-b00d-95b093ea6390",
                name = "Ron Weasley",
                alternate_names = listOf("Dragomir Despard"),
                species = "human",
                gender = "male",
                house = "Gryffindor",
                dateOfBirth = "01-03-1980",
                yearOfBirth = 1980,
                wizard = true,
                ancestry = "pure-blood",
                eyeColour = "blue",
                hairColour = "red",
                wand = Wand(wood = "willow", core = "unicorn tail-hair", length = (14).toDouble()),
                patronus = "Jack Russell terrier",
                hogwartsStudent = true,
                hogwartsStaff = false,
                actor = "Rupert Grint",
                alternate_actors = listOf(),
                alive = true,
                image = "https://ik.imagekit.io/hpapi/ron.jpg"
            )
            val characterList = listOf(character)

            Mockito.`when`(apiService.getCharacters()).thenReturn(Response.success(characterList))
            val charactersResponse = repository.getCharacters()
            Truth.assertThat(characterList).isEqualTo(charactersResponse.data)
        }
    }
}