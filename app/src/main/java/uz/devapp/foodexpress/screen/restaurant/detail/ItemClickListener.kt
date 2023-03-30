package uz.devapp.foodexpress.screen.restaurant.detail

import uz.devapp.foodexpress.models.RestaurantModel

interface ItemClickListener {
    fun onItemClick(restaurantModel:RestaurantModel)
}