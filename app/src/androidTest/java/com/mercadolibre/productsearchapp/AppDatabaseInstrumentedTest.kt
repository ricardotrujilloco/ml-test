package com.mercadolibre.productsearchapp

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.mercadolibre.productsearchapp.cache.AppDatabase
import com.mercadolibre.productsearchapp.cache.model.CacheModel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AppDatabaseInstrumentedTest {

    @Test
    fun create_db_instance_test() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        val dbID = "ml_db"
        val database = Room.databaseBuilder(appContext, AppDatabase::class.java, dbID).build()

        assertNotNull(database)
    }

    @Test
    fun insert_single_product_in_db_test() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        val dbID = "ml_db"
        val database = Room.databaseBuilder(appContext, AppDatabase::class.java, dbID).build()
        val dao = database.productDao()
        val product = getProductsToInsert().first()

        dao.insert(product)
    }

    @Test
    fun insert_multiple_products_in_db_test() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        val dbID = "ml_db"
        val database = Room.databaseBuilder(appContext, AppDatabase::class.java, dbID).build()
        val dao = database.productDao()
        val products = getProductsToInsert()

        dao.insertAll(products)
    }

    @Test
    fun fetch_product_from_db_test() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        val dbID = "ml_db"
        val database = Room.databaseBuilder(appContext, AppDatabase::class.java, dbID).build()
        val dao = database.productDao()
        val products = getProductsToInsert()
        val productId = "ML123"
        dao.insertAll(products)

        val fetchedProduct = dao.fetch(productId)

        assertEquals(productId, fetchedProduct?.id)
    }

    private fun getProductsToInsert(): List<CacheModel.ProductDetailResponse> {
        return listOf(
            CacheModel.ProductDetailResponse(
                id = "ML123", title = "Producto 1", pictures = listOf(
                    CacheModel.ProductDetailImage("https://image1.jpg"),
                    CacheModel.ProductDetailImage("https://image2.jpg")
                )
            ),
            CacheModel.ProductDetailResponse(
                id = "ML456", title = "Producto 2", pictures = listOf(
                    CacheModel.ProductDetailImage("https://image1.jpg"),
                    CacheModel.ProductDetailImage("https://image2.jpg")
                )
            )
        )
    }
}
