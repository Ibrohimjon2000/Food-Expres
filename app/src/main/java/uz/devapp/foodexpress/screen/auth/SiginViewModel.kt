package uz.devapp.foodexpress.screen.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import uz.devapp.foodexpress.models.CategoryModel
import uz.devapp.foodexpress.models.OfferModel
import uz.devapp.foodexpress.models.RestaurantModel
import uz.devapp.foodexpress.models.request.LoginRequest
import uz.devapp.foodexpress.models.request.RegistrationRequest
import uz.devapp.foodexpress.models.response.LoginResponse
import uz.devapp.foodexpress.models.response.RegistrationResponse
import uz.devapp.foodexpress.models.sealed.DataResult
import uz.devapp.foodexpress.repository.UserRepository

class SiginViewModel : ViewModel() {
    val repository = UserRepository()

    private var _errorLiveData = MutableLiveData<String>()
    var errorLiveData: LiveData<String> = _errorLiveData

    private var _siginLiveData = MutableLiveData<LoginResponse?>()
    var siginLiveData: LiveData<LoginResponse?> = _siginLiveData

    fun getSigin(request: LoginRequest) {
        viewModelScope.launch {
            val result = repository.getSigin(request)
            when (result) {
                is DataResult.Error -> {
                    _errorLiveData.value = result.message
                }
                is DataResult.Success -> {
                    _siginLiveData.value = (result.result)
                }
            }
        }
    }
}