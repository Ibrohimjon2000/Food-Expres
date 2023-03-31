package uz.devapp.foodexpress.networking

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import uz.devapp.foodexpress.models.*
import uz.devapp.foodexpress.models.request.*
import uz.devapp.foodexpress.models.response.BaseResponse
import uz.devapp.foodexpress.models.response.LoginResponse
import uz.devapp.foodexpress.models.response.RegistrationResponse

interface Api {
    @POST("api/login")
    suspend fun login(
        @Body request: LoginRequest
    ): Response<BaseResponse<LoginResponse?>>

    @POST("api/registration")
    suspend fun registration(
        @Body request: RegistrationRequest
    ): Response<BaseResponse<RegistrationResponse?>>

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

    @GET("api/user")
    suspend fun getProfile(
    ): Response<BaseResponse<ProfileModel?>>
}