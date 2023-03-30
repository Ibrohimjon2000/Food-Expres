package uz.devapp.foodexpress.screen.main.order.checkout

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.observers.DisposableObserver
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.launch
import uz.devapp.foodexpress.models.request.MakeOrderRequest
import uz.devapp.foodexpress.models.response.BaseResponse
import uz.devapp.foodexpress.models.sealed.DataResult
import uz.devapp.foodexpress.networking.NetworkingObject
import uz.devapp.foodexpress.repository.UserRepository
import uz.devapp.foodexpress.utils.PrefUtils

class CheckoutViewModel : ViewModel() {
    val repository = UserRepository()

    private var _errorLiveData = MutableLiveData<String>()
    var errorLiveData: LiveData<String> = _errorLiveData

    private var _progressLiveData = MutableLiveData<Boolean>()
    var progressLiveData: LiveData<Boolean> = _progressLiveData

    private var _successOrderLiveData = MutableLiveData<Boolean>()
    var successOrderLiveData: LiveData<Boolean> = _successOrderLiveData

    fun getMakeOrder(request: MakeOrderRequest) {
        viewModelScope.launch {
            when (val result = repository.getMakeOrder(request)) {
                is DataResult.Error<*> -> {
                    _errorLiveData.value = result.message
                }
                is DataResult.Success<*> -> {
                    _successOrderLiveData.value = true
                }
            }
        }
    }
}