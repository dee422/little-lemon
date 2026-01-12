// Top-level build file where you can add configuration options common to all sub-projects/modules.

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false

    // 关键新增：给 app 模块用的 Serialization 插件
    id("org.jetbrains.kotlin.plugin.serialization") version "1.7.20" apply false
}
