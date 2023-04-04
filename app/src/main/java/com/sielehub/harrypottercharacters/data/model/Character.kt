package com.sielehub.harrypottercharacters.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class Character(
    val actor: String,
    val alive: Boolean,
    val alternate_actors: @RawValue List<Any>,
    val alternate_names: List<String>,
    val ancestry: String,
    val dateOfBirth: String?,
    val eyeColour: String,
    val gender: String,
    val hairColour: String,
    val hogwartsStaff: Boolean,
    val hogwartsStudent: Boolean,
    val house: String,
    val id: String,
    val image: String,
    val name: String,
    val patronus: String,
    val species: String,
    val wand: @RawValue Wand,
    val wizard: Boolean,
    val yearOfBirth: Int
): Parcelable