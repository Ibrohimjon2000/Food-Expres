package uz.devapp.foodexpress.screen.restaurant.detail

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
import uz.devapp.foodexpress.models.ProductModel
import uz.devapp.foodexpress.models.RestaurantModel
import uz.devapp.foodexpress.models.request.MakeOrderRequest
import uz.devapp.foodexpress.models.request.MakeRatingRequest
import uz.devapp.foodexpress.models.response.BaseResponse
import uz.devapp.foodexpress.models.sealed.DataResult
import uz.devapp.foodexpress.networking.NetworkingObject
import uz.devapp.foodexpress.repository.UserRepository

class RestaurantDetailViewModel : ViewModel() {
    val repository = UserRepository()

    private var _errorLiveData = MutableLiveData<String>()
    var errorLiveData: LiveData<String> = _errorLiveData

    private var _progressLiveData = MutableLiveData<Boolean>()
    var progressLiveData: LiveData<Boolean> = _progressLiveData

    private var _successProductLiveData = MutableLiveData<List<ProductModel>>()
    var successProductLiveData : LiveData<List<ProductModel>> =_successProductLiveData

    private var _successDetailRestaurantLiveData = MutableLiveData<RestaurantModel>()
    var successDetailRestaurantLiveData : LiveData<RestaurantModel> =_successDetailRestaurantLiveData

    fun getProduct(request: Int) {
        viewModelScope.launch {
            when (val result = repository.getProduct(request)) {
                is DataResult.Error -> {
                    _errorLiveData.value = result.message
                }
                is DataResult.Success -> {
                    _successProductLiveData.value = (result.result)
                }
            }
        }
    }

    fun getDetailRestaurant(request: Int) {
        viewModelScope.launch {
            when (val result = repository.getDetailRestaurant(request)) {
                is DataResult.Error -> {
                    _errorLiveData.value = result.message
                }
                is DataResult.Success -> {
                    _successDetailRestaurantLiveData.value=(result.result)
                }
            }
        }
    }

}