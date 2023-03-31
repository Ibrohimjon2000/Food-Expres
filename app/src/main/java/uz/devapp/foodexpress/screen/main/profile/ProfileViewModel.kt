package uz.devapp.foodexpress.screen.main.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import uz.devapp.foodexpress.models.ProfileModel
import uz.devapp.foodexpress.models.sealed.DataResult
import uz.devapp.foodexpress.repository.UserRepository

class ProfileViewModel : ViewModel() {
    val repository = UserRepository()

    private var _errorLiveData = MutableLiveData<String>()
    var errorLiveData: LiveData<String> = _errorLiveData

    private var _profileLiveData = MutableLiveData<ProfileModel?>()
    var profileLiveData: LiveData<ProfileModel?> = _profileLiveData


    fun getProfile() {
        viewModelScope.launch {
            val result = repository.getProfile()
            when (result) {
                is DataResult.Error -> {
                    _errorLiveData.value = result.message
                }
                is DataResult.Success -> {
                    _profileLiveData.value = (result.result)
                }
            }
        }
    }
}