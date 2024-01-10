package com.example.favoritefoodplaces

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat

class RestaurantDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurant_detail)

        val restaurant = intent.getParcelableExtra<Restaurant>("restaurant")

        val restaurantNameTextView: TextView = findViewById(R.id.textViewRestaurantName)
        restaurantNameTextView.text = restaurant?.name
        val adressTextView1 : TextView = findViewById(R.id.adressView1)
        adressTextView1.text = restaurant?.address
        val typeNameTextView : TextView = findViewById(R.id.textView5)
        typeNameTextView.text = restaurant?.typeOfFood
        val ratingTextView : TextView = findViewById(R.id.textView10)
        ratingTextView.text = restaurant?.rating.toString()
        val infoTextView: TextView = findViewById(R.id.textView11)
        infoTextView.text = restaurant?.info
        val restaurantImageView: ImageView = findViewById(R.id.imageView6)
        restaurant?.imageResId?.let {
            restaurantImageView.setImageResource(it)
        }


        val backButton = findViewById<Button>(R.id.button)
        backButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}
