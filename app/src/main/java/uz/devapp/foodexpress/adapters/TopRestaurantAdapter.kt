package uz.devapp.foodexpress.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.devapp.foodexpress.databinding.RestaurantItemLayoutBinding
import uz.devapp.foodexpress.databinding.TopRestaurantItemLayoutBinding
import uz.devapp.foodexpress.models.RestaurantModel
import uz.devapp.foodexpress.screen.restaurant.detail.RestaurantDetailActivity
import uz.devapp.foodexpress.utils.Constants.EXTRA_DATA
import uz.devapp.foodexpress.utils.loadImage

class TopRestaurantAdapter(val restaurantList: List<RestaurantModel>) :
    RecyclerView.Adapter<TopRestaurantAdapter.Vh>() {

    inner class Vh(val topRestaurantItemLayoutBinding: TopRestaurantItemLayoutBinding) :
        RecyclerView.ViewHolder(topRestaurantItemLayoutBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(
            TopRestaurantItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return restaurantList.size
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        val restaurantModel = restaurantList[position]
        holder.topRestaurantItemLayoutBinding.tvName.text = restaurantModel.name
        holder.topRestaurantItemLayoutBinding.tvAddress.text = restaurantModel.address
        if (restaurantModel.distance > 0) {
            holder.topRestaurantItemLayoutBinding.distanceVisibility.visibility = View.VISIBLE
            holder.topRestaurantItemLayoutBinding.tvDistance.text =
                "${String.format("%.1f", restaurantModel.distance)} km"
        } else {
            holder.topRestaurantItemLayoutBinding.distanceVisibility.visibility = View.GONE
        }
        holder.topRestaurantItemLayoutBinding.tvRating.text = restaurantModel.rating.toString()
        holder.topRestaurantItemLayoutBinding.image.loadImage(restaurantModel.main_image)
        holder.itemView.setOnClickListener {
            val intent = Intent(it.context, RestaurantDetailActivity::class.java)
            intent.putExtra(EXTRA_DATA, restaurantModel)
            it.context.startActivity(intent)
        }
    }
}