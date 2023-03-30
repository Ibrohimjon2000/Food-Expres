package uz.devapp.foodexpress.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.devapp.foodexpress.databinding.CartProductItemLayoutBinding
import uz.devapp.foodexpress.models.ProductModel
import uz.devapp.foodexpress.utils.PrefUtils
import uz.devapp.foodexpress.utils.loadImage

interface CartProductAdapterCallback{
    fun onUpdate(count:Int)
}

class CartProductAdapter(val productList: List<ProductModel>,val callback: CartProductAdapterCallback) :
    RecyclerView.Adapter<CartProductAdapter.Vh>() {

    inner class Vh(val cartProductItemLayoutBinding: CartProductItemLayoutBinding) :
        RecyclerView.ViewHolder(cartProductItemLayoutBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(
            CartProductItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        val productModel = productList[position]
        holder.cartProductItemLayoutBinding.imgAdd.setOnClickListener {
            PrefUtils.add2Cart(productModel.id, ++productModel.cartCount)
            notifyItemChanged(position)
            callback.onUpdate(productModel.cartCount)
        }
        holder.cartProductItemLayoutBinding.imgMinus.setOnClickListener {
            if (productModel.cartCount > 0) {
                PrefUtils.add2Cart(productModel.id, --productModel.cartCount)
                notifyItemChanged(position)
                callback.onUpdate(productModel.cartCount)
            }
        }
        holder.cartProductItemLayoutBinding.image.loadImage(productModel.image)
        holder.cartProductItemLayoutBinding.tvName.text = productModel.name
        holder.cartProductItemLayoutBinding.tvCount.text = productModel.cartCount.toString()
        holder.cartProductItemLayoutBinding.tvPrice.text = "${productModel.price} UZS"
    }
}