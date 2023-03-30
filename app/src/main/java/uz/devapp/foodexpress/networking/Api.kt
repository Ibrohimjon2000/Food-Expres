package uz.devapp.foodexpress.networking

import io.reactivex.rxjava3.core.Observable
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path
import uz.devapp.foodexpress.models.*
import uz.devapp.foodexpress.models.request.*
import uz.devapp.foodexpress.models.response.BaseResponse
import uz.devapp.foodexpress.models.response.LoginResponse
import uz.devapp.foodexpress.models.response.RegistrationResponse
import uz.devapp.foodexpress.utils.Constants.DEVELOPER_KEY

interface Api {
    @POST("api/login")
    fun login(
        @Body request: LoginRequest
    ): Observable<BaseResponse<LoginResponse?>>

    @POST("api/registration")
    fun registration(
        @Body request: RegistrationRequest
    ): Observable<BaseResponse<RegistrationResponse?>>

    @GET("api/offers")
    suspend fun getOffers(
    ): Response<BaseResponse<List<OfferModel>?>>

    @GET("api/categories")
    suspend fun getCategories(
    ): Response<BaseResponse<List<CategoryModel>?>>

    @POST("api/restaurants")
    suspend fun getRestaurants(
        @Body request: RestaurantFilter
    ): Response<BaseResponse<List<RestaurantModel>?>>

    @POST("api/restaurants")
    suspend fun getTopRestaurants(
        @Body request: TopRestaurantFilter
    ): Response<BaseResponse<List<RestaurantModel>?>>

    @POST("api/restaurants")
    suspend fun getAllRestaurants(
        @Body request: AllRestaurant
    ): Response<BaseResponse<List<RestaurantModel>?>>

    @GET("/api/restaurant_detail/{restaurant_id}")
    suspend fun getDetailRestaurants(
        @Path("restaurant_id") id: Int
    ): Response<BaseResponse<RestaurantModel?>>

    @GET("api/restaurant/{restaurant_id}/foods")
    suspend fun getProduct(
        @Path("restaurant_id") id: Int
    ): Response<BaseResponse<List<ProductModel>?>>

    @POST("api/get/food_by_ids")
    suspend fun getFoodsByIds(
        @Body request: FoodsByIdsRequest
    ): Response<BaseResponse<List<ProductModel>?>>

    @POST("api/make_order")
    suspend fun getMakeOrder(
        @Body request: MakeOrderRequest
    ): Response<BaseResponse<Any>>

    @POST("api/make_rating")
   suspend fun getMakeRating(
        @Body request: MakeRatingRequest
    ): Response<BaseResponse<Any?>>
}