package com.example.favoritefoodplaces

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class AddRestaurantActivity : AppCompatActivity() {

    private lateinit var db : FirebaseFirestore
    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_restaurant)

        auth = Firebase.auth
        db = Firebase.firestore

        val button = findViewById<Button>(R.id.add_restaurant_button)
        button.setOnClickListener {
            saveItem()
        }

        val backButton = findViewById<Button>(R.id.button2)
        backButton.setOnClickListener {
            finish()
        }
    }

    fun saveItem() {
        val restaurantName: EditText = findViewById(R.id.editTextRestaurantName)
        val restaurantAddress: EditText = findViewById(R.id.editTextLocation)
        val restaurantCuisine: EditText = findViewById(R.id.editTextCuisine)
        val restaurantRating: EditText = findViewById(R.id.editTextRating)

        val ratingString = restaurantRating.text.toString()
        val rating = if (ratingString.isNotEmpty()) {
            ratingString.toDoubleOrNull() ?: 0.0
        } else {
            0.0
        }

        val restaurant = Restaurant(
            name = restaurantName.text.toString(),
            address = restaurantAddress.text.toString(),
            typeOfFood = restaurantCuisine.text.toString(),
            rating = rating
        )

        val user = auth.currentUser
        if (user != null) {
            db.collection("users").document(user.uid)
                .collection("favorite_restaurants")
                .add(restaurant)
                .addOnSuccessListener {
                    val resultIntent = Intent()
                    resultIntent.putExtra("new_restaurant", restaurant)
                    setResult(Activity.RESULT_OK, resultIntent)
                    finish()
                }
        }
    }
}