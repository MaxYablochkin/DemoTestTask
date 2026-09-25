package com.dev.maxyablochkin.demotaskapp.core.domain.model

enum class NewsCategory(val apiName: String, val titleUk: String) {
    GENERAL("general", "Загальні"),
    BUSINESS("business", "Бізнес"),
    TECHNOLOGY("technology", "Технології"),
    SCIENCE("science", "Наука"),
    SPORTS("sports", "Спорт"),
    ENTERTAINMENT("entertainment", "Розваги"),
    HEALTH("health", "Здоров'я")
}