package com.sielehub.harrypottercharacters

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sielehub.harrypottercharacters.ui.CharacterList
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}