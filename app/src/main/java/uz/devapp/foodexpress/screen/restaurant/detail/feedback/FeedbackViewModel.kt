package uz.devapp.foodexpress.screen.restaurant.detail.feedback

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.observers.DisposableObserver
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.launch
import uz.devapp.foodexpress.models.AllRestaurant
import uz.devapp.foodexpress.models.RestaurantModel
import uz.devapp.foodexpress.models.request.MakeOrderRequest
import uz.devapp.foodexpress.models.request.MakeRatingRequest
import uz.devapp.foodexpress.models.response.BaseResponse
import uz.devapp.foodexpress.models.sealed.DataResult
import uz.devapp.foodexpress.networking.NetworkingObject
import uz.devapp.foodexpress.repository.UserRepository

class FeedbackViewModel : ViewModel() {
    val repository = UserRepository()

    private var _errorLiveData = MutableLiveData<String>()
    var errorLiveData: LiveData<String> = _errorLiveData

    private var _successOrderLiveData = MutableLiveData<Boolean>()
    var successOrderLiveData : LiveData<Boolean> =_successOrderLiveData

    private var _successRestaurantsLiveData = MutableLiveData<List<RestaurantModel>>()
    var successRestaurantsLiveData : LiveData<List<RestaurantModel>> =_successRestaurantsLiveData

    fun getMakeRating(request: MakeRatingRequest) {
        viewModelScope.launch {
            when (val result = repository.getMakeRating(request)) {
                is DataResult.Error<*> -> {
                    _errorLiveData.value = result.message
                }
                is DataResult.Success<*> -> {
                    _successOrderLiveData.value = true
                }
            }
        }
    }

    fun getAllRestaurant() {
        viewModelScope.launch {
            when (val result = repository.getAllRestaurant(AllRestaurant())) {
                is DataResult.Error -> {
                    _errorLiveData.value = result.message
                }
                is DataResult.Success -> {
                    _successRestaurantsLiveData.value=(result.result)
                }
            }
        }
    }

}