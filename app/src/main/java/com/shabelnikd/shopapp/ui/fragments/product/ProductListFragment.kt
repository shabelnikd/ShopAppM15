package com.shabelnikd.shopapp.ui.fragments.product

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shopapp.adapter.ProductAdapter
import com.shabelnikd.shopapp.R
import com.shabelnikd.shopapp.data.datasource.RetrofitService
import com.shabelnikd.shopapp.databinding.FragmentListBinding
import com.shabelnikd.shopapp.ui.models.UiState
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProductListFragment : Fragment() {

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ListViewModel by viewModel()

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
        observeState()
    }

    private fun setupRecycler() {
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter
    }

    private fun observeState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { state ->
                    with(binding) {
                        when (state) {
                            is UiState.Loading -> {
                               progressBar.isVisible = true
                                recyclerView.isVisible = false
                            }
                            is UiState.Success -> {
                                progressBar.isVisible = false
                                recyclerView.isVisible = true
                                adapter.submitList(state.data)
                            }

                            is UiState.Error -> {
                                progressBar.isVisible = false
                                recyclerView.isVisible = false
                                Toast.makeText(requireContext(), state.message, Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}