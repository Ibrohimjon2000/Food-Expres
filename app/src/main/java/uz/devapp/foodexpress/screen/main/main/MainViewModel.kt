package uz.devapp.foodexpress.screen.main.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import uz.devapp.foodexpress.models.CategoryModel
import uz.devapp.foodexpress.models.OfferModel
import uz.devapp.foodexpress.models.RestaurantModel
import uz.devapp.foodexpress.models.sealed.DataResult
import uz.devapp.foodexpress.repository.UserRepository

class MainViewModel : ViewModel() {
    val repository = UserRepository()

    private var _errorLiveData = MutableLiveData<String>()
    var errorLiveData: LiveData<String> = _errorLiveData

    private var _offerListLiveData = MutableLiveData<List<OfferModel>?>()
    var offerListLiveData: LiveData<List<OfferModel>?> = _offerListLiveData

    private var _categoryListLiveData = MutableLiveData<List<CategoryModel>?>()
    var categoryListLiveData: LiveData<List<CategoryModel>?> = _categoryListLiveData

    private var _restaurantListLiveData = MutableLiveData<List<RestaurantModel>?>()
    var restaurantListLiveData: LiveData<List<RestaurantModel>?> =
        _restaurantListLiveData

    private var _restaurantTopListLiveData = MutableLiveData<List<RestaurantModel>?>()
    var restaurantTopListLiveData: LiveData<List<RestaurantModel>?> =
        _restaurantTopListLiveData


    fun getOffers() {
        viewModelScope.launch {
            val result = repository.getOffers()
            when (result) {
                is DataResult.Error -> {
                    _errorLiveData.value = result.message
                }
                is DataResult.Success -> {
                    _offerListLiveData.value = (result.result)
                }
            }
        }
    }

    fun getCategory() {
        viewModelScope.launch {
            when (val result = repository.getCategory()) {
                is DataResult.Error -> {
                    _errorLiveData.value = result.message
                }
                is DataResult.Success -> {
                    _categoryListLiveData.value = (result.result)
                }
            }
        }
    }

    fun getRestaurant() {
        viewModelScope.launch {
            when (val result = repository.getRestaurants()) {
                is DataResult.Error -> {
                    _errorLiveData.value = result.message
                }
                is DataResult.Success -> {
                    _restaurantListLiveData.value = (result.result)
                }
            }
        }
    }

    fun getTopRestaurant() {
        viewModelScope.launch {
            when (val result = repository.getTopRestaurants()) {
                is DataResult.Error -> {
                    _errorLiveData.value = result.message
                }
                is DataResult.Success -> {
                    _restaurantTopListLiveData.value = (result.result)
                }
            }
        }
    }
}