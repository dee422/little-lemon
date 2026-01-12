package com.littlelemon.littlelemon.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// 这个类表示从网络获取的菜单数据（包含所有菜单项）
@Serializable
data class MenuNetwork(
    @SerialName("menu")
    val menu: List<MenuItemNetwork> // 每个菜单项是 MenuItemNetwork
)

// 这个类表示一个单独的菜单项
@Serializable
data class MenuItemNetwork(
    val id: Int,
    val title: String,
    val description: String,
    val price: String,
    val image: String,
    val category: String
)
