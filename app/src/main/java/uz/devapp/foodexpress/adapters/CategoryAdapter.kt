package uz.devapp.foodexpress.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.devapp.foodexpress.databinding.CategoryItemLayoutBinding
import uz.devapp.foodexpress.models.CategoryModel
import uz.devapp.foodexpress.utils.loadImage

class CategoryAdapter(val categoryList: List<CategoryModel>) :
    RecyclerView.Adapter<CategoryAdapter.Vh>() {

    inner class Vh(val categoryItemLayoutBinding: CategoryItemLayoutBinding) :
        RecyclerView.ViewHolder(categoryItemLayoutBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(
            CategoryItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        val categoryModel = categoryList[position]
        holder.categoryItemLayoutBinding.tvTitle.text = categoryModel.title
        holder.categoryItemLayoutBinding.slideImage.loadImage(categoryModel.image)
    }
}