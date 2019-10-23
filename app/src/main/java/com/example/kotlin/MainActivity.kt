package com.example.kotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var viewModule: MainViewModule
    private lateinit var button: Button
    private lateinit var textView : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModule = ViewModelProviders.of(this).get(MainViewModule::class.java)

        button = findViewById(R.id.button)
        textView = findViewById(R.id.textView)

        viewModule.viewState().observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        })

        button.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        if (v!!.id == R.id.button)
            textView.text = viewModule.viewState().value
            textView.rotation += 10F
    }


}
