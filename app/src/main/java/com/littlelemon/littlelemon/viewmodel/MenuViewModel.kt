package com.littlelemon.littlelemon.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.littlelemon.littlelemon.database.MenuItemEntity
import com.littlelemon.littlelemon.repository.MenuRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MenuViewModel(
    private val repository: MenuRepository
) : ViewModel() {

    // 1. 原始菜单数据
    private val allMenuItems: Flow<List<MenuItemEntity>> =
        repository.getAllMenuItems()

    // 2. 搜索关键词
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    // 3. 选中分类
    private val _selectedCategory = MutableStateFlow("All")
    val selectedCategory: StateFlow<String> = _selectedCategory.asStateFlow()

    // 4. 最终过滤结果（核心）
    val filteredMenuItems: StateFlow<List<MenuItemEntity>> =
        combine(allMenuItems, searchQuery, selectedCategory) { items, query, category ->
            items.filter { item ->
                val matchesSearch =
                    query.isBlank() ||
                            item.title.contains(query, ignoreCase = true) ||
                            item.description.contains(query, ignoreCase = true)

                val matchesCategory =
                    category == "All" || item.category.equals(category, ignoreCase = true)

                matchesSearch && matchesCategory
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    // 5. 对外事件
    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
    }

    fun onCategorySelected(category: String) {
        _selectedCategory.value = category
    }

    private var hasFetched = false
    fun fetchMenu() {
        if (hasFetched) return
        hasFetched = true

        viewModelScope.launch {
            repository.fetchAndStoreMenu()
        }
    }
}
