package com.mercadolibre.productsearchapp.productsearch

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.mercadolibre.productsearchapp.common.debounce
import com.mercadolibre.productsearchapp.common.list.AppListAdapter
import com.mercadolibre.productsearchapp.common.navigation.AppNavigator
import com.mercadolibre.productsearchapp.databinding.FragmentProductSearchBinding
import com.mercadolibre.productsearchapp.productsearch.adapter.OnViewSelectedListener
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

class ProductSearchFragment : Fragment(), OnViewSelectedListener {
    private val viewModel: ProductSearchViewModel by viewModel()
    private val listAdapter: AppListAdapter by inject()
    private val navigator: AppNavigator<String> by inject()

    private lateinit var viewBinding: FragmentProductSearchBinding

    init {
        listAdapter.setListener(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = FragmentProductSearchBinding.inflate(
            LayoutInflater.from(context), null, true
        )
        return viewBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        context?.let { context ->
            viewBinding
                .setUpListManager(context)
                .setAdapter(listAdapter)
                .setSearchFieldChangeListener { searchProducts(it) }
                .setSearchKeyboardActionListener()
                .showContent(SearchState.SEARCH_HINT)
                .setErrorCallToActionListener { searchProducts(it) }
        }
        viewModel.getMediator().observe(viewLifecycleOwner, {
            viewBinding.showContent(it.state)
            listAdapter.setItems(it.products ?: listOf())
        })
    }

    private val searchProducts =
        debounce<String>(500L, lifecycleScope) { viewModel.searchProducts(it) }

    override fun onItemSelected(id: String) {
        navigator.next(activity, id)
    }
}
