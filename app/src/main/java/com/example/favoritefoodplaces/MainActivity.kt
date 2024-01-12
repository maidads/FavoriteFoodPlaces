package com.example.favoritefoodplaces

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {

    lateinit var db : FirebaseFirestore
    lateinit var auth : FirebaseAuth
    private val restaurants = mutableListOf(
        Restaurant("Sultan", "Hisingsgatan 28, 417 03 Göteborg", "Turkisk kolgrill", 4.3, R.drawable.sultan_bild, "Sultan är inte bara en restaurang; det är en plats där smaker, dofter och vänlighet smälter samman för att skapa en minnesvärd upplevelse. För varje besök på Sultan blir du en del av deras berättelse och tar med dig en bit av Turkiet med dig när du lämnar."),
        Restaurant("Divan", "Nordanvindsgatan 2, 417 17 Göteborg", "Turkisk kolgrill", 4.2, R.drawable.divan_bild, "Menyn är som en resa genom de mest autentiska smakerna av turkisk matlagning. Varje rätt är en konstnärlig presentation av kärlek till traditionella recept och färska ingredienser. Köttet, grillat över öppen eld, smälter i munnen och för tankarna till gatorna i Istanbul."),
        Restaurant("Takame", "Sankt Eriksgatan 8, 411 05 Göteborg", "Sushi", 4.4, R.drawable.takame_bild, "Menyn på Takame är som en resa genom havets skatter. Varje tugga av deras sushi är som en explosion av smaker, där varje ingrediens är noga utvald och kombinerad för att skapa en harmonisk symfoni av färska smaker."),
        Restaurant("Moon Thai Kitchen", "Kristinelundsgatan 9, 411 37 Göteborg", "Thailändskt", 4.2, R.drawable.moon_bild, "Moon Thai Kitchen är en plats där smak, kreativitet och vänlighet smälter samman till en förtrollande upplevelse. Varje besök på Moon Thai Kitchen är som en resa till de pulserande marknaderna i Bangkok och de doftande gatorna i Phuket."),
        Restaurant("Sannes Pizzeria", "Långängen 13, 417 63 Göteborg", "Pizzeria", 4.1, R.drawable.pizza_bild, "Redan när du kliver in möts du av den underbara doften av nygrillad deg och en atmosfär av glädje och gemenskap. Sannegårdens pizzeria är en plats där vänner samlas för att njuta av enkelheten och njutningen av en fantastisk bit pizza.")
    )
    private val adapter = RestaurantAdapter(restaurants)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        val myRecyclerView = findViewById<RecyclerView>(R.id.my_recycler_view)
        myRecyclerView.layoutManager = LinearLayoutManager(this)
        myRecyclerView.adapter = adapter


        val addRestaurantLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                data?.let {
                    val newRestaurant = it.getParcelableExtra<Restaurant>("new_restaurant")
                    newRestaurant?.let { restaurant ->
                        restaurants.add(restaurant)
                        adapter.notifyItemInserted(restaurants.size - 1)
                    }
                }
            }
        }


        val addButton = findViewById<Button>(R.id.add_button)
        addButton.setOnClickListener {
            val addRestaurantIntent = Intent(this, AddRestaurantActivity::class.java)
            addRestaurantLauncher.launch(addRestaurantIntent)
        }
        fetchRestaurantsFromFirestore()

    }

    fun fetchRestaurantsFromFirestore() {
        val user = auth.currentUser
        if (user != null) {
            db.collection("users").document(user.uid)
                .collection("favorite_restaurants")
                .addSnapshotListener { value, error ->
                    if (error != null) {
                        // Hantera fel här
                        Log.e("Firestore", "Error fetching restaurants", error)
                        return@addSnapshotListener
                    }

                    if (value != null) {
                        val fetchedRestaurants = ArrayList<Restaurant>()

                        for (document in value) {
                            val restaurant = document.toObject(Restaurant::class.java)
                            fetchedRestaurants.add(restaurant)
                        }

                        // Uppdatera adaptern med den nya listan av restauranger
                        for (newRestaurant in fetchedRestaurants) {
                            adapter.addRestaurant(newRestaurant)
                        }
                    }
                }
        }
    }
}