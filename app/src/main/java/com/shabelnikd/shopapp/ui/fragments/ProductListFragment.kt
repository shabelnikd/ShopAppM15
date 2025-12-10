package com.shabelnikd.shopapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shopapp.adapter.ProductAdapter
import com.shabelnikd.shopapp.R
import com.shabelnikd.shopapp.data.api.RetrofitService
import com.shabelnikd.shopapp.databinding.FragmentListBinding
import kotlinx.coroutines.launch

class ProductListFragment : Fragment() {

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    private val adapter = ProductAdapter { productDto ->
        val bundle = bundleOf("PRODUCT_ID" to productDto.id)

        findNavController().navigate(
            R.id.action_productListFragment_to_detailFragment,
            bundle
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecycler()
        loadProducts()
    }

    private fun setupRecycler() {
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter
    }

    private fun loadProducts() {
        binding.progressBar.isVisible = true

        viewLifecycleOwner.lifecycleScope.launch {

            try {
                val products = RetrofitService.api.getAllProducts()
                adapter.submitList(products)
            } catch (e: Exception) {
                Toast.makeText(requireContext(), "Ошибка: ${e.message}", Toast.LENGTH_LONG).show()
            } finally {
                _binding?.progressBar?.isVisible = false
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}