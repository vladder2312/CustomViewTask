package ru.vladder2312.customviewtask

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(R.layout.activity_main) {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        myView.colors = mutableListOf(
            getColor(R.color.black),
            getColor(R.color.purple_200),
            getColor(R.color.purple_500),
            getColor(R.color.purple_700),
            getColor(R.color.teal_200),
            getColor(R.color.teal_700)
        )
    }
}