package com.mercadolibre.factory

import androidx.fragment.app.Fragment

interface FragmentCreator {
    fun get(): Fragment
}
