package com.littlelemon.littlelemon.network

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.android.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.decodeFromString



// 创建 HttpClient 实例，用于进行网络请求
val httpClient = HttpClient(Android) {
    install(ContentNegotiation) {
        json(
            Json {
                ignoreUnknownKeys = true
                isLenient = true
            }
        )
    }
}

suspend fun fetchMenu(): MenuNetwork {
    val response: String = httpClient
        .get("https://raw.githubusercontent.com/dee422/littlelemon-assets/refs/heads/main/menu.json")
        .body()

    println("🔥 RAW JSON = $response")

    return Json { ignoreUnknownKeys = true }
        .decodeFromString(MenuNetwork.serializer(), response)
}



