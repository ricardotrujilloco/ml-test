package com.mercadolibre.productsearchapp.productsearch

import android.content.Context
import android.view.inputmethod.InputMethodManager
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.mercadolibre.productsearchapp.common.list.AppListAdapter
import com.mercadolibre.productsearchapp.databinding.FragmentProductSearchBinding

fun FragmentProductSearchBinding.setUpListManager(context: Context): FragmentProductSearchBinding =
    apply {
        val linearLayout = LinearLayoutManager(context)
        productsList.layoutManager = linearLayout
    }

fun FragmentProductSearchBinding.setAdapter(listAdapter: AppListAdapter): FragmentProductSearchBinding =
    apply {
        productsList.adapter = listAdapter
    }

fun FragmentProductSearchBinding.setSearchFieldChangeListener(action: (value: String) -> Unit): FragmentProductSearchBinding =
    apply {
        searchField.addTextChangedListener {
            if (searchField.hasFocus()) {
                action(it.toString())
            }
        }
    }

fun FragmentProductSearchBinding.setSearchKeyboardActionListener(): FragmentProductSearchBinding =
    apply {
        searchField.apply {
            setOnEditorActionListener { _, _, _ ->
                clearFocus()
                val inputManager: InputMethodManager? =
                    context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
                inputManager?.hideSoftInputFromWindow(windowToken, 0)
                true
            }
        }
    }

fun FragmentProductSearchBinding.showContent(state: SearchState): FragmentProductSearchBinding =
    apply {
        searchContent.displayedChild = state.code()
    }
