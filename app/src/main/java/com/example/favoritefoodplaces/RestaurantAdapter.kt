package com.example.favoritefoodplaces

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RestaurantAdapter(private val restaurantList: MutableList<Restaurant>) :
    RecyclerView.Adapter<RestaurantAdapter.RestaurantViewHolder>() {

        inner class RestaurantViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val nameView: TextView = itemView.findViewById(R.id.textView6)
            val locationView: TextView = itemView.findViewById(R.id.textView7)
            val cuisineView: TextView = itemView.findViewById(R.id.textView8)
            val ratingView: TextView = itemView.findViewById(R.id.textView9)
            init {
                itemView.setOnClickListener {
                    val clickedRestaurant = restaurantList[adapterPosition]
                    val intent = Intent(itemView.context, RestaurantDetailActivity::class.java)
                    intent.putExtra("restaurant", clickedRestaurant)
                    itemView.context.startActivity(intent)
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantViewHolder {
            val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_restaurants, parent, false)
            return RestaurantViewHolder(itemView)
        }

        override fun onBindViewHolder(holder: RestaurantViewHolder, position: Int) {
            val restaurant = restaurantList[position]

            holder.nameView.text = restaurant.name
            holder.locationView.text = restaurant.address
            holder.cuisineView.text = restaurant.typeOfFood
            holder.ratingView.text = restaurant.rating.toString()
        }

        override fun getItemCount(): Int {
            return restaurantList.size
        }

        fun updateData(newRestaurants: List<Restaurant>) {
            restaurantList.clear()
            restaurantList.addAll(newRestaurants)
            notifyDataSetChanged()
        }

    }
