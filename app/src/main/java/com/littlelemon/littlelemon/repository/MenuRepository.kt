package com.littlelemon.littlelemon.repository

import com.littlelemon.littlelemon.database.AppDatabase
import com.littlelemon.littlelemon.database.MenuItemEntity
import com.littlelemon.littlelemon.network.fetchMenu

class MenuRepository(
    private val database: AppDatabase
) {

    fun getAllMenuItems() = database.menuDao().getAllMenuItems()

    suspend fun fetchAndStoreMenu() {
        val menuNetwork = fetchMenu()

        val entities = menuNetwork.menu.map { item ->
            MenuItemEntity(
                id = item.id,
                title = item.title,
                description = item.description,
                price = item.price,
                image = item.image,
                category = item.category
            )
        }

        database.menuDao().insertAll(entities)
    }
}
