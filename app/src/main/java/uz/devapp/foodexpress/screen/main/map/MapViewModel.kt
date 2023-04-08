package uz.devapp.foodexpress.screen.main.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import uz.devapp.foodexpress.models.ProfileModel
import uz.devapp.foodexpress.models.RestaurantModel
import uz.devapp.foodexpress.models.sealed.DataResult
import uz.devapp.foodexpress.repository.UserRepository

class MapViewModel : ViewModel() {
    val repository = UserRepository()

    private var _errorLiveData = MutableLiveData<String>()
    var errorLiveData: LiveData<String> = _errorLiveData

    private var _progressLiveData = MutableLiveData<Boolean>()
    var progressLiveData: LiveData<Boolean> = _progressLiveData

    private var _restaurantListLiveData = MutableLiveData<List<RestaurantModel>?>()
    var restaurantListLiveData: LiveData<List<RestaurantModel>?> = _restaurantListLiveData


    fun getRestaurant() {
        _progressLiveData.value = true
        viewModelScope.launch {
            val result = repository.getTopRestaurants()
            when (result) {
                is DataResult.Error -> {
                    _errorLiveData.value = result.message
                }
                is DataResult.Success -> {
                    _restaurantListLiveData.value = (result.result)
                }
            }
            _progressLiveData.value = false
        }
    }
}