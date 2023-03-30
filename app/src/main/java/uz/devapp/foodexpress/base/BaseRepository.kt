package uz.devapp.foodexpress.base

import retrofit2.Response
import uz.devapp.foodexpress.models.response.BaseResponse
import uz.devapp.foodexpress.models.sealed.DataResult

open class BaseRepository {
    fun <T> wrapResponse(response: Response<BaseResponse<T>>): DataResult<T> {
        return if (response.isSuccessful) {
            if (response.body()?.success == true) {
                DataResult.Success(response.body()!!.data!!)
            } else {
                DataResult.Error(response.body()?.message ?: "")
            }
        } else {
            DataResult.Error(response.message())
        }
    }
}