package com.littlelemon.littlelemon.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.littlelemon.littlelemon.repository.MenuRepository

class MenuViewModelFactory(
    private val repository: MenuRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MenuViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MenuViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
