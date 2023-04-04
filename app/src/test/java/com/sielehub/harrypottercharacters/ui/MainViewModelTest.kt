package com.sielehub.harrypottercharacters.ui

import com.google.common.truth.Truth
import com.sielehub.harrypottercharacters.data.repo.MainRepository
import com.sielehub.harrypottercharacters.util.Resource
import com.sielehub.harrypottercharacters.data.model.Character
import com.sielehub.harrypottercharacters.data.model.Wand
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(JUnit4::class)
class MainViewModelTest {
    @Mock
    private lateinit var mainRepository: MainRepository
    private lateinit var viewModel: MainViewModel

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)
        viewModel = MainViewModel(mainRepository)
    }

    @Test
    fun `get character should return success state response with characters`() {
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

            Mockito.`when`(mainRepository.getCharacters()).thenReturn(Resource.Success(characterList))
            testDispatcher.scheduler.advanceUntilIdle()
            val characters = viewModel.characters.value
            Truth.assertThat(characterList).isEqualTo(characters.data)
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `get characters with no internet connection should return network error state`() {
        runTest {
            val networkErrorMessage = "No internet connection. Please check and try again"

            Mockito.`when`(mainRepository.getCharacters()).thenReturn(Resource.Error(networkErrorMessage))
            testDispatcher.scheduler.advanceUntilIdle()
            val response = viewModel.characters.value
            Truth.assertThat(networkErrorMessage).isEqualTo(response.message)
        }

    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `get characters with server should return server error state`() {
        runTest {
            val serverErrorMessage = "Server error occurred. Please try again later"
            Mockito.`when`(mainRepository.getCharacters()).thenReturn(Resource.Error(serverErrorMessage))
            testDispatcher.scheduler.advanceUntilIdle()
            val response = viewModel.characters.value
            Truth.assertThat(serverErrorMessage).isEqualTo(response.message)
        }
    }
}