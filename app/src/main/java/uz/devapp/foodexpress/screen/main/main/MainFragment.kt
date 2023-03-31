package uz.devapp.foodexpress.screen.main.main

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import uz.devapp.foodexpress.R
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
                binding.flProgress.visibility = View.GONE
            }

            viewModel.offerListLiveData.observe(requireActivity()) {
                slideAdapter = SlideAdapter(it ?: emptyList())
                rvSlide.adapter = slideAdapter
                binding.flProgress.visibility = View.GONE
            }


            viewModel.categoryListLiveData.observe(requireActivity()) {
                categoryAdapter = CategoryAdapter(it ?: emptyList())
                rvCategories.adapter = categoryAdapter
                binding.flProgress.visibility = View.GONE
            }

            viewModel.restaurantListLiveData.observe(requireActivity()) {
                restaurantAdapter = RestaurantAdapter(it ?: emptyList())
                rvNearbyRestaurants.adapter = restaurantAdapter
                binding.flProgress.visibility = View.GONE
            }

            viewModel.restaurantTopListLiveData.observe(requireActivity()) {
                topRestaurantAdapter = TopRestaurantAdapter(it ?: emptyList())
                rvTopRestaurants.adapter = topRestaurantAdapter
                binding.flProgress.visibility = View.GONE
            }

            profile.setOnClickListener {
//                Navigation.findNavController(root).navigate(R.id.profileFragment)
            }

            loadData()
        }
        return binding.root
    }

    fun loadData() {
        binding.flProgress.visibility = View.VISIBLE
        viewModel.getOffers()
        viewModel.getCategory()
        viewModel.getRestaurant()
        viewModel.getTopRestaurant()
    }

    companion object {
        @JvmStatic
        fun newInstance() = MainFragment()
    }
}