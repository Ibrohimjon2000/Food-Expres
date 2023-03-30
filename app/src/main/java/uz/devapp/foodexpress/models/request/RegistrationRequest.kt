package uz.devapp.foodexpress.models.request


import com.google.gson.annotations.SerializedName

data class RegistrationRequest(

    @SerializedName("fullname")
    val fullname: String,
    @SerializedName("phone")
    val phone: String,
    @SerializedName("password")
    val password: String
)