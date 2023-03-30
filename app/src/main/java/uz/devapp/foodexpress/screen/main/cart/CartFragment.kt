package uz.devapp.foodexpress.screen.main.cart

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import uz.devapp.foodexpress.adapters.CartProductAdapter
import uz.devapp.foodexpress.adapters.CartProductAdapterCallback
import uz.devapp.foodexpress.databinding.FragmentCartBinding
import uz.devapp.foodexpress.screen.main.order.checkout.CheckoutActivity
import uz.devapp.foodexpress.utils.Constants

class CartFragment : Fragment(), CartProductAdapterCallback {
    lateinit var binding: FragmentCartBinding
    lateinit var cartProductAdapter: CartProductAdapter
    lateinit var viewModel: CartViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCartBinding.inflate(inflater, container, false)
        binding.apply {

            viewModel = ViewModelProvider(this@CartFragment)[CartViewModel::class.java]

            viewModel.errorLiveData.observe(requireActivity()) {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }

            viewModel.progressLiveData.observe(requireActivity()) {
                flProgress.visibility = if (it) View.VISIBLE else View.GONE
            }

            viewModel.foodListLiveData.observe(requireActivity()) {
                cartProductAdapter = CartProductAdapter(it ?: emptyList(), this@CartFragment)
                rvCart.adapter = cartProductAdapter
                lottie.visibility = if (it!!.isNotEmpty()) View.GONE else View.VISIBLE
                lyCartAction.visibility = if (it.isNotEmpty()) View.VISIBLE else View.GONE
                tvTotalAmount.text = it.sumOf { it.cartCount * it.price }.toString() + " UZS"
            }

            checkout.setOnClickListener {
                val intent = Intent(requireActivity(), CheckoutActivity::class.java)
                intent.putExtra(
                    Constants.EXTRA_DATA,
                    viewModel.foodListLiveData.value?.sumOf { it.cartCount * it.price }
                        .toString()
                )
                startActivity(intent)
            }

            viewModel.getFoods()

        }
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = CartFragment()
    }

    override fun onUpdate(count: Int) {
        if (count == 0) {
            binding.lottie.visibility =
                if (viewModel.foodListLiveData.value!!.isNotEmpty()) View.GONE else View.VISIBLE
            viewModel.getFoods()
        } else {
            binding.tvTotalAmount.text =
                viewModel.foodListLiveData.value?.sumOf { it.cartCount * it.price }
                    .toString() + " UZS"
        }
    }
}