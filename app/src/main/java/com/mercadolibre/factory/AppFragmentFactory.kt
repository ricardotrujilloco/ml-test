package com.mercadolibre.factory

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory

class AppFragmentFactory(
        private val creators: Map<Class<out Fragment>, FragmentCreator>
) : FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        val fragmentClass = loadFragmentClass(classLoader, className)
        val creator = creators[fragmentClass]
                ?: return createFragmentAsFallback(classLoader, className)
        return creator.get()
    }

    private fun createFragmentAsFallback(classLoader: ClassLoader, className: String): Fragment {
        return super.instantiate(classLoader, className)
    }
}
