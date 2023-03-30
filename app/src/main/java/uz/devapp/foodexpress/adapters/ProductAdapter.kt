package uz.devapp.foodexpress.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.devapp.foodexpress.databinding.ProductItemLayoutBinding
import uz.devapp.foodexpress.models.ProductModel
import uz.devapp.foodexpress.utils.PrefUtils
import uz.devapp.foodexpress.utils.loadImage

class ProductAdapter(val productList: List<ProductModel>) :
    RecyclerView.Adapter<ProductAdapter.Vh>() {

    inner class Vh(val productItemLayoutBinding: ProductItemLayoutBinding) :
        RecyclerView.ViewHolder(productItemLayoutBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(
            ProductItemLayoutBinding.inflate(
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
        holder.productItemLayoutBinding.imgAdd.setOnClickListener {
            PrefUtils.add2Cart(productModel.id, ++productModel.cartCount)
            notifyItemChanged(position)
        }
        holder.productItemLayoutBinding.imgMinus.setOnClickListener {
           if (productModel.cartCount>0){
               PrefUtils.add2Cart(productModel.id, --productModel.cartCount)
               notifyItemChanged(position)
           }
        }
        holder.productItemLayoutBinding.image.loadImage(productModel.image)
        holder.productItemLayoutBinding.tvName.text = productModel.name
        holder.productItemLayoutBinding.tvCount.text = productModel.cartCount.toString()
        holder.productItemLayoutBinding.tvPrice.text = "${productModel.price} UZS"
    }
}