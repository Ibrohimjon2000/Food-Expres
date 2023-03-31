package uz.devapp.foodexpress.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import uz.devapp.foodexpress.base.BaseRepository
import uz.devapp.foodexpress.models.*
import uz.devapp.foodexpress.models.request.*
import uz.devapp.foodexpress.models.response.LoginResponse
import uz.devapp.foodexpress.models.response.RegistrationResponse
import uz.devapp.foodexpress.models.sealed.DataResult
import uz.devapp.foodexpress.networking.NetworkingObject
import uz.devapp.foodexpress.utils.PrefUtils

class UserRepository : BaseRepository() {
    val api = NetworkingObject.getClientInstance()

    suspend fun getOffers() = withContext(Dispatchers.IO) {
        try {
            val response = api.getOffers()
            return@withContext wrapResponse(response)
        } catch (e: Exception) {
            return@withContext DataResult.Error<List<OfferModel>>(e.localizedMessage)
        }
    }

    suspend fun getCategory() = withContext(Dispatchers.IO) {
        try {
            val response = api.getCategories()
            return@withContext wrapResponse(response)
        } catch (e: Exception) {
            return@withContext DataResult.Error<List<CategoryModel>>(e.localizedMessage)
        }
    }

    suspend fun getRestaurants() = withContext(Dispatchers.IO) {
        try {
            val response = api.getRestaurants(RestaurantFilter())
            return@withContext wrapResponse(response)
        } catch (e: Exception) {
            return@withContext DataResult.Error<List<RestaurantModel>>(e.localizedMessage)
        }
    }

    suspend fun getTopRestaurants() = withContext(Dispatchers.IO) {
        try {
            val response = api.getTopRestaurants(TopRestaurantFilter())
            return@withContext wrapResponse(response)
        } catch (e: Exception) {
            return@withContext DataResult.Error<List<RestaurantModel>>(e.localizedMessage)
        }
    }

    suspend fun getMakeRating(request: MakeRatingRequest) = withContext(Dispatchers.IO) {
        try {
            val response = api.getMakeRating(request)
            return@withContext wrapResponse(response)
        } catch (e: Exception) {
            return@withContext e.localizedMessage
        }
    }

    suspend fun getAllRestaurant(request: AllRestaurant) = withContext(Dispatchers.IO) {
        try {
            val response = api.getAllRestaurants(request)
            return@withContext wrapResponse(response)
        } catch (e: Exception) {
            return@withContext DataResult.Error<List<RestaurantModel>>(e.localizedMessage)
        }
    }

    suspend fun getProduct(request: Int) = withContext(Dispatchers.IO) {
        try {
            val response = api.getProduct(request)
            return@withContext wrapResponse(response)
        } catch (e: Exception) {
            return@withContext DataResult.Error<List<ProductModel>>(e.localizedMessage)
        }
    }

    suspend fun getDetailRestaurant(request: Int) = withContext(Dispatchers.IO) {
        try {
            val response = api.getDetailRestaurants(request)
            return@withContext wrapResponse(response)
        } catch (e: Exception) {
            return@withContext DataResult.Error<RestaurantModel>(e.localizedMessage)
        }
    }

    suspend fun getFoods() = withContext(Dispatchers.IO) {
        try {
            val response =
                api.getFoodsByIds(FoodsByIdsRequest(PrefUtils.getCartList().map { it.id }))
            return@withContext wrapResponse(response)
        } catch (e: Exception) {
            return@withContext DataResult.Error<List<ProductModel>>(e.localizedMessage)
        }
    }

    suspend fun getMakeOrder(request: MakeOrderRequest) = withContext(Dispatchers.IO) {
        try {
            val response = api.getMakeOrder(request)
            return@withContext wrapResponse(response)
        } catch (e: Exception) {
            return@withContext e.localizedMessage
        }
    }

    suspend fun getRegistration(request: RegistrationRequest) = withContext(Dispatchers.IO) {
        try {
            val response = api.registration(request)
            return@withContext wrapResponse(response)
        } catch (e: Exception) {
            return@withContext DataResult.Error<RegistrationResponse>(e.localizedMessage)
        }
    }

    suspend fun getSigin(request: LoginRequest) = withContext(Dispatchers.IO) {
        try {
            val response = api.login(request)
            return@withContext wrapResponse(response)
        } catch (e: Exception) {
            return@withContext DataResult.Error<LoginResponse>(e.localizedMessage)
        }
    }

    suspend fun getProfile() = withContext(Dispatchers.IO) {
        try {
            val response = api.getProfile()
            return@withContext wrapResponse(response)
        } catch (e: Exception) {
            return@withContext DataResult.Error<ProfileModel>(e.localizedMessage)
        }
    }
}