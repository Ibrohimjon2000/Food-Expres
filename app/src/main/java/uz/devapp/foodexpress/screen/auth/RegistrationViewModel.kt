package uz.devapp.foodexpress.screen.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import uz.devapp.foodexpress.models.CategoryModel
import uz.devapp.foodexpress.models.OfferModel
import uz.devapp.foodexpress.models.RestaurantModel
import uz.devapp.foodexpress.models.request.RegistrationRequest
import uz.devapp.foodexpress.models.response.RegistrationResponse
import uz.devapp.foodexpress.models.sealed.DataResult
import uz.devapp.foodexpress.repository.UserRepository

class RegistrationViewModel : ViewModel() {
    val repository = UserRepository()

    private var _errorLiveData = MutableLiveData<String>()
    var errorLiveData: LiveData<String> = _errorLiveData

    private var _registrationLiveData = MutableLiveData<RegistrationResponse?>()
    var registrationLiveData: LiveData<RegistrationResponse?> = _registrationLiveData

    fun getRegistration(request: RegistrationRequest) {
        viewModelScope.launch {
            val result = repository.getRegistration(request)
            when (result) {
                is DataResult.Error -> {
                    _errorLiveData.value = result.message
                }
                is DataResult.Success -> {
                    _registrationLiveData.value = (result.result)
                }
            }
        }
    }
}