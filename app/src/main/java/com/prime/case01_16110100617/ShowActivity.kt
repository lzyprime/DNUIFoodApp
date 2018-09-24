package com.prime.case01_16110100617

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_show.*

class ShowActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show)
        Picasso.get().load("http://172.24.10.175:8080/foodService" + intent.getStringExtra("img")).into(imageView)
    }
}
