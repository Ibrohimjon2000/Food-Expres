package uz.devapp.foodexpress.screen.main.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import uz.devapp.foodexpress.adapters.CategoryAdapter
import uz.devapp.foodexpress.adapters.RestaurantAdapter
import uz.devapp.foodexpress.adapters.SlideAdapter
import uz.devapp.foodexpress.adapters.TopRestaurantAdapter
import uz.devapp.foodexpress.databinding.FragmentMainBinding

class MainFragment : Fragment() {
    lateinit var binding: FragmentMainBinding
    lateinit var slideAdapter: SlideAdapter
    lateinit var categoryAdapter: CategoryAdapter
    lateinit var restaurantAdapter: RestaurantAdapter
    lateinit var topRestaurantAdapter: TopRestaurantAdapter
    lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        binding.apply {
            viewModel = ViewModelProvider(this@MainFragment)[MainViewModel::class.java]

            viewModel.errorLiveData.observe(requireActivity()) {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }

            viewModel.progressLiveData.observe(requireActivity()) {
                flProgress.visibility = if (it) View.VISIBLE else View.GONE
            }

            viewModel.offerListLiveData.observe(requireActivity()) {
                slideAdapter = SlideAdapter(it ?: emptyList())
                rvSlide.adapter = slideAdapter
            }


            viewModel.categoryListLiveData.observe(requireActivity()) {
                categoryAdapter = CategoryAdapter(it ?: emptyList())
                rvCategories.adapter = categoryAdapter
            }

            viewModel.restaurantListLiveData.observe(requireActivity()) {
                restaurantAdapter = RestaurantAdapter(it ?: emptyList())
                rvNearbyRestaurants.adapter = restaurantAdapter
            }

            viewModel.restaurantTopListLiveData.observe(requireActivity()) {
                topRestaurantAdapter = TopRestaurantAdapter(it ?: emptyList())
                rvTopRestaurants.adapter = topRestaurantAdapter
            }

            viewModel.getOffers()
            viewModel.getCategory()
            viewModel.getRestaurant()
            viewModel.getTopRestaurant()
        }
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = MainFragment()
    }
}