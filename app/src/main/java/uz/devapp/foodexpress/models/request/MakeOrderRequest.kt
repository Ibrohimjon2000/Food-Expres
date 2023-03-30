package uz.devapp.foodexpress.models.request


import com.google.gson.annotations.SerializedName
import uz.devapp.foodexpress.models.CartModel
import uz.devapp.foodexpress.models.enum.OrderType

data class MakeOrderRequest(
    @SerializedName("adress")
    var address: String,
    @SerializedName("latitude")
    var latitude: Double,
    @SerializedName("longitude")
    var longitude: Double,
    @SerializedName("order_products")
    var orderProducts: List<CartModel>,
    @SerializedName("order_type")
    var orderType: OrderType
)